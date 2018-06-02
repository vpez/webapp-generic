package org.vap.webapp.data.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.vap.webapp.data.Persistent;

/**
 * Common superclass for all MongoDB data managers, holding common info and methods.
 *
 * @author Vahe Pezeshkian
 * May 28, 2018
 */
public abstract class AbstractMongoRepository<T extends Persistent> {

    protected static final String KEY = "_id";

    protected Class<T> type;
    protected MongoClient mongoClient;
    protected String database;

    public AbstractMongoRepository(Class<T> type, MongoClient mongoClient, String database) {
        this.type = type;
        this.mongoClient = mongoClient;
        this.database = database;
    }

    public MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase(database).getCollection(getCollectionName());
    }

    /**
     * Provides the MongoDB collection name that stores object types
     *
     * @return Collection name
     */
    public abstract String getCollectionName();
}
