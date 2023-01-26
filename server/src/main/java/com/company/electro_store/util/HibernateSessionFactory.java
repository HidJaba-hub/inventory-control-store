package com.company.electro_store.util;

import com.company.electro_store.entity.accountant.Salaries;
import com.company.electro_store.entity.accountant.Sales;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.entity.store.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Property.class);
                configuration.addAnnotatedClass(Rack.class);
                configuration.addAnnotatedClass(Shell.class);
                configuration.addAnnotatedClass(Work.class);
                configuration.addAnnotatedClass(Post.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Sales.class);
                configuration.addAnnotatedClass(Salaries.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
                
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return sessionFactory;
    }
}