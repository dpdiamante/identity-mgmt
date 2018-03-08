package dpd.accenture.identity.web.controllers;

import dpd.accenture.identity.domain.User;
import dpd.accenture.identity.repository.UserRepository;
import dpd.accenture.identity.web.resources.PermissionChange;
import dpd.accenture.identity.web.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity create(@RequestBody UserResource user){
                User domainUser = new User();
        domainUser.setName(user.getName());
        domainUser.setUsername(user.getUsername());
        domainUser.setEmail(user.getEmail());
        domainUser.setPassword(user.getPassword());

        if (repository.findByUsername(user.getUsername()) == null) {
            repository.save(domainUser);
            return new ResponseEntity("User created successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity("User already exists", HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity update(@RequestBody UserResource user){
        User domainUser = new User();
        domainUser.setName(user.getName());
        domainUser.setUsername(user.getUsername());
        domainUser.setEmail(user.getEmail());
        domainUser.setPassword(user.getPassword());

        String loggedIn = SecurityContextHolder.getContext().getAuthentication().getName();

        if (repository.findByUsername(user.getUsername()) != null) {

            if (loggedIn.equals(user.getUsername())) {
                repository.save(domainUser);
                return new ResponseEntity("User updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity("Cannot edit your own user", HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity("User does not exists", HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/delete/{username}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable String username){

        if (!hasRole("ADMIN")) {
            return new ResponseEntity("Deletion of user not allowed for current role", HttpStatus.FORBIDDEN);
        }

        User domainUser = repository.findByUsername(username);

        if (domainUser == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }

        repository.delete(domainUser);

        return new ResponseEntity("user deleted successfully", HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    public Iterable<User> list(Model model) {
        Iterable<User> userList = repository.findAll();
        return userList;
    }

    @RequestMapping(value = "/admin/permission", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity modifyPermission(@RequestBody PermissionChange permissionChange){

        if (!hasRole("ADMIN")) {
            return new ResponseEntity("Change of permission ont allowed", HttpStatus.FORBIDDEN);
        }

        User user = repository.findByUsername(permissionChange.getUsername());

        if (user == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }

        user.setAdmin(permissionChange.isAdmin());

        repository.save(user);

        return new ResponseEntity("Permission changed successfully", HttpStatus.OK);
    }

    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

}
