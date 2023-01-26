package com.company.electro_store.service.accountant;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.accountant.SalariesDao;
import com.company.electro_store.dao.persons.PersonDao;
import com.company.electro_store.entity.accountant.Salaries;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.service.Service;

import java.sql.Date;
import java.util.List;

public class SalariesService implements Service<Salaries> {
    Dao<Salaries> salariesDao = new SalariesDao();

    @Override
    public boolean saveOrUpdate(Salaries salaries) {
        return salariesDao.saveOrUpdate(salaries);
    }

    @Override
    public List<Salaries> readAll() {
        return salariesDao.readAll();
    }
    @Override
    public boolean delete(Salaries salaries) {
        return salariesDao.delete(salaries);
    }
    @Override
    public Salaries read(Integer id) {
        return salariesDao.read(id);
    }
    public Salaries read(Date date){
        SalariesDao s=new SalariesDao();
        return s.read(date);}
}
