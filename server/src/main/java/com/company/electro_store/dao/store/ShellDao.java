package com.company.electro_store.dao.store;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ShellDao implements Dao<Shell> {
    @Override
    public boolean saveOrUpdate(Shell shell){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            //session.persist(shell);
            if (shell.getShell_id() == null) {
                session.persist(shell);
            } else {
                session.merge(shell);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Shell> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Shell", Shell.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Shell read(String id) {
        Shell shell;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Shell> cr = cb.createQuery(Shell.class);
            Root<Shell> root = cr.from(Shell.class);
            cr.select(root).where(cb.equal(root.get("shell_id"), id));
            shell = session.createQuery(cr).getSingleResult();

            return shell;
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
