package progmmo.server.apikey;

import org.springframework.data.mongodb.core.index.Indexed;

public class APIKey {

    public APIKey(String prefix){
        this.prefix = prefix;
    }

    //@Indexed(unique = true)
    private String hash;

    @Indexed(unique = true)
    private String prefix;

}
