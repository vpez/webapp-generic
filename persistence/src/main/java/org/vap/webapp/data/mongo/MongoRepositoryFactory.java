package org.vap.webapp.data.mongo;

import com.mongodb.MongoClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.Repository;

import java.util.Objects;
import java.util.function.Function;


/**
 * This factory creates MongoDB-specific repository instances that store objects of each class
 * in a separate collection, named identical to the class name.
 *
 * @author Vahe Pezeshkian
 * May 26, 2018
 */

@Component
public class MongoRepositoryFactory {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoConfig mongoConfig;

    public MongoRepositoryFactory(MongoClient mongoClient, MongoConfig mongoConfig) {
        this.mongoClient = mongoClient;
        this.mongoConfig = mongoConfig;
    }

    /**
     * Create a MongoDB repository for objects of given class
     *
     * @param persistentClass Class of objects to work with.
     * @param <T> The class type
     * @return MongoDB repository
     */
    public <T extends Persistent> MongoRepository<T> create(Class<T> persistentClass) {
        return new MongoRepository<>(persistentClass, mongoClient, mongoConfig.getDatabase());
    }

    /**
     * Create a MongoDB repository for objects of given class, which enforces uniqueness of objects.
     *
     * @param persistentClass Class of objects to work with.
     * @param keyExtractor Extracts unique attributes of an object.
     * @param <T> The class type of objects
     * @param <U> The class type of unique attribute
     *
     * @return MongoDB repository
     */
    public <T extends Persistent, U> Repository<T> create(Class<T> persistentClass, Function<T, U> keyExtractor) {
        Repository<T> repository = create(persistentClass);
        Function<U, String> keyMapper = attributeValue -> DigestUtils.md5Hex(Objects.toString(attributeValue));
        return new UniqueFieldMongoRepository<>(persistentClass, mongoClient, mongoConfig.getDatabase(), repository, keyExtractor, keyMapper);
    }
}
