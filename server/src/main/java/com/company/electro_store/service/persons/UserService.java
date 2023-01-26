package com.company.electro_store.service.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.persons.UserDao;
import com.company.electro_store.dao.persons.WorkDao;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.service.Service;

import java.util.List;

public class UserService implements Service<User> {
    Dao<User> userDao = new UserDao();

    @Override
    public boolean saveOrUpdate(User user) {
        return userDao.saveOrUpdate(user);
    }

    @Override
    public List<User> readAll() {
        return userDao.readAll();
    }
    @Override
    public boolean delete(User user) {
        return userDao.delete(user);
    }
    @Override
    public User read(Integer id) {
        return userDao.read(id);
    }
    @Override
    public User read(String login) {
        return userDao.read(login);
    }
    public User read(Person person) {
        return UserDao.read(person);
    }
}
