package dpd.accenture.identity.web.controllers;

import dpd.accenture.identity.domain.User;
import dpd.accenture.identity.repository.UserRepository;
import dpd.accenture.identity.web.resources.Credentials;
import dpd.accenture.identity.web.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody UserResource user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User domainUser = new User();
        domainUser.setName(user.getName());
        domainUser.setUsername(user.getUsername());
        domainUser.setEmail(user.getEmail());
        domainUser.setPassword(user.getPassword());

        repository.save(domainUser);

        User retrievedUser = repository.findByUsername(user.getUsername());
        System.out.println(retrievedUser.getName());

        return new ResponseEntity("User created successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    public Iterable<User> list(Model model) {
        Iterable<User> userList = repository.findAll();
        return userList;
    }

    @RequestMapping(value = "/admin/permission", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity modifyPermission(@RequestBody UserResource user){
        return new ResponseEntity("User created successfully", HttpStatus.OK);
    }

}
