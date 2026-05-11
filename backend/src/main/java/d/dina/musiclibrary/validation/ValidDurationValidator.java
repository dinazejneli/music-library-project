package d.dina.musiclibrary.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDurationValidator
        implements ConstraintValidator<ValidDuration, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || !value.matches("\\d{1,2}:\\d{2}")) {
            return false;
        }

        String[] parts = value.split(":");
        int seconds = Integer.parseInt(parts[1]);

        return seconds < 60;
    }
}

