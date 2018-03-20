package org.vap.webapp.data;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public interface DataManager<T extends Persistent> {
    T getById(String id);

    T insert(T instance);
}
