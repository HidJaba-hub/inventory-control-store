package com.company.electro_store.service.store;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.store.ProductDao;
import com.company.electro_store.entity.store.Product;
import com.company.electro_store.service.Service;

import java.util.List;

public class ProductService implements Service<Product> {
    Dao<Product> productDao = new ProductDao();

    @Override
    public boolean saveOrUpdate(Product product) {
        return productDao.saveOrUpdate(product);
    }

    @Override
    public List<Product> readAll() {
        return productDao.readAll();
    }
    @Override
    public boolean delete(Product product) {
        return productDao.delete(product);
    }
    @Override
    public Product read(Integer id) {
        return productDao.read(id);
    }
}
