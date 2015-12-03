/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package george.jpacustomvalidation.entity.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
@Documented
@Constraint(validatedBy = FirstContentValidator.class)
@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstContent {
    String message() default "First name has unallowed content.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
    String[] value() default {"George", "Lili", "Dimana", "Viara"};
}
