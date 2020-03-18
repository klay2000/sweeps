package progmmo.server.entity;


import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class Entity {

    Entity(int x, int y, String sector, String ownerPrefix, String entityIdentifier, int entityIndex, float energy){
        this.x = x;
        this.y = y;
        this.sector = sector;
        this.ownerPrefix = ownerPrefix;
        this.entityIdentifier = entityIdentifier;
        ID = ownerPrefix+entityIdentifier+entityIndex;
    }

    @Id
    public String _id;

    //Strings that make up ID of this entity
    private String ownerPrefix;
    private String entityIdentifier;
    private int entityIndex;

    private String ID;

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

    public String getID(){
        return ID;
    }

    protected void setID(String ID){
        this.ID = ID;
    }

    public float getEnergy() {
        return energy;
    }

    public String getOwnerPrefix() { return ownerPrefix; }

}
