package com.company.electro_store.server.initialization;

import com.company.electro_store.dto.accountant.SalariesDto;
import com.company.electro_store.dto.accountant.SalesDto;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.dto.storage.ShellDto;
import com.company.electro_store.entity.accountant.Salaries;
import com.company.electro_store.entity.accountant.Sales;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.entity.store.*;
import com.company.electro_store.server.functions.accountant.SalariesFunctions;
import com.company.electro_store.server.functions.accountant.SalesFunctions;
import com.company.electro_store.server.functions.storage.ProductFunction;
import com.company.electro_store.server.functions.GeneralFunctions;
import com.company.electro_store.server.functions.storage.PropertyFunctions;
import com.company.electro_store.server.functions.storage.RackFunctions;
import com.company.electro_store.server.functions.storage.ShellFunctions;
import com.company.electro_store.util.managers.CommandStorage;
import com.company.electro_store.util.managers.CommandStorage.ClientRequestString;
import com.company.electro_store.util.managers.GsonBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Date;


public class InitCommands {
    private static final Gson gson = GsonBuilder.getGson();
    
    // todo доделать обработчик роли
    public static void create() {
        // General
        CommandStorage.addCommandToStorage("LOGIN", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    User user = gson.fromJson(clientRequestString.requestString, User.class);
                    user = GeneralFunctions.login(user, clientRequestString.client);
                    /*if(user.getLogin().equals("notFound")){
                        throw new IllegalArgumentException("Неверный логин проверьте пароль");
                    }*/
                    if (user != null) {
                        return new UserDto(user);
                    }
                    int c=1+/*500*/+2;
                    throw new IllegalArgumentException("Неверный пароль");
                }
        ));
        CommandStorage.addCommandToStorage("LOGOUT", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    try {
                        return GeneralFunctions.logout(clientRequestString.client);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        ));
        CommandStorage.addCommandToStorage("QUIT", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    GeneralFunctions.quit(clientRequestString.client);
                    return null;
                }
        ));
        CommandStorage.addCommandToStorage("ADDPROPERTY",new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Property property = gson.fromJson(clientRequestString.requestString, Property.class);
                    if (PropertyFunctions.saveOrUpdateProperty(property)) {
                        return "Property created";
                    }// TODO
                    return "Property not created";
                }
        ));
        CommandStorage.addCommandToStorage("EDITPROPERTY",new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Property property = gson.fromJson(clientRequestString.requestString, Property.class);
                    if (PropertyFunctions.saveOrUpdateProperty(property)) {
                        return "Property edited";
                    }// TODO
                    return "Property not edited";
                }
        ));
        CommandStorage.addCommandToStorage("DELETEPROPERTY",new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Property property = gson.fromJson(clientRequestString.requestString, Property.class);
                    if (PropertyFunctions.deleteProperty(property)) {
                        return "Property edited";
                    }// TODO
                    return "Property not edited";
                }
        ));
        CommandStorage.addCommandToStorage("SHOWPROPERTY", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> PropertyFunctions.getProperties().stream()
                        .map(PropertyDto::new).toList()
        ));
        CommandStorage.addCommandToStorage("PROPERTYBYID", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    Integer name = gson.fromJson(clientRequestString.requestString, Integer.class);
                    Property property=PropertyFunctions.getPropertyById(name);
                    if (property!=null) {
                        return new PropertyDto(property);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("PROPERTYBYNAME", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    String name = gson.fromJson(clientRequestString.requestString, String.class);
                    Property property=PropertyFunctions.getPropertyByName(name);
                    if (property!=null) {
                        return new PropertyDto(property);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("ADDRACK", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Rack rack = gson.fromJson(clientRequestString.requestString, Rack.class);
                    if (RackFunctions.addRack(rack)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("EDITRACK", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Rack rack = gson.fromJson(clientRequestString.requestString, Rack.class);
                    if (RackFunctions.updateRack(rack)) {
                        return "Rack updated";
                    }// TODO
                    return "Rack isnt updated";
                }
        ));
        CommandStorage.addCommandToStorage("DELETERACK", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    Rack rack = gson.fromJson(clientRequestString.requestString, Rack.class);
                    if (RackFunctions.deleteRack(rack)) {
                        return "Rack deleted";
                    }// TODO
                    return "Rack isnt deleted";
                }
        ));
        CommandStorage.addCommandToStorage("SHOWRACK", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> RackFunctions.getRacks().stream()
                        .map(RackDto::new).toList()
        ));
        CommandStorage.addCommandToStorage("RACKBYNAME", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    String name = gson.fromJson(clientRequestString.requestString, String.class);
                    Rack rack= RackFunctions.getRackByName(name);
                    if (rack!=null) {
                        return new RackDto(rack);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("RACKBYNAME_ANDPLACE", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    RackId rackId=gson.fromJson(clientRequestString.requestString,RackId.class);
                    Rack rack= RackFunctions.getRackByNameAndPlace(rackId.getName(), rackId.getPlace());
                    if (rack!=null) {
                        return new RackDto(rack);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("ADDSHELL", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Shell shell= gson.fromJson(clientRequestString.requestString, Shell.class);
                    if (ShellFunctions.addShell(shell)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("EDITSHELL", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Shell shell= gson.fromJson(clientRequestString.requestString, Shell.class);
                    if (ShellFunctions.addShell(shell)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("DELETESHELL", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Shell shell= gson.fromJson(clientRequestString.requestString, Shell.class);
                    if (ShellFunctions.deleteShell(shell)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("SHOWSHELL", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> ShellFunctions.getShells().stream()
                        .map(ShellDto::new).toList()
        ));
        CommandStorage.addCommandToStorage("SHELLBYNAME", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    String name = gson.fromJson(clientRequestString.requestString, String.class);
                    Shell shell=ShellFunctions.getShellByName(name);
                    if (shell!=null) {
                        return new ShellDto(shell,false);
                    }// TODO
                    return "Property not found";
                }
        ));
        CommandStorage.addCommandToStorage("ADDPRODUCT", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Product product = gson.fromJson(clientRequestString.requestString, Product.class);
                    if (ProductFunction.saveOrUpdateProduct(product)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("EDITPRODUCT", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Product product = gson.fromJson(clientRequestString.requestString, Product.class);
                    if (ProductFunction.saveOrUpdateProduct(product)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("DELETEPRODUCT", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Product product = gson.fromJson(clientRequestString.requestString, Product.class);
                    if (ProductFunction.deleteProduct(product)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("SHOWPRODUCT", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> ProductFunction.getProducts().stream()
                        .map(ProductDto::new).toList()
        ));
        CommandStorage.addCommandToStorage("PRODUCTBYNAME", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> {
                    Integer name = gson.fromJson(clientRequestString.requestString, Integer.class);
                    Product product=ProductFunction.getProductByName(name);
                    if (product!=null) {
                        return new ProductDto(product);
                    }// TODO
                    throw new IllegalArgumentException("нет такого продукта");
                }
        ));
        CommandStorage.addCommandToStorage("ADDSALARY", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Salaries salaries = gson.fromJson(clientRequestString.requestString, Salaries.class);
                    if (SalariesFunctions.saveOrUpdateSalaries(salaries)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("ADDSALE", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Sales sales = gson.fromJson(clientRequestString.requestString, Sales.class);
                    if (SalesFunctions.saveOrUpdateSales(sales)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("FINDSALARYBYDATE", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Date date = gson.fromJson(clientRequestString.requestString, Date.class);
                    Salaries salaries=SalariesFunctions.getSalaryByDate(date);
                    if (salaries!=null) {
                        return new SalariesDto(salaries);
                    }// TODO
                    throw new IllegalArgumentException("нет такого продукта");
                }
        ));
        CommandStorage.addCommandToStorage("FINDSALARYBYID", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Integer id = gson.fromJson(clientRequestString.requestString, Integer.class);
                    Salaries salaries=SalariesFunctions.getSalaryById(id);
                    if (salaries!=null) {
                        return new SalariesDto(salaries);
                    }// TODO
                    throw new IllegalArgumentException("нет такого продукта");
                }
        ));
        CommandStorage.addCommandToStorage("FINDSALEBYDATE", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Date date = gson.fromJson(clientRequestString.requestString, Date.class);
                    Sales sales=SalesFunctions.getSalaryByDate(date);
                    if (sales!=null) {
                        return new SalesDto(sales);
                    }// TODO
                    throw new IllegalArgumentException("нет такого продукта");
                }
        ));
        CommandStorage.addCommandToStorage("FINDSALEBYID", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Integer id = gson.fromJson(clientRequestString.requestString, Integer.class);
                    Sales sales=SalesFunctions.getSalaryById(id);
                    if (sales!=null) {
                        return new SalesDto(sales);
                    }// TODO
                    throw new IllegalArgumentException("нет такого продукта");
                }
        ));
        CommandStorage.addCommandToStorage("EDITSALE", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Sales sales = gson.fromJson(clientRequestString.requestString, Sales.class);
                    if (SalesFunctions.saveOrUpdateSales(sales)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("EDITSALARY", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString)->{
                    Salaries salaries = gson.fromJson(clientRequestString.requestString, Salaries.class);
                    if (SalariesFunctions.saveOrUpdateSalaries(salaries)) {
                        return "Rack created";
                    }// TODO
                    return "Rack not created";
                }
        ));
        CommandStorage.addCommandToStorage("SHOWSALARY", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> SalariesFunctions.getSalaries().stream()
                        .map(SalariesDto::new).toList()
        ));
        CommandStorage.addCommandToStorage("SHOWSALE", new CommandStorage.ServerCommand(
                (ClientRequestString clientRequestString) -> SalesFunctions.getSales().stream()
                        .map(SalesDto::new).toList()
        ));
    }
}
