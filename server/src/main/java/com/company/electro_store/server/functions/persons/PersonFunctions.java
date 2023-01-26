package com.company.electro_store.server.functions.persons;


import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.server.functions.Function;
import com.company.electro_store.service.persons.UserService;

import java.util.List;

public class PersonFunctions extends Function {
    public static boolean saveOrUpdateUser(User user){
        return userService.saveOrUpdate(user);
    }
    public static boolean saveOrUpdatePerson(Person person) {
        User user=person.getUser();
        user.setPerson(person);
        return userService.saveOrUpdate(user);
        //return personService.saveOrUpdate(person);
    }
    public static boolean deletePerson(Person person){
        return personService.delete(person);
    }
    public static List<Person> getPersons(){
        return personService.readAll();
    }
    public static User getUserByPerson(Person person){return userService.read(person);}
    public static List<User> getUsers(){return userService.readAll();}
    public static Person getPersonByName(Integer name){
        return personService.read(name);
    }
    public static User getUserByName(Integer name){
        return userService.read(name);
    }
}
