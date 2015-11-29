/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package george.jpapersistencelifecycle.entity.audit;

import george.jpapersistencelifecycle.entity.Person;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class Audit {
    /**
     * automatic property set before any database persistence
     */
    @PreUpdate
    @PrePersist
    public void setLastUpdate(Person o) {
        System.out.println("Audit:");
        System.out.println(o.toString());
    }
    
}
