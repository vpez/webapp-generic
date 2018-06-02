package org.vap.webapp.data.mongo;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * MongoDB implementation of CRUD operations.
 * Every object type is stored in a separate collection, matching the class name.
 *
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public class MongoRepository<T extends Persistent> extends AbstractMongoRepository<T> implements Repository<T> {

    private static final JsonWriterSettings settings = JsonWriterSettings.builder()
            .int64Converter((value, writer) -> writer.writeNumber(value.toString()))
            .build();

    public MongoRepository(Class<T> type, MongoClient mongoClient, String database) {
        super(type, mongoClient, database);
    }

    @Override
    public String getCollectionName() {
        return type.getSimpleName();
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
        return fromDocuments(iterable);
    }

    @Override
    public List<T> getByValue(String attribute, Object value) {
        Document criteria = new Criteria(attribute).is(value).getCriteriaObject();
        final FindIterable<Document> iterable = getCollection().find(criteria);
        return fromDocuments(iterable);
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

    private T fromDocument(Gson gson, Document document) {
        if (document == null) {
            return null;
        }

        return populateId(gson.fromJson(document.toJson(settings), type), document);
    }

    private T populateId(T instance, Document document) {
        instance.setId(document.getObjectId(KEY).toString());
        return instance;
    }

    private List<T> fromDocuments(FindIterable<Document> documents) {
        Gson gson = new Gson();

        return StreamSupport.stream(documents.spliterator(), false)
                .map(document -> fromDocument(gson, document))
                .collect(Collectors.toList());
    }
}