package progmmo.server.apikey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.*;
import progmmo.server.apikey.unconfirmedkey.UnconfirmedAPIKey;
import progmmo.server.apikey.unconfirmedkey.UnconfirmedAPIKeyRepository;
import progmmo.server.utils.EmailSender;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apikeys/")
public class APIKeyController {

    private static final int PREFIX_MAX_SIZE = 10; //Todo: convert to a setting.

    @Autowired
    private APIKeyRepository repository;

    @Autowired
    private UnconfirmedAPIKeyRepository unconfirmedRepository;

    @Autowired
    private EmailSender emailSender;

    @RequestMapping(value = "{prefix}", method = RequestMethod.POST)
    public Map<String, String> createAPIKey(@PathVariable("prefix") String prefix, @RequestBody Map<String, Object> body){

        String email = (String)body.get("email");

        HashMap response = new HashMap();

        // failure conditions
        if(prefix.length() > PREFIX_MAX_SIZE || prefix.length() < 1){
            response.put("response", "Prefix must be between 1 and " + PREFIX_MAX_SIZE + " characters.");
            return response;
        }

        if(repository.findByPrefix(prefix) != null || unconfirmedRepository.findByPrefix(prefix) != null){
            response.put("response", "This prefix is already in use, please pick another prefix.");
            return response;
        }

        String key = UnconfirmedAPIKey.generateRandomKey();

            emailSender.sendEmail(email, "API Key", "Hi " + prefix + "!\nYour APIkey is " + prefix+key
                    + " Please save this key somewhere as it is unrecoverable if lost!\nPlease send a POST request to" +
                    " /apikeys/" + prefix + "/confirm with a json containing your key and email address (as shown in " +
                    "the API docs) within an hour or before the next server reset to confirm your APIKey.");


        unconfirmedRepository.save(
                new UnconfirmedAPIKey(prefix, email, UnconfirmedAPIKey.generateHash(prefix, key))
        );

        response.put("response", "Success! You should receive an email at " + email + " with instructions to confirm" +
                " your APIkey shortly!");

        return response;
    }

    @RequestMapping(value = "{prefix}/confirm", method = RequestMethod.POST)
    public Map<String, String> confirm(@PathVariable("prefix")String prefix, @RequestBody Map<String, String> body){
        HashMap response = new HashMap();

        UnconfirmedAPIKey unconf = unconfirmedRepository.findByPrefix(prefix);

        if(unconf == null){
            response.put("response", "This prefix does not exist, are you sure you typed it right?");
        }

        if(!unconf.getHash().equals(UnconfirmedAPIKey.generateHash(body.get("key"))) || !unconf.getEmail().equals(body.get("email"))){
            response.put("response", "Incorrect key or email, are you sure you typed them right?");
        }

        repository.save(new APIKey(unconf.getPrefix(), unconf.getEmail(), unconf.getHash()));
        unconfirmedRepository.deleteByPrefix(prefix);

        response.put("response", "Success! Your key has been confirmed, you may now use it.");
        return response;
    }

}
