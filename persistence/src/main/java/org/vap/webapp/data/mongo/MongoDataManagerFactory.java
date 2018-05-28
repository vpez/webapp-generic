package org.vap.webapp.data.mongo;

import com.mongodb.MongoClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.UniqueFieldDataManager;

import java.util.Objects;
import java.util.function.Function;


/**
 * @author Vahe Pezeshkian
 * May 26, 2018
 */

@Component
public class MongoDataManagerFactory {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoConfig mongoConfig;

    public MongoDataManagerFactory(MongoClient mongoClient, MongoConfig mongoConfig) {
        this.mongoClient = mongoClient;
        this.mongoConfig = mongoConfig;
    }

    public <T extends Persistent> MongoDataManager<T> create(Class<T> persistentClass) {
        return new MongoDataManager<>(persistentClass, mongoClient, mongoConfig.getDatabase());
    }

    public <T extends Persistent, U> UniqueFieldDataManager<T, U> create(Class<T> persistentClass, Function<T, U> keyExtractor) {

        Function<U, String> keyMapper = attributeValue -> DigestUtils.md5Hex(Objects.toString(attributeValue));

        return new UniqueFieldMongoDataManager<>(
                persistentClass, mongoClient, mongoConfig.getDatabase(),
                keyExtractor, keyMapper);
    }
}
