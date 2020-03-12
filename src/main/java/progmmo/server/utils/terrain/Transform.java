import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Transform {
    private static int randomThreshold = 50;

    public static void populateRandom(World world) {
        Random rand = new Random();

        for (int y=0; y<world.size(); y++) {
            for (int x=0; x<world.size(); x++) {
                int roll = rand.nextInt(100);
                if (roll > randomThreshold) {
                    world.setCell(x, y, true);
                }
            }
        }
    }

    public static void thinStrayWalls(World world) {
        for (int y=0; y<world.size(); y++) {
            for (int x=0; x<world.size(); x++) {
                Boolean[][] block = world.getSurround(x, y);
                //substituting false for null because false != null and null doesn't like being compared
                for (int px=0; px<=2; px++) {
                    for (int py=0; py<=2; py++) {
                        if (block[px][py] == null) {
                            block[px][py] = new Boolean(false);
                        }
                    }
                }
                if ((block[0][1] || block[2][1]) & (block[1][0] || block[1][2])) {
                    break;
                } else {
                    world.setCell(x, y, false);
                }
            }
        }
    }

//    public static void generateWalls(Sector sector) {
//        for (int x=0; x<sector.size(); x++) {
//            for (int y=0; y<sector.size(); y++) {
//                int count = 0;
//                for (int xo=-1; xo<2; xo++) {
//                    for (int yo=-1; yo<2; yo++) {
//                        try {
//                            if (sector.getCell(x+xo, y+yo).getState() == true) {
//                                count++;
//                            }
//                        }
//                        catch(Exception e) {
//                            break;
//                        }
//                    }
//                }
//                sector.setCell(x, y, count>=4);
//            }
//        }
//    }
//
//    public static void generateWalls(Sector sector, int threshold) {
//        for (int x=0; x<sector.size(); x++) {
//            for (int y=0; y<sector.size(); y++) {
//                int count = 0;
//                for (int xo=-1; xo<2; xo++) {
//                    for (int yo=-1; yo<2; yo++) {
//                        try {
//                            if (sector.getCell(x+xo, y+yo).getState() == true) {
//                                count++;
//                            }
//                        }
//                        catch(Exception e) {
//                            break;
//                        }
//                    }
//                }
//                if (count>= threshold) {
//                    sector.setCell(x, y, true);
//                }
//                else {
//                    sector.setCell(x, y, false);
//                }
//            }
//        }
//    }
//
//    //I'm so sorry if anyone ever has to see this aside from me
//    public static void generateWalls(World world, int threshold) {
//        for (int xw=0; xw<world.getSectorRowSize(); xw++) {
//            for (int yw=0; yw<world.getSectorRowSize(); yw++) {
//                for (int xs=0; xs<world.getSector(0, 0).size(); xs++) {
//                    for (int ys=0; ys<world.getSector(0, 0).size(); ys++) {
//                        int count = 0;
//                        boolean[] surround = world.getSector(xw, yw).getSurround(xs, ys);
//                        for (int i=0; i<surround.length; i++) {
//                            if (surround[i]==true) {
//                                count++;
//                            }
//                        }
//                        if (count>= threshold) {
//                            world.getSector(xw, yw).setCell(xs, ys, true);
//                        }
//                        else {
//                            world.getSector(xw, yw).setCell(xs, ys, false);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//    public static void populateRandom(Sector sector, int density) {
//        Random rand = new Random();
//        for (int x=0; x<sector.size(); x++) {
//            for (int y=0; y<sector.size(); y++) {
//                sector.setCell(x, y, rand.nextInt(100) <= density);
//            }
//        }
//    }
}