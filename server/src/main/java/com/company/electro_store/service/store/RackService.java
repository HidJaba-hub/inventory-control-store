package com.company.electro_store.service.store;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.store.PropertyDao;
import com.company.electro_store.dao.store.RackDao;
import com.company.electro_store.entity.store.Property;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.service.Service;

import java.util.List;

public class RackService implements Service<Rack> {
    Dao<Rack> rackDao = new RackDao();

    @Override
    public boolean saveOrUpdate(Rack rack) {
        return rackDao.saveOrUpdate(rack);
    }

    @Override
    public List<Rack> readAll() {
        return rackDao.readAll();
    }
    @Override
    public boolean delete(Rack rack) {
        return rackDao.delete(rack);
    }
    @Override
    public Rack read(String name) {
        return rackDao.read(name);
    }
    @Override
    public Rack read(String name, Rack.Place place) {
        return rackDao.read(name, place);
    }
}
