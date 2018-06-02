package org.vap.webapp.validator;

/**
 * A conditional validator will try to validate the provided object only of certain preconditions are met.
 *
 * @author Vahe Pezeshkian
 * May 31, 2018
 */
public abstract class ConditionalValidator<T> implements Validator<T> {

    public abstract boolean isApplicable(T validationContext);

    @Override
    public void validate(T validationContext) throws ValidationException {
        if (isApplicable(validationContext)) {
            doValidate(validationContext);
        }
    }

    public abstract void doValidate(T validationContext) throws ValidationException;
}
