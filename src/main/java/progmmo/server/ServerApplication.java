package progmmo.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        repository.save(new APIKey("bloop"));
        repository.save(new APIKey("bleep"));
        repository.save(new APIKey("blop"));

        //System.out.println(repository.findByPrefix("bleep"));

    }
}
