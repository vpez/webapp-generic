package org.vap.webapp.data;

/**
 * @author Vahe Pezeshkian
 * May 28, 2018
 */
public interface UniqueFieldDataManager<T extends Persistent, U> extends DataManager<T> {
    T getUnique(U value);
}
