import org.omg.CORBA.DynAnyPackage.Invalid;

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

    public Boolean[][] getSurround(int x, int y) {
        Boolean[][] surround = {
                {this.getCell(x-1, y-1), this.getCell(x, y-1), this.getCell(x+1, y-1)},
                {this.getCell(x-1, y), this.getCell(x, y), this.getCell(x+1, y)},
                {this.getCell(x-1, y+1), this.getCell(x, y+1), this.getCell(x+1, y+1)}
        };
        return surround;
    }
}