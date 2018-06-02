package org.vap.webapp.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.vap.webapp.data.Repository;
import org.vap.webapp.data.mongo.MongoConfig;
import org.vap.webapp.data.mongo.MongoRepositoryFactory;
import org.vap.webapp.model.Product;
import org.vap.webapp.validator.ValidatorFactory;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
@Configuration
public class AppConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    @Configuration
    @ComponentScan(basePackages = "org.vap.webapp.data")
    @Import(MongoConfig.class)
    public static class DataComponents {
    }

    @Configuration
    @ComponentScan(basePackages = "org.vap.webapp.controller")
    public static class Controller {
    }

    @Configuration
    @ComponentScan(basePackages = "org.vap.webapp.service")
    public static class Service {
    }

    @Autowired
    private MongoRepositoryFactory dataManagerFactory;

    @Bean
    public Repository<Product> productDataManager() {
        return dataManagerFactory.create(Product.class);
    }

    @Bean
    public ValidatorFactory validatorFactory() {
        return new ValidatorFactory(applicationContext);
    }
}
