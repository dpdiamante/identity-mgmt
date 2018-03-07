package dpd.accenture.identity.identitymgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("dpd.accenture.identity")
public class IdentityManager {

    public static void main(String args[]) {
        SpringApplication.run(IdentityManager.class, args);
    }
}
