package progmmo.server.apikey;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Document(collection = "apikey")
public class APIKey {

    public APIKey(String prefix, String email, byte[] hash){

        if(prefix.length() > 25) this.prefix = prefix.substring(0, 25);
        else this.prefix = prefix;

        this.hash = hash;

        this.email = email;

    }

    private byte[] hash;

    @Indexed(unique = true, name = "APIKey_Prefix_Index")
    private String prefix;

    private String email;

    private int entitiesCreated = 0;

    public String getPrefix(){
        return prefix;
    }

    public int getEntitiesCreated(){
        return entitiesCreated;
    }

    public int getEntityIndex(){
        return entitiesCreated++;
    }

    public static byte[] generateHash(String prefix, String key) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");

            return hasher.digest((prefix+key).getBytes("UTF_16"));

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) { e.printStackTrace();}

        return new byte[0];
    }

    public static byte[] generateHash(String key) {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");

            return hasher.digest((key).getBytes("UTF_16"));

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) { e.printStackTrace();}

        return new byte[0];
    }

}
