package com.company.electro_store.temp.worker.person;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class Person {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty phone;
    private final StringProperty post;
    private final StringProperty password;
    private final StringProperty login;
    private Integer UserId;

    private byte[] pass;

    public Person() {
        this(null,null,null,null,null,null,null);
    }
    public Person(String firstName, String lastName, String phone, String post,Integer userId, String log, byte[] pas) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone=new SimpleStringProperty(phone);
        if(post!=null)this.post=new SimpleStringProperty(post);
        else this.post=new SimpleStringProperty("Нет должности");
        this.UserId=userId;
        this.pass=pas;
        this.password=new SimpleStringProperty();
        this.login=new SimpleStringProperty(log);
    }
    public StringProperty lastNameProperty() {

        return lastName;
    }
    public String getFirstName() {
        return firstName.get();
    }
    public String getLastName() {
        return lastName.get();
    }
    public String getPhone() {
        return phone.get();
    }
    public String getLogin() {
        return login.get();
    }
    public String getPassword(){
        return password.get();
    }
    public String getPost() {
        return post.get();
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public void setPassword(String password){
        this.password.set(password);
    }
    public void setLogin(String login){
        this.login.set(login);
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
}
