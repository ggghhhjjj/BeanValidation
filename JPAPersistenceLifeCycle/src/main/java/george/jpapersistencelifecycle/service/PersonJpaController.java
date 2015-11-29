/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package george.jpapersistencelifecycle.service;

import george.jpapersistencelifecycle.entity.Person;
import george.jpapersistencelifecycle.service.exceptions.IllegalOrphanException;
import george.jpapersistencelifecycle.service.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.exceptions.ValidationException;

/**
 *
 * @author George Shumakov <george.shumakov@gmail.com>
 */
public class PersonJpaController extends Thread implements Serializable {

    private EntityManagerFactory emf = null;

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("JPAPersistenceLifeCycle");
            PersonJpaController ctrl = new PersonJpaController(emf);

            Person p1 = new Person("George", "Shumakov");
            ctrl.create(p1);
            System.out.println("p1 " + p1);
            Person p2 = new Person("Dimana", "Shumakova");
            p2.setId(p1.getId());
            try {
                ctrl.edit(p2);
                System.out.println("p1 " + p1);
                System.out.println("p2 " + p2);
                ctrl.destroy(p2.getId());
                System.out.println("p1 " + p1);
                System.out.println("p2 " + p2);
                Person p3 = ctrl.findPerson(p1.getId());
                System.out.println("p3 " + p3);

            } catch (NonexistentEntityException ex) {
                Logger.getLogger(PersonJpaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            closeEMF(emf);
        }

    }

    public PersonJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void run() {
        closeEMF(emf);
    }

    private static void closeEMF(EntityManagerFactory emf) {
        System.out.println("Shutdown hook started...");
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public void create(Person person) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } catch (IllegalOrphanException ex) {
            System.out.println("---------- validation exception -----------");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Person person) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            person = em.merge(person);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = person.getId();
                if (findPerson(id) == null) {
                    throw new NonexistentEntityException("The person with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Person person;
            try {
                person = em.getReference(Person.class, id);
                person.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The person with id " + id + " no longer exists.", enfe);
            }
            em.remove(person);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Person> findPersonEntities() {
        return findPersonEntities(true, -1, -1);
    }

    public List<Person> findPersonEntities(int maxResults, int firstResult) {
        return findPersonEntities(false, maxResults, firstResult);
    }

    private List<Person> findPersonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Person.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Person findPerson(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Person.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Person> rt = cq.from(Person.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
