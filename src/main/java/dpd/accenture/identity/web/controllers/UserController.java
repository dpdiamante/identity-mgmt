package dpd.accenture.identity.web.controllers;

import dpd.accenture.identity.web.resources.Credentials;
import dpd.accenture.identity.web.resources.UserResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody UserResource user){
        return new ResponseEntity("User created successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity authenticate(@RequestBody Credentials credentials){
        return new ResponseEntity("User created successfully", HttpStatus.OK);
    }

}
