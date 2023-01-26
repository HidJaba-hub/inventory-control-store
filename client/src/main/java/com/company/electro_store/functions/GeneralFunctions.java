package com.company.electro_store.functions;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.util.managers.HashPassword;

import java.io.IOException;
import java.util.Scanner;

public class GeneralFunctions extends Functions {
    static Scanner in = new Scanner(System.in);
    public static UserDto login(String login, String password) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("LOGIN",
            new UserDto(login, HashPassword.getHash(password)))
        );
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (UserDto) response.getResponseObject(UserDto.class);
        }
        return null;
    }
    
    public static String logout() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("LOGOUT", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    
    public static void quit() throws IOException {
        if(connection==null)System.exit(0);
        connection.makeRequest(new Request("QUIT", null));
    }
}
