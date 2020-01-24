package progmmo.server.entity;


import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.AccessType.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class Entity {

    //Strings that make up ID of this entity
    private String ownerPrefix;
    private String entityIdentifier;
    private int entityIndex;

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

    @AccessType(Type.PROPERTY)
    public String getID(){
        return ownerPrefix+entityIdentifier+entityIndex;
    }

    public float getEnergy() {
        return energy;
    }
}
