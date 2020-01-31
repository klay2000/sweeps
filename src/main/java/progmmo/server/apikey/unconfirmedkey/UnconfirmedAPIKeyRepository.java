package progmmo.server.apikey.unconfirmedkey;

import org.springframework.data.mongodb.repository.MongoRepository;
import progmmo.server.apikey.APIKey;

public interface UnconfirmedAPIKeyRepository extends MongoRepository<UnconfirmedAPIKey, String> {

    UnconfirmedAPIKey findByPrefix(String prefix);

    void deleteByPrefix(String prefix);

}
