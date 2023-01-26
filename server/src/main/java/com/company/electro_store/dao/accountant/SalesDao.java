package com.company.electro_store.dao.accountant;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.accountant.Sales;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.store.Property;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SalesDao implements Dao<Sales> {
    @Override
    public boolean saveOrUpdate(Sales sales){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (sales.getSalesId() == null) {
                session.persist(sales);
            } else {
                session.merge(sales);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Sales> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Sales", Sales.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Sales read(Integer code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Sales.class, code);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public Sales read(Date date) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Sales> cr = cb.createQuery(Sales.class);
            Root<Sales> root = cr.from(Sales.class);
            cr.select(root).where(cb.equal(root.get("date"), date));
            return session.createQuery(cr).getSingleResult();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
