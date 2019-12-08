package sweeps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Sector implements Serializable {

    public static final int SECTOR_HEIGHT = 25;
    public static final int SECTOR_WIDTH = 25;

    public int[][] map = new int[SECTOR_WIDTH][SECTOR_HEIGHT];

    private int x;
    private int y;

    public ArrayList<Entity> entities = new ArrayList<>();

    public Sector(int sX, int sY) throws InvalidPositionException {
        this.x = sX;
        this.y = sY;
        for(int y = 0; y < SECTOR_WIDTH; y++){
            for(int x = 0; x < SECTOR_HEIGHT; x++){
                map[x][y] = (int)Math.round(Math.random());
            }
        }

    }

    public boolean inBounds(int x, int y){
        return (x>=0&&y>=0&&x<SECTOR_WIDTH&&y<SECTOR_WIDTH);
    }

    public boolean isBlocked(int x, int y){

        if(!inBounds(x, y)) return true;

        if(map[x][y] != 0) return true;

        for(Entity i : entities){
            if(x == i.getXPosition() && y == i.getYPosition())
                return true;
        }

        return  false;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public Entity getEntity(int x, int y) {
        for (Entity i : entities) {
            if ( (i.getXPosition()==x) && (i.getYPosition() == y) ) {
                return i;
            }
        }
        return null;
    }

}
