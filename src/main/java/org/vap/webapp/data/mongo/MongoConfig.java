package org.vap.webapp.data.mongo;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vap.webapp.data.Product;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient();
    }

    @Bean
    public MongoDataManager<Product> productDataManager() {
        return new MongoDataManager<>(Product.class, mongoClient());
    }
}
