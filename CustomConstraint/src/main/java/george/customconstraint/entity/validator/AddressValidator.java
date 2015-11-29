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
package george.customconstraint.entity.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import george.customconstraint.entity.MyAddress;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class AddressValidator implements ConstraintValidator<Address, MyAddress> {

    @Override
    public void initialize(Address constraintAnnotation) {
    }

    /**
     * <pre>
     * 1. The address should not be null.
     * 2. The address should have all the data values specified.
     * 3. Pin code in the address should be of atleast 6 characters.
     * 4. The country in the address should be of atleast 4 characters.
     * </pre>
     */
    @Override
    public boolean isValid(MyAddress value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.getCity() == null || value.getCountry() == null || value.getLocality() == null
                || value.getPinCode() == null || value.getState() == null || value.getStreetAddress() == null) {
            return false;
        }

        if (value.getPinCode().length() < 6) {
            return false;
        }

        return value.getCountry().length() >= 4;
    }
}
