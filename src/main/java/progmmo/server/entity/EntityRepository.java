package progmmo.server.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import progmmo.server.apikey.APIKey;

import java.util.List;

@Repository
public interface EntityRepository extends MongoRepository<Entity, String> {

    Entity findByID(String id);
    List<Entity> findBySector(String sector);

}
