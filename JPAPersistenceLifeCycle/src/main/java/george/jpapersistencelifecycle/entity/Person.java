/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package george.jpapersistencelifecycle.entity;

import george.jpapersistencelifecycle.entity.audit.Audit;
import george.jpapersistencelifecycle.service.exceptions.IllegalOrphanException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import org.eclipse.persistence.exceptions.ValidationException;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
@Entity
@Table(name = "PERSON_LIFE_CICLE")
@EntityListeners (Audit.class)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    public Person() {
        this("", "");
    }

    public Person(final String firstName, final String lastName) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s : id=%d, firstName=%s, lastName=%s", super.toString(), id, firstName, lastName);
    }

    @PrePersist
    public void prePersist() {
        System.out.println("------------- PrePersist -------------");
    }

    @PostPersist
    public void postPersist() {
        System.out.println("------------- PostPersist -------------");
    }

    @PostLoad
    public void postLoad() {
        System.out.println("------------- PostLoad -------------");
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("------------- PreUpdate -------------");
    }

    @PostUpdate
    public void postUpdate() {
        System.out.println("------------- PostUpdate -------------");
    }

    @PreRemove
    public void preRemove() {
        System.out.println("------------- PreRemove -------------");
    }

    @PostRemove
    public void postRemove() {
        System.out.println("------------- PostRemove -------------");
    }
}
