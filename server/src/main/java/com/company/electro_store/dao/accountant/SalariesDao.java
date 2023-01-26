package com.company.electro_store.dao.accountant;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.accountant.Salaries;
import com.company.electro_store.entity.accountant.Sales;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SalariesDao implements Dao<Salaries> {
    @Override
    public boolean saveOrUpdate(Salaries salaries){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (salaries.getSalary() == null) {
                session.persist(salaries);
            } else {
                session.merge(salaries);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Salaries> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Salaries", Salaries.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Salaries read(Integer code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Salaries.class, code);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public Salaries read(Date date) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Salaries> cr = cb.createQuery(Salaries.class);
            Root<Salaries> root = cr.from(Salaries.class);
            cr.select(root).where(cb.equal(root.get("date"), date));
            return session.createQuery(cr).getSingleResult();
        }catch (NoResultException e){//TODO добавить это везде
            System.out.println("Не найдено");
            return null;
        }
        catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
