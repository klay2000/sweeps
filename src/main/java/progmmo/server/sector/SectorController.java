package progmmo.server.sector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmmo.server.config.MapConfig;
import progmmo.server.entity.Entity;
import progmmo.server.entity.EntityRepository;

import java.util.HashMap;

@RestController
@RequestMapping("/sectors")
public class SectorController{

    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private MapConfig mapConfig;

    @RequestMapping(value = "/{sectorID}", method = RequestMethod.GET)
    HashMap getEntities(@PathVariable("SectorID") String sectorID) {
        HashMap<String, Object> response = new HashMap<>();

        //TODO: check if sector exists, if it does not say so, same with generation

        response.put("entities", entityRepository.findBySector(sectorID));

        return response;
    }
}
