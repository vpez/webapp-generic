package org.vap.webapp.service.user.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vap.webapp.model.User;
import org.vap.webapp.validator.ConditionalValidator;
import org.vap.webapp.validator.ValidationException;

/**
 * Validates that passwords are non-empty. "admin" user is exempt from this rule.
 *
 * @author Vahe Pezeshkian
 * May 31, 2018
 */

@Component
public class SimplePasswordValidator extends ConditionalValidator<User> implements UserValidator {

    @Value("${app.password.strategy}")
    private String strategy;

    @Override
    public boolean isApplicable(User user) {
        return "simple".equals(strategy) && !"admin".equals(user.getUsername());
    }

    @Override
    public void doValidate(User user) throws ValidationException {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }
    }
}
