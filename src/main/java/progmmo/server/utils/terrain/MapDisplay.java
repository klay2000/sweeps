package progmmo.server.utils.terrain;

import java.awt.*;
import java.io.File;
import java.lang.StringBuilder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class MapDisplay extends JPanel {
    private static JFrame jframe;
    private static Canvas canvas;
    private static String title = "ProgMMO world map";
    private int width = 2000;
    private int height = 2000;
    private static int cellPadding = 6;
    private World data;
    private BufferedImage bImage;

    private static final int padding = 10;

    public MapDisplay(World world) {
        this.data = world;
    }

    public MapDisplay() {
    }


    public void draw(World world) {
        this.data = world;
        initCanvas(this.data);
    }

    public void save(String filename) {
        try {
            ImageIO.write(this.bImage, "jpeg", new File(filename));
        } catch (Exception e) {

        }
    }

    public void print(World world) {
        this.data = world;
        for (int y = 0; y < world.size(); y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < world.size(); x++) {
                String c = world.getCell(x, y).booleanValue() ? "# " : ". ";
                line.append(c);
            }
            System.out.printf("%s\n", line);
        }
    }

    //only prints a region
    public void print(World world, int x1, int y1, int x2, int y2) {
        this.data = world;
        for (int y = y1; y < y2; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = x1; x < x2; x++) {
                String c = world.getCell(x, y).booleanValue() ? "# " : ". ";
                line.append(c);
            }
            System.out.println(line);
        }
    }

    private void drawGrid(Graphics g) {
        int cellWidth = width / this.data.size();
        for (int y = 0; y < this.data.size(); y++) {
            for (int x = 0; x < this.data.size(); x++) {
                break;
            }
        }
    }


    //gets called during call to jframe.paint()
    protected void paintComponent(Graphics g) {
        int cellWidth = width / this.data.size();
        System.out.println(cellWidth);
        for (int y = 0; y < this.data.size(); y++) {
            for (int x = 0; x < this.data.size(); x++) {
                int gx = x * cellWidth;
                int gy = y * cellWidth;
                if (this.data.getCell(x, y)) {
                    g.fillRect(gx + (cellPadding / 2), gy + (cellPadding / 2), cellWidth - (cellPadding / 2), cellWidth - (cellPadding / 2));
                }
            }
        }
    }

    private void initCanvas(World world) {
        this.bImage = new BufferedImage(this.height, this.width, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2d = this.bImage.createGraphics();
        jframe = new JFrame(title);
        jframe.add(this);
        jframe.setSize(width, height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);

        jframe.paint(graphics2d);
    }
}
