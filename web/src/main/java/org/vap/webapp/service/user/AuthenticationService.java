package org.vap.webapp.service.user;

import org.vap.webapp.model.User;

/**
 * Authentication service contract
 *
 * @author Vahe Pezeshkian
 */
public interface AuthenticationService {
    User register(String username, String password);

    User confirm(String username, String token);

    User login(String username, String password) throws AuthException;

    User logout(String username);
}
