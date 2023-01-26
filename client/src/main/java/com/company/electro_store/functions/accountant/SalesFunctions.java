package com.company.electro_store.functions.accountant;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.accountant.SalesDto;
import com.company.electro_store.functions.Functions;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SalesFunctions extends Functions {
    public static String addSales(Double profit,Double expences, Integer amount, Date date) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDSALE",
                new SalesDto(amount,profit,expences,date)));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }

    public static List<SalesDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWSALE", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<SalesDto>>(){}.getType();
            return (List<SalesDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static SalesDto readById(Integer id)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("FINDSALEBYID", id));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (SalesDto) response.getResponseObject(SalesDto.class);
        }
        return null;
    }
    public static SalesDto readByDate(Date date)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("FINDSALEBYDATE", date));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (SalesDto) response.getResponseObject(SalesDto.class);
        }
        return null;
    }
    public static String updateSales(SalesDto SalesDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("EDITSALE", SalesDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
}
