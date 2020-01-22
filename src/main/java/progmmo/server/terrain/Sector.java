package progmmo.server.terrain;

import java.util.ArrayList;
import java.util.List;

public class Sector {
	public ArrayList<ArrayList<Cell>> data;
	public World parent = null;
	public String ID = null;

	public Sector() {
		ArrayList<ArrayList<Cell>> data = new ArrayList<ArrayList<Cell>>();
		for (int i=0; i<25; i++) {
			ArrayList<Cell> row = new ArrayList(25);
			for (int j=0; j<25; j++) {
				row.add(new Cell(i, j, this));
			}
			data.add(row);
		}
		this.data = data;
	}

	public Sector(int size, String id) {
		ArrayList<ArrayList<Cell>> data = new ArrayList();
		for (int i=0; i<size; i++) {
			ArrayList<Cell> row = new ArrayList();
			for (int j=0; j<size; j++) {
				row.add(new Cell(i, j, this));
			}
			data.add(i, row);
		}
		this.data = data;
		this.ID = id;
	}

	public Sector(int size) {
		ArrayList<ArrayList<Cell>> data = new ArrayList();
		for (int i=0; i<size; i++) {
			ArrayList<Cell> row = new ArrayList();
			for (int j=0; j<size; j++) {
				row.add(new Cell(i, j, this));
			}
			data.add(i, row);
		}
		this.data = data;
	}

	public Sector(World world, int size, String id) {
		ArrayList<ArrayList<Cell>> data = new ArrayList();
		for (int i=0; i<size; i++) {
			ArrayList<Cell> row = new ArrayList();
			for (int j=0; j<size; j++) {
				row.add(new Cell(i, j, this));
			}
			data.add(i, row);
		}
		this.data = data;
		this.ID = id;
		this.parent=world;
	}

	public Sector(ArrayList<List<Boolean>> data) {
		ArrayList<ArrayList<Boolean>> out = new ArrayList<ArrayList<Boolean>>(data.size());
		for (int x=0; x<data.size(); x++) {
			ArrayList<Boolean> row = new ArrayList<Boolean>();
			for (int y=0; y<data.get(0).size(); y++) {
				row.set(y, data.get(x).get(y));
			}
			out.set(x, row);
		}
	}

	public Sector(World world, String id) {
		ArrayList<ArrayList<Cell>> data = new ArrayList<ArrayList<Cell>>();
		for (int i=0; i<25; i++) {
			ArrayList<Cell> row = new ArrayList(25);
			for (int j=0; j<25; j++) {
				row.add(new Cell(i, j, this));
			}
			data.add(row);
		}
		this.data = data;
		this.parent = world;
		this.ID = id;
	}

	public boolean[] getSurround(int x, int y) {
		boolean[] out = new boolean[8];
		int count = 0;
		for (int yo=-1; yo<=1; yo++) {
			for (int xo=-1; xo<=1; xo++) {
				if ((xo == 0) || (yo == 0)) {
					break;
				}
				try {
					out[count] = this.getCell(x+xo, y+yo).getState();
				}
				//little bit of a kludge but not too big a deal
				catch (Exception e) {
					try {
						out[count] = this.parent.getGlobalCell(x + xo, y + yo).getState();
					}
					catch (Exception f) {
						out[count] = false;
					}
				}
			}
		}
		return out;
	}


	//sectorSize is intended size of sectors in output world
	public static World asWorld(Sector sector, int sectorSize) {
		int numSectors = sector.size()/sectorSize;
		World out = new World(numSectors);
		for (int yw=0; yw<numSectors; yw++) {
			for (int xw=0; xw<numSectors; xw++) {
				Sector sector1 = new Sector(sectorSize);
				for (int ys=0; ys<sectorSize; ys++) {
					for (int xs=0; xs<sectorSize; xs++) {
						int selectX = (xw*sectorSize)+xs;
						int selectY = (yw*sectorSize)+ys;
						sector1.setCell(xs, ys, sector.getCell(selectX, selectY).getState());
					}
				}
				out.setSector(xw, yw, sector1);
			}
		}
		return out;
	}
	public void setCell(int x, int y, boolean val) {
		this.data.get(x).get(y).setState(val);
	}

	public Cell getCell(int x, int y) {
		return this.data.get(x).get(y);
	}

	public int size() {
		return this.data.size();
	}

}
