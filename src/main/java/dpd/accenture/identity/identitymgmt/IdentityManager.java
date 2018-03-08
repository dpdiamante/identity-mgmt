package dpd.accenture.identity.identitymgmt;

import dpd.accenture.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("dpd.accenture.identity")
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
public class IdentityManager {

    public static void main(String args[]) {
        SpringApplication.run(IdentityManager.class, args);
    }


}
