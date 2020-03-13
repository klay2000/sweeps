import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Transform {
    //this is a percentage
    private static int randomThreshold = 50;

    public static void populateRandom(World world) {
        world.applyFunction((x, y) -> {
            Random rand = new Random();
            if (rand.nextInt(100) < randomThreshold) {
                world.setCell(x, y, true);
            }
        });
    }

    public static void populateRandom(World world, int threshold) {
        world.applyFunction((x, y) -> {
            Random rand = new Random();
            if (rand.nextInt(100) < threshold) {
                world.setCell(x, y, true);
            }
        });
    }

    public static void thinStrayWalls(World world) {
        world.applyFunction((x, y) -> {
            Boolean[][] block = world.getSurround(x, y);
            //substituting false for null because false != null and null doesn't like being compared
            for (int px = 0; px <= 2; px++) {
                for (int py = 0; py <= 2; py++) {
                    if (block[px][py] == null) {
                        block[px][py] = new Boolean(false);
                    }
                }
            }
            if (!((block[0][1] || block[2][1]) & (block[1][0] || block[1][2]))) {
                world.setCell(x, y, false);
                //Syste>m.out.printf("====================\n%s\n%s\n%s\n====================\n", Arrays.asList(block[0]), Arrays.asList(block[1]), Arrays.asList(block[2]));
            }
        });
    }

    public static void shapeWalls(World world) {
        world.applyFunction((x, y) -> {
            Boolean[][] block = world.getSurround(x, y);
            int count = 0;
            for (int xo=0; xo<=2; xo++) {
                for (int yo=0; yo<=2; yo++) {
                    if (block[xo][yo] == null) {
                        break;
                    } else if (block[xo][yo] == true) {
                        count++;
                    }
                }
            }
            if (count >= 5) {
                world.setCell(x, y, true);
            } else {
                world.setCell(x, y, false);
            }
        });
    }

    //this threshold is 0-8
    public static void shapeWalls(World world, int threshold) {
        world.applyFunction((x, y) -> {
            Boolean[][] block = world.getSurround(x, y);
            int count = 0;
            for (int xo=0; xo<=2; xo++) {
                for (int yo=0; yo<=2; yo++) {
                    if (block[xo][yo] & !((xo==0)&yo==0)) {
                        count++;
                    }
                }
            }
            if (count >= threshold) {
                world.setCell(x, y, true);
            } else {
                world.setCell(x, y, false);
            }
        });
    }
}