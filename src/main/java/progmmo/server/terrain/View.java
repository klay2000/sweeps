package progmmo.server.terrain;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class View {
	private static final String ZERO_CHAR = "▂";
	private static final String ONE_CHAR = "█";

	public static void printSector(Sector sector) {
		for (int y=0; y<sector.size(); y++) {
			StringBuilder line = new StringBuilder();
			for (int x=0; x<sector.size(); x++) {
				line.append(sector.getCell(x, y).getState()==true ? ONE_CHAR : ZERO_CHAR);
				line.append(sector.getCell(x, y).getState()==true ? ONE_CHAR : ZERO_CHAR);
			}
			System.out.println(line);
		}
	}

	public static void drawSector(Sector sector) {
		ImageWindow display = new ImageWindow(sector.size());
		for (int x=0; x<sector.size(); x++) {
			for (int y=0; y<sector.size(); y++) {
				display.drawCell(x, y, sector.getCell(x, y).getState());
			}
		}
		display.showPicture();
	}

	public static void drawWorld(World world) {
		int size=world.getSectorRowSize()*world.getSector(0, 0).size();
		ImageWindow display = new ImageWindow(size);
		display.setScale(10);
		for (int yw=0; yw<world.getSectorRowSize(); yw++) {
			for (int xw=0; xw<world.getSectorRowSize(); xw++) {
				for (int ys=0; ys<world.getSectorRowSize(); ys++) {
					for (int xs=0; xs<world.getSectorRowSize(); xs++) {
						int x=(xw*world.getSectorRowSize())+xs;
						int y=(yw*world.getSectorRowSize())+ys;
					    display.drawCell(x, y, world.getSector(xw, yw).getCell(xs, ys).getState());
					}
				}
			}
		}
		display.showPicture();
	}
}


class ImageWindow extends JFrame {
	private MapImage data;
	private int scale = 25;
	private int spaceWidth = 2;
	public ImageWindow(int size) {
		data = new MapImage((size*scale)+(size*spaceWidth));
		data.setPreferredSize(new Dimension((size*scale)+(size*spaceWidth), (size*spaceWidth)));
		Container cp = getContentPane();
		cp.add(data);
		setSize(size*scale, size*scale);
		setTitle("map/sector");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showPicture() {
		super.setVisible(true);
	}

	public void setScale(int newScale) { this.scale = newScale;	}

	public void drawCell(int x, int y, boolean state) {
		if (state==true) {
		    int xpos=(x*scale)+(spaceWidth);
		    int ypos=(y*scale)+(spaceWidth);
			data.fillSquare(xpos, ypos, scale-spaceWidth);
		}
	}

	private class MapImage extends JPanel {
		private BufferedImage state;

		public MapImage(int size) {
		    this.state = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		    Graphics g = state.getGraphics();
		    g.setColor(Color.WHITE);
		    g.fillRect(0, 0, size, size);
		    g.dispose();
		}

		public void fillSquare(int x, int y,int scale) {
			Graphics g = state.getGraphics();
			g.setColor(Color.BLACK);
			g.fillRect(x, y, scale, scale);
			g.dispose();
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(state, 0, 0, null);
		}
	}
}
