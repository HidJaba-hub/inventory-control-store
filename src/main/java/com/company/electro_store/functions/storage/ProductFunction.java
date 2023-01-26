package com.company.electro_store.functions.storage;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.functions.Functions;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductFunction extends Functions {
    public static String addProduct(String code, String nominalPrice, PropertyDto propertyDto, String product_name, Double number, Double weight) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDPRODUCT",
                new ProductDto(Integer.parseInt(code),Double.parseDouble(nominalPrice),propertyDto,product_name,number, weight)));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }

    public static List<ProductDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWPRODUCT", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<ProductDto>>(){}.getType();
            return (List<ProductDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static ProductDto readById(Integer name)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("PRODUCTBYNAME", name));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (ProductDto) response.getResponseObject(ProductDto.class);
        }
        return null;
    }
    public static String updateProduct(ProductDto productDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("EDITPRODUCT", productDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static String deleteProduct(ProductDto productDto) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("DELETEPRODUCT", productDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    ////////////
    public static void theTableForProduct(ProductDto c) {
        System.out.println(" ");
        String str="";
        int i=0;
        if(c.getShell()!=null) System.out.format("%5s%30s%30s%30s%30s\n", c.getCode() + " |", c.getName()+" |", c.getCost()+" |", c.getProperty().getName()+" |", c.getShell().getShell_id()+" |");
        else System.out.format("%5s%30s%30s%30s\n", c.getCode() + " |", c.getName()+" |", c.getCost()+" |", c.getProperty().getName()+" |");
    }
    public static void theHeaderForProduct() {
        System.out.format("%5s%30s%30s%30s%30s"," code |", "name |", "cost |", "property |", " shell_id |");
    }

    public static void showProducts() throws IOException, ClassNotFoundException {
        List<ProductDto> products = readAllUsers();
        if (products.size() != 0) {
            for (ProductDto c: products) {
                theHeaderForProduct();
                theTableForProduct(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет продуктов!");
        }
    }
}
