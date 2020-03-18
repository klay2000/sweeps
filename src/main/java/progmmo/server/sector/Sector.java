package progmmo.server.sector;

/*
 * This class basically only functions as a marker to keep track of what sectors have been generated.
 * It in no way keeps track of any data about a sector besides marking that it exists.
 */

public class Sector {

    public Sector(String id){
        this.ID = id;
    }

    private String ID; // Format is (x coord):(y coord), ex 1:2 for sector at 1, 2.

    public String getID(){ return ID; }
}
