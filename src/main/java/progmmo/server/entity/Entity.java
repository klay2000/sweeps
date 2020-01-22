package progmmo.server.entity;

public abstract class Entity {

    //ID of this entity.
    private String id;

    //The ID of the sector this entity is in.
    private String sector;

    //Entity position variables.
    private int x;
    private int y;

    //Entity's stored energy. An entity is destroyed when this reaches 0.
    private float energy;


    public int getX() {
        return x;
    }

    public String getSector(){
        return sector;
    }

    public int getY() {
        return y;
    }

    public float getEnergy() {
        return energy;
    }
}
