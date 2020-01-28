package progmmo.server.apikey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import progmmo.server.utils.EmailSender;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/apikeys/")
public class APIKeyController {

    private static final int PREFIX_MAX_SIZE = 10; //Todo: convert to a setting.

    @Autowired
    private APIKeyRepository repository;

    @Autowired
    private EmailSender emailSender;

    @RequestMapping(value = "{prefix}", method = RequestMethod.POST)
    public Map<String, String> createAPIKey(@PathVariable("prefix") String prefix, @RequestBody Map<String, Object> body){

        String email = (String)body.get("email");

        HashMap response = new HashMap();

        if(prefix.length() > PREFIX_MAX_SIZE || prefix.length() < 1){
            response.put("response", "Prefix must be between 1 and " + PREFIX_MAX_SIZE + " characters.");
            return response;
        }

        String key = APIKey.generateRandomKey();

        if(repository.findByPrefix(prefix) != null){
            response.put("response", "This prefix is already in use, please pick another prefix.");
            return response;
        }

        try {
            emailSender.sendEmail(email, "API Key", "Hi " + prefix + "!\nYour APIKey is " + prefix+key + " Please save this key somewhere as it is unrecoverable if lost!");
            repository.save(new APIKey(prefix, email, APIKey.generateHash(prefix, key)));
        } catch (MessagingException e){
            e.printStackTrace();
        }


        response.put("response", "Success! You should receive an email at " + email + " with your api key shortly!");

        return response;
    }


}
