package progmmo.server.terrain;

public class Cell {
    public int x;
    public int y;
    public Sector parent;
    private boolean state;

    public Cell() {
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell(int x, int y, Sector parent) {
        this.x = x;
        this.y = y;
        this.parent = parent;
    }

    public boolean getState() { return this.state; }

    public void setState(boolean val) { this.state = val; }
}
