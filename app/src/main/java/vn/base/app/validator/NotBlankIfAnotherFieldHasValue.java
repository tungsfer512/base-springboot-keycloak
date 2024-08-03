package vn.base.app.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Repeatable;

/**
 * Validates that field {@code dependFieldName} is not null if
 * field {@code fieldName} has value {@code fieldValue}.
 **/
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Repeatable(NotBlankIfAnotherFieldHasValue.List.class) // only with hibernate-validator >= 6.x
@Constraint(validatedBy = NotBlankIfAnotherFieldHasValueValidator.class)
@Documented
public @interface NotBlankIfAnotherFieldHasValue {

    String fieldName();

    String fieldValue();

    String message() default "{NotBlankIfAnotherFieldHasValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ TYPE, FIELD, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotBlankIfAnotherFieldHasValue[] value();
    }

}