package progmmo.server.utils.terrain;

import java.util.*;

public class Transform {
    //this is a percentage
    private static byte[] seed = WorldOperation.hexStringToByteArray("000000000000");
    public static Random rand = new Random();
    // this is a list of all of the function objects for algorithms, sorry it's kind
    // of a weird solution, I promise there's a reason
    public static Map<String, WorldManipulation> Operations = new HashMap<String , WorldManipulation>() {{
        put("PopulateRandom", (World world, int x, int y, int threshold) -> {
            if (rand.nextInt(100) < threshold) {
                world.setCell(x, y, true);
            }
        });

        put("thinStrayWalls", (World world, int x, int y, int threshold)-> {
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

        put("shapeWalls", (World world, int x, int y, int threshold)->{
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
    }};
}