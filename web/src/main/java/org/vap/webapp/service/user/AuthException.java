package org.vap.webapp.service.user;

/**
 * @author Vahe Pezeshkian
 * May 28, 2018
 */
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
