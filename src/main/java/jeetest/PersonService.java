package jeetest;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PersonService {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    void postconstruct() {
        entityManager.persist(new Person("Horst"));
        entityManager.persist(new Person("Hans"));
    }

    public List<Person> fetchAllPersons() {
        return entityManager.createQuery("select f from Person f", Person.class).getResultList();
    }
}
