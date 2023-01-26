package com.company.electro_store.service;

import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;

import java.util.List;

public interface Service<T> {
    boolean saveOrUpdate(T entity);

    List<T> readAll();

    boolean delete(T entity);

    default T read(Integer id){return  null;}
    default T read(String name){return  null;}
    default T read( String obj, Rack.Place place) {return null;}
}
