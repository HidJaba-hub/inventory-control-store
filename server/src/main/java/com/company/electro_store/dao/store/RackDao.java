package com.company.electro_store.dao.store;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.RackId;
import com.company.electro_store.entity.store.Shell;
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

public class RackDao implements Dao<Rack> {
    @Override
    public boolean saveOrUpdate(Rack rack){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (rack.getRackId().getName() == null) {
                session.persist(rack);
            } else {
                session.merge(rack);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Rack> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Rack", Rack.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Rack read(String name) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Rack> cr = cb.createQuery(Rack.class);
            Root<Rack> root = cr.from(Rack.class);
            //RackId rackId=new RackId(name,place);
            cr.select(root).where(cb.equal(root.get("rackId").get("name"), name));
            return session.createQuery(cr).getSingleResult();
            /*RackId rackId=session.get(RackId.class, name);
            return session.get(Rack.class, rackId);*/
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    @Override
    public Rack read(String name, Rack.Place place) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Rack> cr = cb.createQuery(Rack.class);
            Root<Rack> root = cr.from(Rack.class);
            RackId rackId=new RackId(name,place);
            cr.select(root).where(cb.equal(root.get("rackId"), rackId));
            return session.createQuery(cr).getSingleResult();
        } catch (NoResultException e){
            System.out.println("not found");
            return null;
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
