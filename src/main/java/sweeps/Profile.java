package sweeps;
import sweeps.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Profile implements Serializable{

    private byte[] hash;
    private ArrayList<Entity> owned;

    public Profile(byte[] hash){
        this.hash = hash;
        owned = new ArrayList<>();
    }

    public byte[] getHash(){
        return hash;
    }
    public ArrayList<Entity> getOwned(){
        return owned;
    }

}
