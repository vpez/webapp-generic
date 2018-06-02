package org.vap.webapp.validator;

/**
 * @author Vahe Pezeshkian
 * May 31, 2018
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
