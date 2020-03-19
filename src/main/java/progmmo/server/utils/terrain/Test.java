import progmmo.server.utils.terrain.*;
public class Test {
    public static void main(String[] args) {
        World test = new World();
        Transform.populateRandom(test, 70);
        MapDisplay disp = new MapDisplay();
        disp.print(test, 0, 0, 100, 50);
        System.out.println("==========================================");
        Transform.thinStrayWalls(test);
        Transform.thinStrayWalls(test);
        Transform.shapeWalls(test);
        Transform.shapeWalls(test);
        Transform.thinStrayWalls(test);
        disp.draw(test);
        disp.save("./map.jpg");
    }

    public static void printBlock(Boolean[][] block) {
        for (int l=0; l<block.length; l++) {
            System.out.printf("[ ");
            for(int i=0; i<block[0].length; i++) {
                System.out.printf("%d ", block[l][i]!=null ? block[l][i]==true ? 1 : 0 : 0);
            }
            System.out.printf("]\n");
        }
    }
}
