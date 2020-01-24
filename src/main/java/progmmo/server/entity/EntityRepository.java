package progmmo.server.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface EntityRepository extends MongoRepository<Entity, String> {

    Entity findByID(String ID);
    List<Entity> findBySector(String sector);

}
