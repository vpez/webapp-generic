package org.vap.webapp.data;

/**
 * Common interface for data sources that enforce uniqueness on arbitrary fields
 *
 * @author Vahe Pezeshkian
 * May 28, 2018
 */
public interface UniqueFieldDataManager<T extends Persistent, U> extends DataManager<T> {

    /**
     * Queries for an object with the provided unique value
     * @param value The unique value of the defined field
     *
     * @return Instantiated record
     */
    T getUnique(U value);
}
