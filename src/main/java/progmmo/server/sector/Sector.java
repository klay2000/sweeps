package progmmo.server.sector;

/*
 * This class basically only functions as a marker to keep track of what sectors have been generated.
 * It in no way keeps track of any data about a sector besides marking that it exists.
 */

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sector {

    @Id
    private String id; // Format is (x coord):(y coord), ex 1:2 for sector at 1, 2.

    public Sector(String id){
        this.id = id;
    }


    @AccessType(AccessType.Type.PROPERTY)
    public String getId(){ return id; }

    public void save() {

    }
}
