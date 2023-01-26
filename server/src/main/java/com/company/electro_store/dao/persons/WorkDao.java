package com.company.electro_store.dao.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class WorkDao implements Dao<Work> {
    @Override
    public boolean saveOrUpdate(Work work){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (work.getWorkId() == null) {
                session.persist(work);
            } else {
                session.merge(work);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Work> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Work", Work.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Work read(Integer code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Work.class, code);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
