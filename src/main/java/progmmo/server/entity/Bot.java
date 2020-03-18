package progmmo.server.entity;

public class Bot extends Entity{

    private static final float INITIAL_ENERGY = 10;

    public Bot(int x, int y, String sector, String ownerPrefix, String entityIdentifier, int entityIndex){
        super(x, y, sector, ownerPrefix, entityIdentifier, entityIndex, INITIAL_ENERGY);

    }



}
