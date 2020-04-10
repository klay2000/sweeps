package progmmo.server.utils.terrain;

import org.omg.CORBA.DynAnyPackage.Invalid;
import progmmo.server.entity.Entity;
import progmmo.server.entity.EntityController;
import progmmo.server.entity.EntityRepository;
import progmmo.server.entity.Wall;

import java.util.ArrayList;
import java.util.List;

//TODO: We're rewriting this whole bitch, possibly from the ground up
//TODO reimplement creating world with size parameters
//TODO relative cell retrieval methods
public class World {
    public ArrayList<List<Boolean>> map;
    public int sectorWidth;
    public int worldWidth;

    public World() {
        map = new ArrayList<List<Boolean>>(sectorWidth*worldWidth);
        sectorWidth = 25;
        worldWidth = 10;

        for (int y=0; y < sectorWidth*worldWidth; y++) {
            map.add(new ArrayList<Boolean>(sectorWidth*worldWidth));
            for (int x=0; x < sectorWidth*worldWidth; x++) {
                map.get(y).add(x, false);
            }
        }
    }

    public World(WorldOperation sequence[]) {
        for (int i=0; i<sequence.length; i++) {

        }
    }

    public static World GenerateNew() {
        World r = new World();
        r.generate(WorldOperation.hexStringToByteArray("1234567890ab"));
        return r;
    }

    public int size() {
        return sectorWidth*worldWidth;
    }

    public Boolean getCell(int x, int y) {
        Boolean r;
        try {
            r = map.get(x).get(y);
        } catch (Exception e) {
            r = null;
        }
        return r;
    }

    public void setCell(int x, int y, boolean state) {
        map.get(x).set(y, state);
    }

    public void writeTo(EntityRepository entityRepository) {
        for (int y=0; y<this.size(); y++) {
            for (int x=0; x<this.size(); x++) {
                if (this.getCell(x, y)) {
                    int sx = x%this.sectorWidth;
                    int sy = y%this.sectorWidth;
                    String sectorID = String.format("%d.%d", (int) x/this.sectorWidth, (int) y/this.sectorWidth);
                    entityRepository.save(new Wall(sx, sy, sectorID));
                }
            }
        }
    }

    public Boolean[][] getSurround(int x, int y) {
        Boolean[][] surround = {
                {this.getCell(x-1, y-1), this.getCell(x, y-1), this.getCell(x+1, y-1)},
                {this.getCell(x-1, y), this.getCell(x, y), this.getCell(x+1, y)},
                {this.getCell(x-1, y+1), this.getCell(x, y+1), this.getCell(x+1, y+1)}
        };
        return surround;
    }

    public void applyFunction(String name, int threshold) {
        for (int x=0; x<this.size(); x++) {
            for (int y=0; y<this.size(); y++) {
                Transform.Operations.get(name).doAt(this, x, y, threshold);
            }
        }
    }

    public void generate(byte[] seed) {
        this.applyFunction("populateRandom", 45);
    }

    public void generate(WorldOperation funs[]) {
        for (int i=0; i<funs.length; i++) {
            this.applyFunction(funs[i].name, funs[i].threshold);
        }
    }
}