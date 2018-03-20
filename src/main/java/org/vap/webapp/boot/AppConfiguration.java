package org.vap.webapp.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.vap.webapp.data.mongo.MongoConfig;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
@Configuration
public class AppConfiguration {

    @Configuration
    @ComponentScan(basePackages = "org.vap.webapp.controller")
    public static class Controller {
    }

    @Configuration
    @Import(MongoConfig.class)
    public static class MongoConfiguration {

    }
}
