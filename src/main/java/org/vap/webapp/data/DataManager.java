package org.vap.webapp.data;

import java.util.List;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public interface DataManager<T extends Persistent> {
    T getById(String id);

    List<T> getAll();

    T insert(T instance);
}
