package com.company.electro_store.service.store;

import com.company.electro_store.dao.Dao;
import com.company.electro_store.dao.store.RackDao;
import com.company.electro_store.dao.store.ShellDao;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import com.company.electro_store.service.Service;

import java.util.List;

public class ShellService implements Service<Shell> {
    Dao<Shell> shellDao = new ShellDao();

    @Override
    public boolean saveOrUpdate(Shell shell) {
        return shellDao.saveOrUpdate(shell);
    }

    @Override
    public List<Shell> readAll() {
        return shellDao.readAll();
    }
    @Override
    public boolean delete(Shell shell) {
        return shellDao.delete(shell);
    }

    public Shell read(String id) {
        return shellDao.read(id);
    }
}
