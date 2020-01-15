package progmmo.server.apikey;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Document
public class APIKey {

    public APIKey(String prefix, String key){

        if(prefix.length() > 25) this.prefix = prefix.substring(0, 25);
        else this.prefix = prefix;

        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");

            hash = hasher.digest((prefix+key).getBytes("UTF_16"));

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) { e.printStackTrace(); }

    }

    private byte[] hash;

    @Indexed(unique = true, name = "Index")
    private String prefix;
}
