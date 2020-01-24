package progmmo.server.command;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends MongoRepository<Command, String> {

}
