package com.company.electro_store.temp.user;

import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.functions.persons.PersonFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.worker.person.Person;
import com.company.electro_store.util.managers.HashPassword;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
public class UserController {
    @FXML
    private Label nameField;
    @FXML
    private Label surnameField;
    @FXML
    private Label phoneField;
    @FXML
    private Label postField;
    @FXML
    private Label hoursField;
    @Setter
    @Getter
    private static UserDto user;
    @FXML
    protected void initialize() throws IOException, ClassNotFoundException {
        if (user != null) {
            nameField.setText(user.getPerson().getName());
            if(user.getMinutes()!=null)hoursField.setText(Double.toString(user.getMinutes()));
            if(user.getPost()!=null)postField.setText(user.getPost().getName());
            surnameField.setText(user.getPerson().getSurname());
            phoneField.setText(user.getPerson().getPhone());
        }
    }
    @FXML
    private void editUser() throws IOException, ClassNotFoundException {
        MenuController menu=new MenuController();
        Person selectedPerson=new Person(user.getPerson().getName(),user.getPerson().getSurname(),user.getPerson().getPhone(),user.getPost().getName(),user.getUserId(),user.getLogin(), user.getPassword());
            boolean okClicked = menu.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                user.getPerson().setName(selectedPerson.getFirstName());
                user.getPerson().setSurname(selectedPerson.getLastName());
                user.getPerson().setPhone(selectedPerson.getPhone());
                user.setPassword(HashPassword.getHash(selectedPerson.getPassword()));
                user.setLogin(selectedPerson.getLogin());
                PersonFunctions.updatePerson(user);
            }
    }
}
