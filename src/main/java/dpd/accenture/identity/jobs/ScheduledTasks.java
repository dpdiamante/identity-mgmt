package dpd.accenture.identity.jobs;

import dpd.accenture.identity.domain.User;
import dpd.accenture.identity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private UserRepository repository;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(fixedRate = 5000)
    public void generateUserListReport() {
        for (User systemUser : repository.findAll()) {
            log.info(systemUser.toString());
        }
    }
}
