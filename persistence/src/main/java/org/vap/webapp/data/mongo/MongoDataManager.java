package org.vap.webapp.data.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Persistent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public class MongoDataManager<T extends Persistent> implements DataManager<T> {

    private static final String KEY = "_id";

    private Class<T> type;
    private MongoClient mongoClient;
    private String database;

    MongoDataManager(Class<T> type, MongoClient mongoClient, String database)  {
        this.type = type;
        this.mongoClient = mongoClient;
        this.database = database;
    }

    @Override
    public T getById(String id) {
        Document criteria = new Criteria(KEY).is(new ObjectId(id)).getCriteriaObject();
        Document document = getCollection().find(criteria).first();

        return fromDocument(new Gson(), document);
    }

    @Override
    public List<T> getAll() {
        final FindIterable<Document> iterable = getCollection().find();
        Gson gson = new Gson();

        return StreamSupport.stream(iterable.spliterator(), false)
                .map(document -> fromDocument(gson, document))
                .collect(Collectors.toList());
    }

    @Override
    public T insert(T instance) {
        Document document = Document.parse(new Gson().toJson(instance));
        getCollection().insertOne(document);

        return populateId(instance, document);
    }

    @Override
    public T update(T instance) {
        Document criteria = new Criteria(KEY).is(new ObjectId(instance.getId())).getCriteriaObject();
        Document update = Document.parse(new Gson().toJson(instance));
        getCollection().replaceOne(criteria, update);

        return instance;
    }

    @Override
    public boolean deleteById(String id) {
        Document criteria = new Criteria(KEY).is(new ObjectId(id)).getCriteriaObject();
        DeleteResult deleteResult = getCollection().deleteOne(criteria);

        return deleteResult.getDeletedCount() > 0;
    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase(database).getCollection(type.getSimpleName());
    }

    private T fromDocument(Gson gson, Document document) {
        if (document == null) {
            return null;
        }

        return populateId(gson.fromJson(document.toJson(), type), document);
    }

    private T populateId(T instance, Document document) {
        instance.setId(document.getObjectId(KEY).toString());
        return instance;
    }
}
