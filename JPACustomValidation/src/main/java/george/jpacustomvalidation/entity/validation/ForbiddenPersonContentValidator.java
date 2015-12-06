/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package george.jpacustomvalidation.entity.validation;

import george.jpacustomvalidation.entity.Person;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import george.jpacustomvalidation.entity.validation.annotation.ForbiddenPersonContent;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class ForbiddenPersonContentValidator implements ConstraintValidator<ForbiddenPersonContent, Person> {

    private Iterable<FirstLastPair> forbiddenPairs = null;

    @Override
    public void initialize(ForbiddenPersonContent constraintAnnotation) {
        forbiddenPairs = Arrays.asList(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(Person value, ConstraintValidatorContext context) {
        if (null != forbiddenPairs) {
            final String first = value.getFirst();
            final String last = value.getLast();
            
            //handle LAZY loading or detachment
            PersistenceUtil putil = Persistence.getPersistenceUtil();
            if (!putil.isLoaded(value, "first") || !putil.isLoaded(value, "last")) {
                // don't validate the person
                return true;
            }
            
            for (final FirstLastPair pair : forbiddenPairs) {
                if (pair.getFirst().equals(first) && pair.getLast().equals(last)) {
                    return false;
                }
            }
        }
        return true;
    }

}
