package com.company.electro_store.functions.persons;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.persons.PostDto;
import com.company.electro_store.dto.persons.WorkDto;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.functions.Action;
import com.company.electro_store.functions.Functions;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.company.electro_store.functions.Functions.connection;

public class PostFunctions extends Functions {
    public static String addPost(String name, String cost) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDPOST",
                new PostDto(name,Double.parseDouble(cost))));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static void addAdmin() throws IOException, ClassNotFoundException {
        PostDto post=new PostDto("admin",1.0);
        updatePost(post);
        Action[] str=Action.values();
        int i=0;
        for(Action action: str){
            WorkDto work=new WorkDto(action, post);
            post.addAction(work);
            // propertyService.saveOrUpdate(property);
            addWork(work);
        }
    }
    public static String addWork(WorkDto workDto) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDWORK",
                workDto));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static List<PostDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWPOST", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<PostDto>>(){}.getType();
            return (List<PostDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static List<WorkDto> readAllWorks() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWWORK", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<WorkDto>>(){}.getType();
            return (List<WorkDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static PostDto readById(Integer name)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("POSTBYNAME", name));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (PostDto) response.getResponseObject(PostDto.class);
        }
        return null;
    }
    public static String updatePost(PostDto productDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("EDITPOST", productDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static String deletePost(PostDto PostDto) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("DELETEPOST", PostDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static String deleteWork(WorkDto workDto) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("DELETEWORK", workDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static List<WorkDto> showFunctions(PostDto post){
        List<WorkDto> works=post.getActions();
        int i=1;
        for(WorkDto w: works){
            System.out.println(i+" "+w.getAction().getStr());
            i++;
        }
        return works;
    }
    public static void theTableForPost(PostDto c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s%30s\n", c.getPost_id() + " |", c.getName() + " |", c.getCost()+" |");
        showFunctions(c);
    }
    public static void theHeaderForPost() {
        System.out.format("%5s%30s%30s"," id |"," name |", " cost |");
    }

    public static void showPosts() throws IOException, ClassNotFoundException {
        List<PostDto> Posts = readAllUsers();
        if (Posts.size() != 0) {
            theHeaderForPost();
            for (PostDto c: Posts) {
                theTableForPost(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет должностей!");
        }
    }
}
