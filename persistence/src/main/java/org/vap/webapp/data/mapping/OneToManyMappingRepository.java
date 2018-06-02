package org.vap.webapp.data.mapping;

import org.vap.webapp.data.Persistent;
import org.vap.webapp.data.Repository;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A decorator that builds upon two other repository instances that provides a one-to-many object persistence
 * functionality in a way that internal details are hidden from the client code.
 *
 * @author Vahe Pezeshkian
 * June 02, 2018
 */
public class OneToManyMappingRepository<T extends Persistent, U extends Persistent> implements Repository<T> {

    private String foreignKey;
    private BiConsumer<T, List<U>> setter;
    private Function<T, List<U>> getter;
    private Repository<T> parent;
    private Repository<U> referenced;

    public OneToManyMappingRepository(String foreignKey, Function<T, List<U>> getter, BiConsumer<T,List<U>> setter,
                                      Repository<T> parent, Repository<U> referenced) {
        this.foreignKey = foreignKey;
        this.getter = getter;
        this.setter = setter;
        this.parent = parent;
        this.referenced = referenced;
    }

    @Override
    public T getById(String id) {
        T instance = parent.getById(id);
        return loadCollection(instance);
    }

    @Override
    public List<T> getAll() {
        List<T> list = parent.getAll();
        return list.stream().map(this::loadCollection).collect(Collectors.toList());
    }

    @Override
    public List<T> getByValue(String attribute, Object value) {
        List<T> list = parent.getByValue(attribute, value);
        return list.stream().map(this::loadCollection).collect(Collectors.toList());
    }

    @Override
    public T insert(T instance) {
        return parent.insert(instance);
        // TODO insert referenced items
    }

    @Override
    public T update(T instance) {
        parent.update(instance);

        List<U> collection = referenced.getByValue(foreignKey, instance.getId());

        // TODO supports only addition, missing: removing from collection
        getter.apply(instance)
                .stream()
                .filter(item -> !collection.contains(item))
                .forEach(item -> referenced.insert(item));

        return instance;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    private T loadCollection(T instance) {
        List<U> collection = referenced.getByValue(foreignKey, instance.getId());
        setter.accept(instance, collection);
        return instance;
    }
}
