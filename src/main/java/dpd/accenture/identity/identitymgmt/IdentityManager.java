package dpd.accenture.identity.identitymgmt;

import dpd.accenture.identity.domain.User;
import dpd.accenture.identity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("dpd.accenture.identity")
@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@EnableScheduling
public class IdentityManager {

    public static void main(String args[]) {
        SpringApplication.run(IdentityManager.class, args);
    }


    @Bean
    CommandLineRunner init(final UserRepository userRepository) {

        return new CommandLineRunner() {

            @Override
            public void run(String... arg0) throws Exception {
                User domainUser = new User();
                domainUser.setName("Administrator");
                domainUser.setUsername("admin");
                domainUser.setPassword("password");
                domainUser.setAdmin(true);

                userRepository.save(domainUser);
            }

        };

    }
}
