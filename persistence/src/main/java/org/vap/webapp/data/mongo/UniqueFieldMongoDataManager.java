package org.vap.webapp.data.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.UniqueFieldDataManager;

import java.util.List;
import java.util.function.Function;

/**
 * @author Vahe Pezeshkian
 * May 28, 2018
 */
public class UniqueFieldMongoDataManager<T extends Persistent, U> implements UniqueFieldDataManager<T, U> {

    private Class<T> type;
    private MongoClient mongoClient;
    private String database;

    private DataManager<T> wrappedDataManager;
    private Function<T, U> keyExtractor;
    private Function<U, String> keyMapper;

    UniqueFieldMongoDataManager(Class<T> type, MongoClient mongoClient, String database,
                                Function<T, U> keyExtractor,
                                Function<U, String> keyMapper) {

        this.type = type;
        this.mongoClient = mongoClient;
        this.database = database;

        this.keyExtractor = keyExtractor;
        this.keyMapper = keyMapper;
        this.wrappedDataManager = new MongoDataManager<>(type, mongoClient, database);
    }

    @Override
    public T getUnique(U value) {

        // Lookup in proxy collection
        String refId = searchProxy(value);

        return wrappedDataManager.getById(refId);
    }

    @Override
    public T getById(String id) {
        return wrappedDataManager.getById(id);
    }

    @Override
    public List<T> getAll() {
        return wrappedDataManager.getAll();
    }

    @Override
    public T insert(T instance) {

        // Check if current key exists in proxy collection
        String refId = searchProxy(keyExtractor.apply(instance));
        if (refId != null) {
            return null;
        }

        T inserted = wrappedDataManager.insert(instance);

        // Insert in proxy collection
        String uniqueKey = calculateUniqueKey(instance);
        Document document = new Document();
        document.put("_id", uniqueKey);
        document.put("refId", inserted.getId());
        getProxyCollection().insertOne(document);

        return inserted;
    }

    @Override
    public T update(T instance) {
        return wrappedDataManager.update(instance);
    }

    @Override
    public boolean deleteById(String id) {

        T instance = getById(id);

        boolean deleteObject = wrappedDataManager.deleteById(id);

        // Delete from proxy collection
        Document criteria = new Criteria("_id").is(calculateUniqueKey(instance)).getCriteriaObject();
        DeleteResult deleteResult = getProxyCollection().deleteOne(criteria);

        return deleteObject && deleteResult.getDeletedCount() > 0;
    }

    private String searchProxy(U value) {
        String uniqueKey = keyMapper.apply(value);
        Document criteria = new Criteria("_id").is(uniqueKey).getCriteriaObject();
        Document document = getProxyCollection().find(criteria).first();

        if (document == null) {
            return null;
        }

        return document.getString("refId");
    }

    private String calculateUniqueKey(T instance) {
        return keyExtractor.andThen(keyMapper).apply(instance);
    }

    private MongoCollection<Document> getProxyCollection() {
        return mongoClient.getDatabase(database).getCollection(type.getSimpleName() + "Proxy");
    }
}
