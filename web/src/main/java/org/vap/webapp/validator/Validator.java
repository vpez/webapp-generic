package org.vap.webapp.validator;

/**
 * @author Vahe Pezeshkian
 * May 31, 2018
 */
@FunctionalInterface
public interface Validator<T> {
    void validate(T validationContext) throws ValidationException;
}
