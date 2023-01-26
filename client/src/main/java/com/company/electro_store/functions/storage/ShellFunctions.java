package com.company.electro_store.functions.storage;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.dto.storage.ShellDto;
import com.company.electro_store.functions.Functions;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.company.electro_store.functions.Functions.connection;

public class ShellFunctions extends Functions {
    public static String deleteShell(ShellDto rackDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("DELETESHELL", rackDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    //TODO не обновляется первичный ключ
    public static String updateShell(ShellDto shellDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("EDITSHELL", shellDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static String addShell(RackDto rackDto, String capacity) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDSHELL",
                new ShellDto(setShellId(rackDto, rackDto.getRackId().getPlace()),
                        rackDto.getRackId().getPlace(),
                        rackDto,
                        Double.parseDouble(capacity),
                        setCoordinate(rackDto,setShellId(rackDto, rackDto.getRackId().getPlace())))));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static List<ShellDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWSHELL", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<ShellDto>>(){}.getType();
            return (List<ShellDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static ShellDto readById(String name)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHELLBYNAME", name));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (ShellDto) response.getResponseObject(ShellDto.class);
        }
        return null;
    }
    public static void addProductToShell(ProductDto product, String name) throws IOException, ClassNotFoundException {
        ShellDto shell=readById(name);
        //shell.addProduct(product);
        product.setShell(shell);
        ProductFunction.updateProduct(product);
    }
    public static int setShellId(RackDto rack, RackDto.Place place){
        List<ShellDto> shells=rack.getShells();
        List<ShellDto> shells_return=new ArrayList<>();
        int i=1;
        for(ShellDto shell: shells){
            if(shell.getIndex()!=i){return i;}
            i++;
        }
        return i;
    }
    public static String setCoordinate(RackDto rack, Integer index){
        String result="";
        result+=rack.getRackId().getName();
        switch (rack.getRackId().getPlace()){
            case HALL -> result+="H";
            case STORAGE -> result+="S";
        }
        result+=index.toString();
        return result;
    }
    public static void showShells() throws IOException, ClassNotFoundException {
        List<ShellDto> shells = readAllUsers();
        if (shells.size() != 0) {
            theHeaderForShell();
            for (ShellDto c: shells) {
                theTableForShell(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет стеллажей!");
        }
    }
    public static void theTableForShell(ShellDto c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s%30s%30s\n", c.getShell_id()+ " |",c.getRack().getRackId().getName()+ " |", c.getPlace()+" |", c.getCapacity()+ " |");
        for(ProductDto productDto: c.getProducts()){
            System.out.println(productDto.getName());
        }
    }
    public static void theHeaderForShell() {
        System.out.format("%5s%30s%30s%30s"," shell id |"," rack |", "place |", "capacity |");
    }
}
