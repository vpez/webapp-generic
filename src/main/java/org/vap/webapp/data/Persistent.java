package org.vap.webapp.data;

/**
 * @author Vahe Pezeshkian
 * March 20, 2018
 */
public abstract class Persistent {
    protected String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
