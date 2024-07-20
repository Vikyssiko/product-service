package com.example.test_task.annotations;

import com.example.test_task.validators.PrecisionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PrecisionValidator.class)
public @interface Precision {
    int value() default 1;
    String message() default "must be not more than {value} digits after floating point";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
