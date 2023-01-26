package com.company.electro_store.server.initialization;

import com.company.electro_store.dto.persons.PersonDto;
import com.company.electro_store.dto.persons.PostDto;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.dto.persons.WorkDto;
import com.company.electro_store.entity.persons.Person;
import com.company.electro_store.entity.persons.Post;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.persons.Work;
import com.company.electro_store.server.functions.persons.PersonFunctions;
import com.company.electro_store.server.functions.persons.PostFunctions;
import com.company.electro_store.util.managers.CommandStorage;
import com.company.electro_store.util.managers.GsonBuilder;
import com.google.gson.Gson;

public class InitPersons {
    private static final Gson gson = GsonBuilder.getGson();
    public static void create() {
        // General
        CommandStorage.addCommandToStorage("ADDPOST", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Post post = gson.fromJson(clientRequestString.requestString, Post.class);
                    if (PostFunctions.saveOrUpdatePost(post)) {
                        return "Person created";
                    }
                    return "Person not created";
                }
        ));
        CommandStorage.addCommandToStorage("DELETEPOST", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Post post = gson.fromJson(clientRequestString.requestString, Post.class);
                    if (PostFunctions.deletePost(post)) {
                        return "Person deleted";
                    }
                    return "Person not deleted";
                }
        ));
        CommandStorage.addCommandToStorage("EDITPOST", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Post post = gson.fromJson(clientRequestString.requestString, Post.class);
                    if (PostFunctions.saveOrUpdatePost(post)) {
                        return "Person updated";
                    }
                    return "Person not updated";
                }
        ));
        CommandStorage.addCommandToStorage("POSTBYNAME", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Integer name = gson.fromJson(clientRequestString.requestString, Integer.class);
                    Post post= PostFunctions.getPostByName(name);
                    if (post!=null) {
                        return new PostDto(post);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("SHOWPOST", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> PostFunctions.getPosts().stream()
                        .map(PostDto::new).toList()
        ));
        CommandStorage.addCommandToStorage("SHOWWORK", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> PostFunctions.getWorks().stream()
                        .map(WorkDto::new).toList()
        ));
        CommandStorage.addCommandToStorage("ADDWORK", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Work work = gson.fromJson(clientRequestString.requestString, Work.class);
                    if (PostFunctions.addAction(work)) {
                        return "Work created";
                    }
                    return "Work not created";
                }
        ));
        CommandStorage.addCommandToStorage("DELETEWORK", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Work work = gson.fromJson(clientRequestString.requestString, Work.class);
                    if (PostFunctions.deleteWork(work)) {
                        return "Work created";
                    }
                    return "Work not created";
                }
        ));
        CommandStorage.addCommandToStorage("ADDPERSON", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    User user = gson.fromJson(clientRequestString.requestString, User.class);
                    if (PersonFunctions.saveOrUpdateUser(user)) {
                        return "Person created";
                    }
                    return "Person not created";
                }
        ));
        CommandStorage.addCommandToStorage("DELETEPERSON", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Person post = gson.fromJson(clientRequestString.requestString, Person.class);
                    if (PersonFunctions.deletePerson(post)) {
                        return "Person deleted";
                    }
                    return "Person not deleted";
                }
        ));
        CommandStorage.addCommandToStorage("EDITPERSON", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    User user= gson.fromJson(clientRequestString.requestString, User.class);
                    if (PersonFunctions.saveOrUpdateUser(user)) {
                        return "Person updated";
                    }
                    return "Person not updated";
                }
        ));
        CommandStorage.addCommandToStorage("PERSONBYNAME", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Integer name = gson.fromJson(clientRequestString.requestString, Integer.class);
                    Person post= PersonFunctions.getPersonByName(name);
                    if (post!=null) {
                        return new PersonDto(post);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("USERBYNAME", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Integer name = gson.fromJson(clientRequestString.requestString, Integer.class);
                    User post= PersonFunctions.getUserByName(name);
                    if (post!=null) {
                        return new UserDto(post);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("USERBYPERSON", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> {
                    Person person = gson.fromJson(clientRequestString.requestString, Person.class);
                    User user= PersonFunctions.getUserByPerson(person);
                    if (user!=null) {
                        return new UserDto(user);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("SHOWPERSON", new CommandStorage.ServerCommand(
                (CommandStorage.ClientRequestString clientRequestString) -> PersonFunctions.getUsers().stream()
                        .map(UserDto::new).toList()
        ));
    }
}
