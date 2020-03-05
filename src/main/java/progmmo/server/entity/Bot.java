package progmmo.server.entity;

public class Bot extends Entity{

    private static final float INITIAL_ENERGY = 10;

    public Bot(int x, int y, String sectorID, String ownerPrefix, String entityIdentifier){
        super(x, y, sectorID, ownerPrefix, entityIdentifier, INITIAL_ENERGY);

    }



}
