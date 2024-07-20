package com.example.test_task.validators;

import com.example.test_task.annotations.Precision;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PrecisionValidator implements ConstraintValidator<Precision, Double> {
    int precision;

    @Override
    public void initialize(Precision constraintAnnotation) {
        this.precision = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value.toString().split("\\.")[1].length() <= this.precision;
    }
}
