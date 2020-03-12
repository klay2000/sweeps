import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MapDisplay extends JPanel {
    private static JFrame jframe;
    private static Canvas canvas;
    private static String title = "ProgMMO world map";
    private static int width = 1000;
    private static int height = 1000;
    private World data;

    private static final int padding = 5;

    public MapDisplay(World world) {
        this.data = world;
    }

    public MapDisplay() {

    }

    public void paint(Graphics g) {
        int cellWidth = width/this.data.size();
        System.out.println(cellWidth);
        drawGrid(g);
        for (int y=0; y<this.data.size(); y++) {
            for (int x=0; x<this.data.size(); x++) {
                int gx = x*cellWidth;
                int gy = y*cellWidth;
                if (this.data.getCell(x, y)) {
                    g.fillRect(gx, gy, gx+cellWidth, gy+cellWidth);
                    System.out.printf("%d, %d\n", gx, gy);
                }
            }
        }
    }

    public void draw(World world) {
        this.data = world;
        initCanvas(this.data);
    }

    public void print(World world) {
        this.data = world;
        for (int y=0; y<world.size(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x=0; x<world.size(); x++) {
                String c = world.getCell(x, y).booleanValue() ? "# " : ". ";
                line.append(c);
            }
            System.out.printf("%s\n", line);
        }
    }

    public void print(World world, int x1, int y1, int x2, int y2) {
        this.data = world;
        for (int y=y1; y<y2; y++) {
            StringBuilder line = new StringBuilder();
            for (int x=x1; x<x2; x++) {
                String c = world.getCell(x, y).booleanValue() ? "# " :". ";
                line.append(c);
            }
            System.out.println(line);
        }
    }

    private void drawGrid(Graphics g) {
        int cellWidth = width/this.data.size();
        for (int y=0; y<this.data.size(); y++) {
            for (int x=0; x<this.data.size(); x++) {
                break;
            }
        }
    }

    private static void initCanvas(World world) {
        jframe = new JFrame(title);
        jframe.setSize(width, height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);
        jframe.getContentPane().add(new MapDisplay(world));

        jframe.pack();
    }
}
