package bzb.server.controller;

import bzb.server.model.User;
import bzb.server.service.impl.UserService;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET, path = "/verify/{processId}/{username}", produces = "application/json")
    public @ResponseBody
    ResponseEntity get(@PathVariable("processId") String processId, @PathVariable("username") String username) {

        System.out.println("USAO U VERIFIKACIJU");
        String hash = runtimeService.getVariable(processId,"hash").toString();
        User user = userService.findOneByUsername(username);

        if(BCrypt.checkpw(username,hash)){
            user.setAktiviran(true);
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
