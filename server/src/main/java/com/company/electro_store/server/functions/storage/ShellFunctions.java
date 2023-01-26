package com.company.electro_store.server.functions.storage;

import com.company.electro_store.entity.store.Property;
import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import com.company.electro_store.server.functions.Function;

import java.util.ArrayList;
import java.util.List;

public class ShellFunctions extends Function {
    public static boolean addShell(Shell shell) {
        return shellService.saveOrUpdate(shell);
    }
    public static boolean deleteShell(Shell shell){
        return shellService.delete(shell);
    }
    public static List<Shell> getShells(){
        return shellService.readAll();
    }
    public static Shell getShellByName(String name){
        return shellService.read(name);
    }
    public static String setCoordinate(Rack rack, Integer index){
        String result="";
        result+=rack.getRackId().getName();
        switch (rack.getRackId().getPlace()){
            case HALL -> result+="H";
            case STORAGE -> result+="S";
        }
        result+=index.toString();
        return result;
    }
    public static List<Shell> getShellbyRack(Rack rack){
        return rack.getShells();
    }

    public void addShell(Shell shell, Rack rack){
        rack.addShell(shell);
        rackService.saveOrUpdate(rack);
    }
}
