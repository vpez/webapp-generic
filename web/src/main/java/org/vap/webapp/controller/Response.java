package org.vap.webapp.controller;

import java.io.Serializable;

/**
 * A wrapper entity to create consistent HTTP responses.
 *
 * @author Vahe Pezeshkian
 */
public class Response<T> implements Serializable {
    private T data;
    private boolean success;
    private String message;

    public Response(T data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public Response(Exception e) {
        this(null, false, e.getMessage());
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
