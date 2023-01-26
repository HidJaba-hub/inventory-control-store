package com.company.electro_store.functions.accountant;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.accountant.SalariesDto;
import com.company.electro_store.functions.Functions;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class SalariesFunctions extends Functions {
    public static String addSalaries(Double salary, Date date) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDSALARY",
                new SalariesDto(salary,date)));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }

    public static List<SalariesDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWSALARY", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<SalariesDto>>(){}.getType();
            return (List<SalariesDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static SalariesDto readById(Integer id)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("FINDSALARYID", id));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (SalariesDto) response.getResponseObject(SalariesDto.class);
        }
        return null;
    }
    public static SalariesDto readByDate(Date date)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("FINDSALARYBYDATE", date));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (SalariesDto) response.getResponseObject(SalariesDto.class);
        }
        return null;
    }
    public static String updateSalaries(SalariesDto SalariesDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("EDITSALARY", SalariesDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
}
