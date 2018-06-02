package org.vap.webapp.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.vap.webapp.data.Repository;
import org.vap.webapp.data.UserJdbcRepository;
import org.vap.webapp.data.UserLoginJdbcRepository;
import org.vap.webapp.data.mongo.MongoRepositoryFactory;
import org.vap.webapp.data.mapping.OneToManyMappingRepository;
import org.vap.webapp.model.User;
import org.vap.webapp.service.user.validator.UserValidator;
import org.vap.webapp.validator.Validator;
import org.vap.webapp.validator.ValidatorFactory;

import javax.sql.DataSource;

/**
 * Provides and manages all Spring-contained beans that are specific to UserAuthenticationService
 * @see UserAuthenticationService
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
@Component
public class UserServiceConfiguration {

    @Value("${repo.config.user}")
    private String userRepo;

    @Autowired
    private MongoRepositoryFactory mongoRepositoryFactory;
    @Autowired
    private ValidatorFactory validatorFactory;
    @Autowired
    private DataSource dataSource;

    /**
     * Configures and provides a repository instance for User class
     * To switch from a MySQL database to MongoDB, or vice-versa, or switch from
     * a 1-to-many mapping strategy to an embedded document store, modify the way this method
     * builds and provides the repository instance.
     *
     * @return A repository to work with User objects at persistent level
     */
    @Bean
    public Repository<User> userRepository() {
        if ("mysql".equals(userRepo)) {
            UserJdbcRepository parent = new UserJdbcRepository(dataSource);
            UserLoginJdbcRepository referenced = new UserLoginJdbcRepository(dataSource);
            return new OneToManyMappingRepository<>("userId", User::getUserLogins, User::setUserLogins, parent, referenced);
        }

        // Default DB type is Mongo
        return mongoRepositoryFactory.create(User.class, User::getUsername);
    }

    @Bean
    public Validator<User> userValidator() {
        return validatorFactory.getValidator(UserValidator.class);
    }
}
