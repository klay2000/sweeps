package progmmo.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import progmmo.server.apikey.APIKey;
import progmmo.server.apikey.APIKeyRepository;
import progmmo.server.command.Command;
import progmmo.server.command.CommandRepository;
import progmmo.server.entity.Entity;
import progmmo.server.entity.EntityRepository;
import progmmo.server.utils.CommandType;
import progmmo.server.utils.Direction;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private APIKeyRepository apiKeyRepository;

    @Autowired
    private CommandRepository commandRepository;

    public static void main(String [] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//        apiKeyRepository.save(new APIKey("bleep", APIKey.generateHash("bleep", "123")));
//
//        commandRepository.save(new Command(Direction.north, CommandType.attack, "1"));

    }
}
