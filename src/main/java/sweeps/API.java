package sweeps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sweeps.call_templates.AuthAPIKeyJSON;
import sweeps.call_templates.CommandBoiJSON;
import sweeps.call_templates.CreateBoiJSON;
import sweeps.call_templates.GetSectorJSON;

import java.security.NoSuchAlgorithmException;

@RestController
public class API {
    @RequestMapping(value = "/api/getMap", method = RequestMethod.GET)
    String getMap() throws JsonProcessingException {
        return Map.toJson();
    }

    @RequestMapping(value = "/api/getSector", method = RequestMethod.PUT)
    String getSector(@RequestBody GetSectorJSON json) throws JsonProcessingException, InvalidPositionException {
        return Map.getSector(json.x, json.y).toJson();
    }

    @RequestMapping(value = "/api/getNewAPIKey", method = RequestMethod.GET)
    String getNewAPIKey() throws NoSuchAlgorithmException, JsonProcessingException {
        return  new ObjectMapper().writeValueAsString(Profiles.makeAPIKey());
    }

    @RequestMapping(value = "/api/authenticateAPIKey", method = RequestMethod.PUT)
    String authenticateAPIKey(@RequestBody AuthAPIKeyJSON json) throws NoSuchAlgorithmException, JsonProcessingException {
        return new ObjectMapper().writeValueAsString(Profiles.authenticate(json.key));
    }

    @RequestMapping(value = "/api/createInitialBoi", method = RequestMethod.PUT)
    String createInitialBoi(@RequestBody CreateBoiJSON json) throws NoSuchAlgorithmException, JsonProcessingException, InvalidPositionException {
        Profile profile = Profiles.getProfile(json.key);
        if(profile.getOwned().size() == 0){
            Entity e = new Boi(json.x, json.y, json.sectorX, json.sectorY, 25);
            profile.getOwned().add(e);
            return new ObjectMapper().writeValueAsString(e);
        }
        return new ObjectMapper().writeValueAsString("failure");
    }

    @RequestMapping(value = "/api/commandBoi", method = RequestMethod.PUT)
    String commandBoi(@RequestBody CommandBoiJSON json) throws JsonProcessingException, NoSuchAlgorithmException, InvalidPositionException {
        Profile profile = Profiles.getProfile(json.key);

        Entity e = profile.getEntityByID(json.id);

        Boi.Direction direction;

        switch (json.direction){
            case "north":
                direction = Boi.Direction.north;
                break;

            case "south":
                direction = Boi.Direction.south;
                break;

            case "east":
                direction = Boi.Direction.east;
                break;

            case "west":
                direction = Boi.Direction.west;
                break;

            default:
                return new ObjectMapper().writeValueAsString("failure");
        }

        switch(json.command){
            case "move":
                ((Boi)e).moveBoi(direction);
                return new ObjectMapper().writeValueAsString("success");

            case "eat":
                ((Boi)e).eat(direction);
                return new ObjectMapper().writeValueAsString("success");

        return new ObjectMapper().writeValueAsString("failure");
    }



}
