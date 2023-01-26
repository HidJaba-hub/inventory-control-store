package com.company.electro_store.server.functions.storage;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.entity.store.Property;
import com.company.electro_store.server.functions.Function;

import java.io.IOException;
import java.util.List;

public class PropertyFunctions extends Function {
    public static boolean saveOrUpdateProperty(Property property) {
        return propertyService.saveOrUpdate(property);
    }
    public static boolean deleteProperty(Property property){
        return propertyService.delete(property);
    }
    public static List<Property> getProperties(){
        return propertyService.readAll();
    }
    public static Property getPropertyById(Integer name){
        return propertyService.read(name);
    }
    public static Property getPropertyByName(String name){
        return propertyService.read(name);
    }
}
