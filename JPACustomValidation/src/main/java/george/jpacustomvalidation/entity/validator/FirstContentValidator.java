/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package george.jpacustomvalidation.entity.validator;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class FirstContentValidator implements ConstraintValidator<FirstContent, String> {
    private Iterable<String> allowedNames = null;
    
    @Override
    public void initialize(FirstContent constraintAnnotation) {
        allowedNames = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value) {
            return false;
        }
        
        if (null == allowedNames) {
            return true;
        }
        
        for (final String name : allowedNames) {
            if (name.equals(value)) return true;
        }
        
        return false;
    }
    
}
