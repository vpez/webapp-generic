package org.vap.webapp.data.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.Repository;

import java.util.List;
import java.util.function.Function;

/**
 * Data manager that enforces uniqueness on arbitrary object field(s) by delegating to a proxy collection.
 * Main objects are stored in the collection with the same name, but a new "proxy" collection is created to track
 * the ID mappings to enforce uniqueness by calculating a hash value of given fields.
 *
 * @author Vahe Pezeshkian
 * May 28, 2018
 */
public class UniqueFieldMongoRepository<T extends Persistent, U> extends AbstractMongoRepository<T> implements Repository<T> {

    private static final String REF_ID = "refId";

    /**
     * Main data manager to delegate object mapping
     */
    private Repository<T> wrappedRepository;

    /**
     * Function that extracts unique field(s)
     */
    private Function<T, U> keyExtractor;

    /**
     * Function that computes a unique hash from the key fields
     */
    private Function<U, String> keyMapper;

    public UniqueFieldMongoRepository(Class<T> type, MongoClient mongoClient, String database,
                                      Repository<T> repository,
                                      Function<T, U> keyExtractor,
                                      Function<U, String> keyMapper) {

        super(type, mongoClient, database);

        this.keyExtractor = keyExtractor;
        this.keyMapper = keyMapper;
        this.wrappedRepository = repository;
    }

    @Override
    public String getCollectionName() {
        return type.getSimpleName() + "Proxy";
    }

    @Override
    public T getById(String id) {
        return wrappedRepository.getById(id);
    }

    @Override
    public List<T> getAll() {
        return wrappedRepository.getAll();
    }

    @Override
    public List<T> getByValue(String attribute, Object value) {
        return wrappedRepository.getByValue(attribute, value);
    }

    @Override
    public T insert(T instance) {

        // Check if current key exists in proxy collection
        String refId = searchProxy(keyExtractor.apply(instance));
        if (refId != null) {
            return null;
        }

        T inserted = wrappedRepository.insert(instance);

        // Insert in proxy collection
        String uniqueKey = calculateUniqueKey(instance);
        Document document = new Document();
        document.put(KEY, uniqueKey);
        document.put(REF_ID, inserted.getId());
        getCollection().insertOne(document);

        return inserted;
    }

    @Override
    public T update(T instance) {

        // TODO update proxy collection on key change

        return wrappedRepository.update(instance);
    }

    @Override
    public boolean deleteById(String id) {

        T instance = getById(id);

        boolean deleteObject = wrappedRepository.deleteById(id);

        // Delete from proxy collection
        Document criteria = new Criteria(KEY).is(calculateUniqueKey(instance)).getCriteriaObject();
        DeleteResult deleteResult = getCollection().deleteOne(criteria);

        return deleteObject && deleteResult.getDeletedCount() > 0;
    }

    private String searchProxy(U value) {
        String uniqueKey = keyMapper.apply(value);
        Document criteria = new Criteria(KEY).is(uniqueKey).getCriteriaObject();
        Document document = getCollection().find(criteria).first();

        if (document == null) {
            return null;
        }

        return document.getString(REF_ID);
    }

    private String calculateUniqueKey(T instance) {
        return keyExtractor.andThen(keyMapper).apply(instance);
    }

}
