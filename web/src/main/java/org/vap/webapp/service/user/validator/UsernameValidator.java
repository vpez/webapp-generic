package org.vap.webapp.service.user.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vap.webapp.model.User;
import org.vap.webapp.validator.ValidationException;

/**
 * Validates that username is not empty and not shorter than N characters.
 *
 * @author Vahe Pezeshkian
 * June 01, 2018
 */

@Component
public class UsernameValidator implements UserValidator {

    @Value("${app.username.length}")
    private int length;

    @Override
    public void validate(User user) throws ValidationException {

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new ValidationException("Username cannot be empty");
        }

        if (user.getUsername().length() < length) {
            throw new ValidationException("Username is too short");
        }
    }
}
