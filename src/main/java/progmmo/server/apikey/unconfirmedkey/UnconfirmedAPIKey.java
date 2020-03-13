package progmmo.server.apikey.unconfirmedkey;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Document(collection = "unconfirmedapikey")
public class UnconfirmedAPIKey {

    private static final int APIKEY_LENGTH = 20; //Todo: convert to a setting.
    private static final int ROUGH_TIME_TO_CONFIRM = 60*60; //Todo: convert to a setting.

    private byte[] hash;

    @Indexed(unique = true, name = "APIKey_Prefix_Index")
    private String prefix;

    @Field
    @Indexed(name = "Expiration_Index", expireAfterSeconds= ROUGH_TIME_TO_CONFIRM)
    private Date creationTime;

    public byte[] getHash() {
        return hash;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getEmail() {
        return email;
    }

    private String email;

    public UnconfirmedAPIKey(String prefix, String email, byte[] hash){

        creationTime = new Date();

        if(prefix.length() > 25) this.prefix = prefix.substring(0, 25);
        else this.prefix = prefix;

        this.hash = hash;

        this.email = email;

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

    public static String generateRandomKey(){
        String salt = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

        String key = "";

        for(int i = 0; i < APIKEY_LENGTH; ++i){
            key += salt.charAt((int)(Math.random()*salt.length()));
        }

        return key;
    }
}
