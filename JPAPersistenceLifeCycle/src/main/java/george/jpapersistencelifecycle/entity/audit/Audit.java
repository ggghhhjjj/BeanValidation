/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package george.jpapersistencelifecycle.entity.audit;

import george.jpapersistencelifecycle.entity.Person;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class Audit {
    
    private static final String GEORGE = "George";
    private static final String SHUMAKOV = "Shumakov";
    
    /**
     * automatic property set before any database persistence
     * @param o
     */
    @PrePersist
    public void validatePersist(Person o) {
        System.out.println("Audit:");
        System.out.println("Persist " + o.toString());
        if (GEORGE.equals(o.getFirstName()) && SHUMAKOV.equals(o.getLastName())) {
            throw new ConstraintViolationException ("Can't set George Shumakov", null);
        }
    }
    
    @PreUpdate
    public void validateUpdate(Person o) {
        System.out.println("Audit:");
        System.out.println("Update " + o.toString());
    }
}
