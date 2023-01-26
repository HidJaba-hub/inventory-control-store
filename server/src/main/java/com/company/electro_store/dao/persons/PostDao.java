package com.company.electro_store.dao.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PostDao implements Dao<Post> {
    @Override
    public boolean saveOrUpdate(Post post){
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (post.getPost_id() == null) {
                session.persist(post);
            } else {
                session.merge(post);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    @Override
    public List<Post> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Post", Post.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public Post read(Integer code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Post.class, code);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    @Override
    public Post read(String code) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Post.class, code);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
