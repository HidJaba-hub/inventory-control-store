package com.company.electro_store.service.persons;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.persons.PostDao;
import com.company.electro_store.dao.persons.WorkDao;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.service.Service;

import java.util.List;

public class WorkService implements Service<Work> {
    Dao<Work> workDao = new WorkDao();

    @Override
    public boolean saveOrUpdate(Work work) {
        return workDao.saveOrUpdate(work);
    }

    @Override
    public List<Work> readAll() {
        return workDao.readAll();
    }
    @Override
    public boolean delete(Work work) {
        return workDao.delete(work);
    }
    @Override
    public Work read(Integer id) {
        return workDao.read(id);
    }
}
