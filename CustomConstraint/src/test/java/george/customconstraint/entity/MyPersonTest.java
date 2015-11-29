/*
 * Copyright (c) 2015, George Shumakov <george.shumakov@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package george.customconstraint.entity;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class MyPersonTest {

    public MyPersonTest() {
    }
    private static ValidatorFactory vf;
    private static Validator validator;
    private static MyAddress address;
    private static MyPerson person;

    /**
     * Setting up the validator and model data.
     */
    @BeforeClass
    public static void setup() {
        vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
        address = new MyAddress("#12, 4th Main", "XYZ Layout", "Bangalore", "Karnataka", "India", "560045");
        person = new MyPerson("First Name", "Last Name", address);
    }

    /**
     * Validating the model data which has correct values.
     */
    @Test
    public void testCorrectAddress() {
        System.out.println("********** Running validator with corret address **********");

        Set<ConstraintViolation<MyPerson>> violations = validator.validate(person);

        assertEquals(violations.size(), 0);
    }

    /**
     * Validating the model data which has incorrect values.
     */
    @Test
    public void testInvalidAddress() {
        System.out.println("********** Running validator against wrong address **********");

        //setting address itself as null
        person.setAddress(null);
        validateAndPrintErrors();
        //One of the address fields as null.
        address.setCity(null);
        person.setAddress(address);
        validateAndPrintErrors();

        //Setting pin code less than 6 characters.
        address.setPinCode("123");
        address.setCity("Bangalore");
        validateAndPrintErrors();

        //Setting country name with less  than 4 characters
        address.setPinCode("123456");
        address.setCountry("ABC");
        validateAndPrintErrors();

    }

    private void validateAndPrintErrors() {
        Set<ConstraintViolation<MyPerson>> violations = validator.validate(person);

        for (ConstraintViolation<MyPerson> viol : violations) {
            System.out.println(viol.getMessage());
        }
        assertEquals(1, violations.size());
    }

}
