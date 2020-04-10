package progmmo.server.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import progmmo.server.apikey.APIKey;
import progmmo.server.apikey.APIKeyRepository;
import progmmo.server.apikey.unconfirmedkey.UnconfirmedAPIKey;
import progmmo.server.apikey.unconfirmedkey.UnconfirmedAPIKeyRepository;
import progmmo.server.config.MapConfig;
import progmmo.server.sector.SectorRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/entities")
public class EntityController {

    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private APIKeyRepository apiKeyRepository;

    //@Autowired
    //private SectorRepository sectorRepository;

    @Autowired
    private MapConfig mapConfig;

    @RequestMapping(value="/{entityID}", method = RequestMethod.GET)
    Map<String, Object> postEntity(@PathVariable("entityID")String entityID){
        HashMap<String, Object> response = new HashMap<>();

        Entity entity = entityRepository.findByID(entityID);
        if(entity == null) response.put("response", "This entity does not exist.");
        else response.put("entity", entityRepository.findByID(entityID));

        return response;
    }

    @RequestMapping(value="/{entityID}", method = RequestMethod.POST)
    Map<String, Object> addEntity(@PathVariable("entityID")String entityID, Map<String, String> body){
        HashMap<String, Object> response = new HashMap<>();

        APIKey key = apiKeyRepository.findByHash(APIKey.generateHash(body.get("APIKey")));
        int x = 0;
        int y = 0;
        String sectorID = body.get("SectorID");
        String idSegment = body.get("IDSegment");

        //ensure all values are entered and valid
        try{
            x = Integer.parseInt(body.get("xPosition"));
            y = Integer.parseInt(body.get("yPosition"));
        }catch (NumberFormatException e){
            response.put("response", "One or more required values missing or invalid.");
            return response;
        }

        if(sectorID == null || idSegment == null){
            response.put("response", "Missing one or more required values.");
            return response;
        }

        //check if APIkey exists
        if(key == null){
            response.put("response", "Incorrect APIkey.");
            return response;
        }

        //check if APIkey already has an entity
        if(key.getEntitiesCreated() > 0){
            response.put("response", "This APIkey already has an entity.");
            return response;
        }

        //check if sector id is valid
        int sectorX = Integer.parseInt(sectorID.split(":")[0]);
        int sectorY = Integer.parseInt(sectorID.split(":")[1]);

        if(Math.abs(sectorX) > mapConfig.getMapSize()/2 || Math.abs(sectorY) > mapConfig.getMapSize()/2){
            response.put("response", "SectorID out of map bounds or invalid.");
            return response;
        }

        //check if position in sector is valid
        if(x > mapConfig.getSectorSize() || y > mapConfig.getSectorSize()){
            response.put("response", "Position in sector is outside of sector bounds or invalid.");
        }

        //check if sector exists
        //if(sectorRepository.findByID(sectorID) == null){
            //TODO: generate sector
        //}

        //check if position is taken by another entity
        for(Entity i : entityRepository.findBySector(sectorID)){
            if(i.getX() == x && i.getY() == y){
                response.put("response", "Position in sector already occupied.");
                return response;
            }
        }

        Entity entity = new Bot(x, y, sectorID, key.getPrefix(), idSegment);

        entityRepository.save(entity);

        response.put("response", "Entity created successfully.");

        return response;
    }

    @RequestMapping(value="/{entityID}", method = RequestMethod.DELETE)
    Map<String, Object> deleteEntity(@PathVariable("entityID")String entityID, Map<String, String> body){
        HashMap<String, Object> response = new HashMap<>();
        String key = body.get("APIKey");
        APIKey keyObj = apiKeyRepository.findByHash(APIKey.generateHash(key));
        Entity entity = entityRepository.findByID(entityID);

        if(entity == null){
            response.put("response", "This entity does not exist.");
            return response;
        }

        if(keyObj == null || keyObj.getPrefix() != entity.getOwnerPrefix()){
            response.put("response", "Incorrect APIkey.");
            return response;
        }

        entityRepository.delete(entity);
        response.put("response", "Entity deleted.");
        return response;
    }

    public void saveEntity(Entity e) {
        entityRepository.save(e);
    }
}
