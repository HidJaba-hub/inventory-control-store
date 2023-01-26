package com.company.electro_store.dao.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PersonDao implements Dao<Person> {
    @Override
    public boolean saveOrUpdate(Person person){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (person.getPersonId() == null) {
                session.persist(person);
            } else {
                session.merge(person);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Person> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Person", Person.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Person read(Integer code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Person.class, code);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
