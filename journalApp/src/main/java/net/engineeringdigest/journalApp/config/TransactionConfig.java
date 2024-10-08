package net.engineeringdigest.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement//for make single operation by using @Transactional
public class TransactionConfig {
    @Bean
    public PlatformTransactionManager platformTransactionManager(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
}
