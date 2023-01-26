package com.company.electro_store.dao;

import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.store.Product;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public interface Dao<T> {
    List<T> readAll();
    boolean saveOrUpdate(T entity) ;

    default T read(Integer obj){return null;}

    default T read(String obj){return null;}
    default T read(String obj, Rack.Place place) {return null;}

    default boolean delete(T entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
