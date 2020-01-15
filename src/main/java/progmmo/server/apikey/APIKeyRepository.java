package progmmo.server.apikey;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface APIKeyRepository extends MongoRepository<APIKey, String> {

    List<APIKey> findAll();

    APIKey findByPrefix(String prefix);
}
