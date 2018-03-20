package org.vap.webapp.data.mongo;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.vap.webapp.data.Product;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(host);
    }

    @Bean
    public MongoDataManager<Product> productDataManager() {
        return new MongoDataManager<>(Product.class, mongoClient(), database);
    }
}
