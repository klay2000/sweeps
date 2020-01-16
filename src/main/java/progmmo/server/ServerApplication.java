package progmmo.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import progmmo.server.apikey.APIKey;
import progmmo.server.apikey.APIKeyRepository;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private APIKeyRepository repository;

    public static void main(String [] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        repository.save(new APIKey("bloop", "123"));
        repository.save(new APIKey("bleep", "123"));
        repository.save(new APIKey("blop", "123"));

        //System.out.println(repository.findByPrefix("bleep"));

    }
}
