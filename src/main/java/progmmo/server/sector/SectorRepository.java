package progmmo.server.sector;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SectorRepository extends MongoRepository<Sector, String> {

    Sector findByID(String ID);
    void save();

}
