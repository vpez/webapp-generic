package org.vap.webapp.service.user.validator;

import org.vap.webapp.model.User;
import org.vap.webapp.validator.Validator;

/**
 * Just a marker interface.
 *
 * All validators that implement this interface are loaded via the ValidatorFactory
 * @see org.vap.webapp.validator.ValidatorFactory
 *
 * Besides loading all validators, other similar marker interfaces can also be introduced
 * in order to group a set of related validators that should work together at a given phase.
 *
 * @author Vahe Pezeshkian
 * June 01, 2018
 */
public interface UserValidator extends Validator<User> {
}
