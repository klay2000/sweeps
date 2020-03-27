package progmmo.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import progmmo.server.apikey.APIKey;
import progmmo.server.apikey.APIKeyRepository;
import progmmo.server.command.Command;
import progmmo.server.command.CommandRepository;
import progmmo.server.config.EmailConfig;
import progmmo.server.entity.Entity;
import progmmo.server.entity.EntityRepository;
import progmmo.server.utils.CommandType;
import progmmo.server.utils.Direction;
import progmmo.server.utils.EmailSender;
import progmmo.server.utils.terrain.World;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.MessageDigest;
import java.util.Properties;

@SpringBootApplication
public class ServerApplication implements CommandLineRunner {

    @Autowired
    private APIKeyRepository apiKeyRepository;

    @Autowired
    private CommandRepository commandRepository;

    public static void main(String [] args) {
        SpringApplication.run(ServerApplication.class, args);
        World map = World.GenerateNew();
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
