package org.vap.webapp.data;

import java.util.List;

/**
 * Common interface to perform basic CRUD operations on a data source
 *
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public interface Repository<T extends Persistent> {
    T getById(String id);

    List<T> getAll();

    List<T> getByValue(String attribute, Object value);

    T insert(T instance);

    T update(T instance);

    boolean deleteById(String id);
}
