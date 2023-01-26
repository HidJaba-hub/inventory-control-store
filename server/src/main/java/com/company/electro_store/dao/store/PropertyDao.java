package com.company.electro_store.dao.store;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.store.Product;
import com.company.electro_store.entity.store.Property;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PropertyDao implements Dao<Property> {
    @Override
    public boolean saveOrUpdate(Property property){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (property.getName() == null) {
                session.persist(property);
            } else {
                session.merge(property);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Property> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Property", Property.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Property read(Integer name) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Property.class, name);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    @Override
    public Property read(String name) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Property> cr = cb.createQuery(Property.class);
            Root<Property> root = cr.from(Property.class);
            //RackId rackId=new RackId(name,place);
            cr.select(root).where(cb.equal(root.get("name"), name));
            return session.createQuery(cr).getSingleResult();
        }catch(NoResultException e){
            System.out.println("not found");
            return null;
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
