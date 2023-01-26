package com.company.electro_store.functions.storage;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.dto.storage.RackIdDto;
import com.company.electro_store.dto.storage.ShellDto;
import com.company.electro_store.functions.Functions;
import com.company.electro_store.temp.draganddrop.DragIconType;
import com.company.electro_store.util.managers.connections.TCPConnection;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.company.electro_store.functions.Functions.connection;

public class RackFunctions extends Functions {
    public static String deleteRack(RackDto rackDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("DELETERACK", rackDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
//TODO не обновляется первичный ключ
    public static String updateRack(RackDto rackDto)throws IOException, ClassNotFoundException{
        connection.makeRequest(new Request("EDITRACK", rackDto));
        Response response=connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static String addRack(String name, RackDto.Place place, DragIconType color) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("ADDRACK",
                new RackDto(name,place,color)));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    public static List<RackDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("SHOWRACK", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<RackDto>>(){}.getType();
            return (List<RackDto>) response.getResponseObject(type);
        }
        return null;
    }
    public static RackDto readById(String name)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("RACKBYNAME", name));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (RackDto) response.getResponseObject(RackDto.class);
        }
        return null;
    }
    public static RackDto readByIdandPlace(RackIdDto rackIdDto)throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("RACKBYNAME_ANDPLACE", rackIdDto));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (RackDto) response.getResponseObject(RackDto.class);
        }
        return null;
    }
    public static void showRacks() throws IOException, ClassNotFoundException {
        List<RackDto> racks = readAllUsers();
        if (racks.size() != 0) {
            theHeaderForRack();
            for (RackDto c: racks) {
                theTableForRack(c);
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет стеллажей!");
        }
    }
    public static void showRacks(RackDto.Place place) throws IOException, ClassNotFoundException {
        List<RackDto> racks = readAllUsers();
        if (racks.size() != 0) {
            theHeaderForRack();
            for (RackDto c: racks) {
                if(c.getRackId().getPlace().equals(place)) {
                    theTableForRack(c);
                }
            }
            System.out.println(" ");
        }
        else {
            System.out.println("Нет стеллажей!");
        }
    }
    public static boolean showShells(RackDto rackDto){
        List<ShellDto> shells=rackDto.getShells();
        if(shells.size()==0)return false;
        ShellFunctions.theHeaderForShell();
        for (ShellDto shell : shells) {
            shell.setRack(rackDto);
            ShellFunctions.theTableForShell(shell);
        }
        return true;
    }
    public static void showShells(){
        //shell
    }
    public static void addShell(ShellDto shell, RackDto rack) throws IOException, ClassNotFoundException {
        rack.addShell(shell);
        updateRack(rack);
    }
    public static void theTableForRack(RackDto c) {
        System.out.println(" ");
        String str="";
        int i=0;
        System.out.format("%5s%30s\n", c.getRackId().getName() + " |", c.getRackId().getPlace()+" |");
        //showShells(c);
    }
    public static void theHeaderForRack() {
        System.out.format("%5s%30s"," name |", "place |", "shells |");
    }
}
