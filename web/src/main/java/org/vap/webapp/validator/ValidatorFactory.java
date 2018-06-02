package org.vap.webapp.validator;

import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Map;

/**
 * A factory to provide all matching validators of a certain class type via a single interface.
 *
 * @author Vahe Pezeshkian
 * June 02, 2018
 */

public class ValidatorFactory {

    private ApplicationContext applicationContext;

    public ValidatorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Combines all matching validators for the provided class type and builds a composite validator that
     * contains all of them.
     * @param validatorClass A marker interface that specifies Validator<T>.
     * @param <T> The class type to validate.
     * @param <U> Common marker interface for all matching interfaces.
     *
     * @return A composite validator for type T
     */
    public <T, U extends Validator<T>> Validator<T> getValidator(Class<U> validatorClass) {
        Map<String, U> beansOfType = applicationContext.getBeansOfType(validatorClass);
        CompositeValidator<T> validator = new CompositeValidator<>(new ArrayList<>());
        beansOfType.values().forEach(validator::addValidator);
        return validator;
    }
}
