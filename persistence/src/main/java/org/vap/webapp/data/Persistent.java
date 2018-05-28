package org.vap.webapp.data;

/**
 * Common superclass for any object type to work with a data source
 * All records that extend this class will get a unique ID
 *
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public abstract class Persistent {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
