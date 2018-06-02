package org.vap.webapp.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A composite validator that contains other validators, enabling it to verify many validation rules at once.
 *
 * @author Vahe Pezeshkian
 * May 31, 2018
 */
public class CompositeValidator<T> implements Validator<T> {

    private List<Validator<T>> validators = new ArrayList<>();

    public CompositeValidator(List<Validator<T>> validators) {
        this.validators.addAll(validators);
    }

    public void addValidator(Validator<T> validator) {
        this.validators.add(validator);
    }

    @Override
    public void validate(T validationContext) throws ValidationException {
        List<String> messages = new ArrayList<>();

        validators.forEach(validator -> {
            try {
                validator.validate(validationContext);
            } catch (ValidationException e) {
                messages.add(e.getMessage());
            }
        });

        if (!messages.isEmpty()) {
            throw new ValidationException(messages.stream().collect(Collectors.joining("|")));
        }
    }
}
