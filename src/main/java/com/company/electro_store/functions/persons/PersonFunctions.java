package com.company.electro_store.functions.persons;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.persons.PersonDto;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.functions.Functions;
import com.company.electro_store.util.managers.HashPassword;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonFunctions extends Functions {
    static Scanner in = new Scanner(System.in);
    public static String addPerson(String name, String surname,String phone, String login, String password) throws IOException, ClassNotFoundException {
        UserDto userDto=getPersonInfo(name, phone, surname, login, password);
        connection.makeRequest(new Request("ADDPERSON",
                userDto));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static List<UserDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWPERSON", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<UserDto>>(){}.getType();
            return (List<UserDto>) response.getResponseObject(type);
        }
        return null;
    }

    public static PersonDto readById(Integer name)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("PERSONBYNAME", name));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (PersonDto) response.getResponseObject(PersonDto.class);
        }
        return null;
    }
    public static UserDto readByPerson(PersonDto person)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("USERBYPERSON", person));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (UserDto) response.getResponseObject(UserDto.class);
        }
        return null;
    }
    public static UserDto readUserById(Integer id) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("USERBYNAME", id));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (UserDto) response.getResponseObject(UserDto.class);
        }
        return null;
    }
    public static String updatePerson(UserDto userDto)throws IOException, ClassNotFoundException{
        //changeDataFromPerson(userDto);
        //changeDataFromUser(userDto);
        connection.makeRequest(new Request("EDITPERSON", userDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static String deletePerson(PersonDto PersonDto) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("DELETEPERSON", PersonDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    private static UserDto getPersonInfo(String name, String phone, String surname, String login, String password) throws IOException, ClassNotFoundException {
        PersonDto person = null;
        UserDto user = getUserInfo(login,password);
        if (user != null) {
            person = new PersonDto(name, surname, phone);
            user.setPerson(person);
            //person.setUser(user);
            //????????????????
            //user.setPerson(person);
        }
        else {
            System.out.println("Неверный ввод!");
        }
        return user;
    }
    private static UserDto getUserInfo(String login, String password) throws IOException, ClassNotFoundException {
        UserDto user = null;
        if(checkUniqueLogin(login)) {
            user = new UserDto(login, HashPassword.getHash(password));
        }
        else {
            System.out.println("Неверный ввод!");
        }
        return user;
    }
    private static boolean checkUniqueLogin(String login) throws IOException, ClassNotFoundException {
        boolean isUnique = true;
        for (UserDto p : readAllUsers()) {
            if (p.getLogin().equals(login)) {
                isUnique = false;
            }
        }
        return isUnique;
    }
    private static UserDto changeDataFromPerson(UserDto user) {
        String name,surname,phone,mail;
        System.out.print("Введите имя : ");
        name = in.nextLine();
        System.out.print("Введите фамилию: ");
        surname = in.nextLine();
        System.out.print("Номер телефона 375(xx)xxx-xx-xx: ");//"375(29)343-48-02"
        phone = in.nextLine();
        user.getPerson().setName(name);
        user.getPerson().setSurname(surname);
        user.getPerson().setPhone(phone);
        return user;
    }

    private static UserDto changeDataFromUser(UserDto user) {

        String login,password;
        System.out.print("Введите логин: ");
        login= in.nextLine();
        System.out.print("Введите пароль: ");
        password= in.nextLine();
        //   if(Validator.correctUser(login, password)) {
        user.setLogin(login);
        user.setPassword(HashPassword.getHash(password));
        // }
        return user;
    }
    public String updateLoginAndPassword(int id) throws IOException, ClassNotFoundException {
        PersonDto person = PersonFunctions.readById(id);
        UserDto user=PersonFunctions.readByPerson(person);
        System.out.println("---изменение логина и пароля---");
        changeDataFromUser(user);
        connection.makeRequest(new Request("EDITPERSON", person));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static void showPersons() throws IOException, ClassNotFoundException {
        List<UserDto> people = readAllUsers();
        if (people.size() != 0) {
            System.out.format("%10s%20s%20s%20s%20s", "ID |", "Имя |", "Фамилия |", "Телефон |", "Логин |");
            for (UserDto p: people) {
                System.out.println(" ");
                System.out.format("%10s%20s%20s%20s%20s", p.getPerson().getPersonId() + " |", p.getPerson().getName() + " |",
                        p.getPerson().getSurname() + " |", p.getPerson().getPhone() + " |", p.getLogin() + " |");
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет пользователей!");
        }
    }
}
