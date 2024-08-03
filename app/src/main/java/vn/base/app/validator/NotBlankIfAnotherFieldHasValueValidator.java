package vn.base.app.validator;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Implementation of {@link NotBlankIfAnotherFieldHasValue} validator.
 **/
public class NotBlankIfAnotherFieldHasValueValidator
        implements ConstraintValidator<NotBlankIfAnotherFieldHasValue, String> {

    private String fieldName;
    private String expectedFieldValue;

    @Override
    public void initialize(NotBlankIfAnotherFieldHasValue annotation) {
        ConstraintValidator.super.initialize(annotation);
        this.fieldName = annotation.fieldName();
        this.expectedFieldValue = annotation.fieldValue();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {

        try {
            String fieldValue = BeanUtils.getProperty(value, fieldName);
            if (expectedFieldValue.equals(fieldValue)) {
                if (value == null || value.trim().equals("")) {
                    return false;
                }
                return true;
            } else {
                return true;
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }

    }

}