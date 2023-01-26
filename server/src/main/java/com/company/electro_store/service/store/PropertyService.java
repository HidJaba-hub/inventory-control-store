package com.company.electro_store.service.store;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.store.ProductDao;
import com.company.electro_store.dao.store.PropertyDao;
import com.company.electro_store.entity.store.Product;
import com.company.electro_store.entity.store.Property;
import com.company.electro_store.service.Service;

import java.util.List;

public class PropertyService implements Service<Property> {
    Dao<Property> propertyDao = new PropertyDao();

    @Override
    public boolean saveOrUpdate(Property property) {
        return propertyDao.saveOrUpdate(property);
    }

    @Override
    public List<Property> readAll() {
        return propertyDao.readAll();
    }
    @Override
    public boolean delete(Property property) {
        return propertyDao.delete(property);
    }
    @Override
    public Property read(Integer name) {
        return propertyDao.read(name);
    }
    @Override
    public Property read(String name) {
        return propertyDao.read(name);
    }
}
