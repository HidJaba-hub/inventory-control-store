package com.company.electro_store.service.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.persons.PersonDao;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.service.Service;

import java.util.List;

public class PersonService implements Service<Person> {
    Dao<Person> personDao = new PersonDao();

    @Override
    public boolean saveOrUpdate(Person person) {
        return personDao.saveOrUpdate(person);
    }

    @Override
    public List<Person> readAll() {
        return personDao.readAll();
    }
    @Override
    public boolean delete(Person person) {
        return personDao.delete(person);
    }
    @Override
    public Person read(Integer id) {
        return personDao.read(id);
    }
}
