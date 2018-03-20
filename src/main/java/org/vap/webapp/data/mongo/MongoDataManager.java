package org.vap.webapp.data.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.vap.webapp.data.DataManager;
import org.vap.webapp.data.Persistent;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public class MongoDataManager<T extends Persistent> implements DataManager<T> {

    private Class<T> type;
    private MongoClient mongoClient;

    MongoDataManager(Class<T> type, MongoClient mongoClient)  {
        this.type = type;
        this.mongoClient = mongoClient;
    }

    @Override
    public T getById(String id) {

        Document query = new Document();
        query.put("_id", new ObjectId(id));

        Document document = getCollection().find(query).first();

        T instance = new Gson().fromJson(document.toJson(), type);
        return populateId(instance, document);
    }

    @Override
    public T insert(T instance) {
        Document document = Document.parse( new Gson().toJson(instance));

        getCollection().insertOne(document);
        return populateId(instance, document);
    }

    public MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("demo").getCollection(type.getName());
    }

    private T populateId(T instance, Document document) {
        instance.setId(document.getObjectId("_id").toString());
        return instance;
    }
}
