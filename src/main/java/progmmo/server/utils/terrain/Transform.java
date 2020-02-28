package progmmo.server.utils.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Transform {
    public static void generateWalls(Sector sector) {
        for (int x=0; x<sector.size(); x++) {
            for (int y=0; y<sector.size(); y++) {
                int count = 0;
                for (int xo=-1; xo<2; xo++) {
                    for (int yo=-1; yo<2; yo++) {
                        try {
                            if (sector.getCell(x+xo, y+yo).getState() == true) {
                                count++;
                            }
                        }
                        catch(Exception e) {
                            break;
                        }
                    }
                }
                sector.setCell(x, y, count>=4);
            }
        }
    }

    public static void generateWalls(Sector sector, int threshold) {
        for (int x=0; x<sector.size(); x++) {
            for (int y=0; y<sector.size(); y++) {
                int count = 0;
                for (int xo=-1; xo<2; xo++) {
                    for (int yo=-1; yo<2; yo++) {
                        try {
                            if (sector.getCell(x+xo, y+yo).getState() == true) {
                                count++;
                            }
                        }
                        catch(Exception e) {
                            break;
                        }
                    }
                }
                if (count>= threshold) {
                    sector.setCell(x, y, true);
                }
                else {
                    sector.setCell(x, y, false);
                }
            }
        }
    }

    //I'm so sorry if anyone ever has to see this aside from me
    public static void generateWalls(World world, int threshold) {
        for (int xw=0; xw<world.getSectorRowSize(); xw++) {
            for (int yw=0; yw<world.getSectorRowSize(); yw++) {
                for (int xs=0; xs<world.getSector(0, 0).size(); xs++) {
                    for (int ys=0; ys<world.getSector(0, 0).size(); ys++) {
                        int count = 0;
                        boolean[] surround = world.getSector(xw, yw).getSurround(xs, ys);
                        for (int i=0; i<surround.length; i++) {
                            if (surround[i]==true) {
                                count++;
                            }
                        }
                        if (count>= threshold) {
                            world.getSector(xw, yw).setCell(xs, ys, true);
                        }
                        else {
                            world.getSector(xw, yw).setCell(xs, ys, false);
                        }
                    }
                }
            }
        }
    }

    public static void populateRandom(Sector sector) {
        Random rand = new Random();
        for (int x=0; x<sector.size(); x++) {
            for (int y=0; y<sector.size(); y++) {
                sector.setCell(x, y, rand.nextInt(2)==1);
            }
        }
    }

    public static void populateRandom(Sector sector, int density) {
        Random rand = new Random();
        for (int x=0; x<sector.size(); x++) {
            for (int y=0; y<sector.size(); y++) {
                sector.setCell(x, y, rand.nextInt(100) <= density);
            }
        }
    }
}