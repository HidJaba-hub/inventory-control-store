package com.company.electro_store.functions.storage;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.functions.Functions;
import com.company.electro_store.util.managers.connections.TCPConnection;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.company.electro_store.functions.Functions.connection;

public class PropertyFunctions extends Functions {
    public static String addProperty(String name, String value) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDPROPERTY",
                new PropertyDto(name,value)));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }

    public static List<PropertyDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWPROPERTY", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<PropertyDto>>(){}.getType();
            return (List<PropertyDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static PropertyDto readById(Integer id)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("PROPERTYBYID", id));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (PropertyDto) response.getResponseObject(PropertyDto.class);
        }
        return null;
    }
    public static PropertyDto readByName(String name)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("PROPERTYBYNAME", name));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (PropertyDto) response.getResponseObject(PropertyDto.class);
        }
        return null;
    }
    public static String updateProperty(PropertyDto propertyDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("EDITPROPERTY", propertyDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static String deleteProperty(PropertyDto propertyDto) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("DELETEPROPERTY", propertyDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static void theTableForProperty(PropertyDto c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s\n", c.getName() + " |", c.getValue()+" |");
    }
    public static void theHeaderForProperty() {
        System.out.format("%5s%30s"," name |", "value |");
    }

    public static void showProperties() throws IOException, ClassNotFoundException {
        List<PropertyDto> properties = readAllUsers();
        if (properties.size() != 0) {
            for (PropertyDto c: properties) {
                theHeaderForProperty();
                theTableForProperty(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет фильмов!");
        }
    }
}
