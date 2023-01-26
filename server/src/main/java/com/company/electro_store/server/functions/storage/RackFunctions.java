package com.company.electro_store.server.functions.storage;

import com.company.electro_store.entity.store.Rack;
import com.company.electro_store.entity.store.Shell;
import com.company.electro_store.server.functions.Function;

import java.util.List;

public class RackFunctions extends Function {
    public static boolean addRack(Rack rack) {
        return rackService.saveOrUpdate(rack);
    }
    public static boolean updateRack(Rack rack) {
        for(Shell shell:rack.getShells()){
            shell.setPlace(rack.getRackId().getPlace());
            shell.setRack(rack);
            ShellFunctions.setCoordinate(rack,shell.getIndex());
            shellService.saveOrUpdate(shell);
        }
        return rackService.saveOrUpdate(rack);
    }
    public static boolean deleteRack(Rack rack){
        return rackService.delete(rack);
    }
    public static List<Rack> getRacks(){
        return rackService.readAll();
    }
    public static Rack getRackByName(String name){
        return rackService.read(name);
    }
    public static Rack getRackByNameAndPlace(String name, Rack.Place place){
        return rackService.read(name,place);
    }
}