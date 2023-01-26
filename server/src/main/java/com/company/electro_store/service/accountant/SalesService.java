package com.company.electro_store.service.accountant;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.accountant.SalesDao;
import com.company.electro_store.entity.accountant.Sales;
import com.company.electro_store.service.Service;

import java.sql.Date;
import java.util.List;

public class SalesService implements Service<Sales> {
    Dao<Sales> salesDao = new SalesDao();

    @Override
    public boolean saveOrUpdate(Sales sales) {
        return salesDao.saveOrUpdate(sales);
    }

    @Override
    public List<Sales> readAll() {
        return salesDao.readAll();
    }
    @Override
    public boolean delete(Sales sales) {
        return salesDao.delete(sales);
    }
    @Override
    public Sales read(Integer id) {
        return salesDao.read(id);
    }
    public Sales read(Date date){
        SalesDao s=new SalesDao();
        return s.read(date);}
}
