package com.company.electro_store.dao.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.RackId;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {
    @Override
    public boolean saveOrUpdate(User user){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (user.getUserId() == null) {
                session.persist(user);
            } else {
                session.merge(user);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<User> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM User", User.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public User read(Integer code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(User.class, code);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    @Override
    public User read(String login) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("login"), login));
            return session.createQuery(cr).getSingleResult();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public static User read(Person person) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("person"), person));
            return session.createQuery(cr).getSingleResult();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
