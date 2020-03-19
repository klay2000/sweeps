package progmmo.server.entity;

public class Wall extends Entity {
    public Wall(int x, int y, String sectorID) {
        super(x, y, sectorID, "SYSTEM", "wall", -1);
    }
}
