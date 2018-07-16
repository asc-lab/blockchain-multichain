package pl.altkom.asc.lab.multichain.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Init user in in-memory database
 */
@Configuration
public class DatabaseInitConfig {

    @Autowired
    DatabaseInitConfig(InitialUserCreator creator) {
        creator.init();
    }
}
