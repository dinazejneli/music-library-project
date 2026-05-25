package d.dina.musiclibrary.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDurationValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDuration {

    String message() default "Duration must be in format mm:ss";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
