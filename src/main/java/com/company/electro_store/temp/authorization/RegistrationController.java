package com.company.electro_store.temp.authorization;

import com.company.electro_store.HelloApplication;
import com.company.electro_store.functions.GeneralFunctions;
import com.company.electro_store.functions.persons.PersonFunctions;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegistrationController {
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField phoneField;
    @FXML
    private void onRegistrationButtonClick() throws IOException, ClassNotFoundException {
        GeneralFunctions generalFunctions=new GeneralFunctions();
        PersonFunctions.addPerson(nameField.getText(),surnameField.getText(),phoneField.getText(),loginField.getText(),passwordField.getText());
        HelloApplication.getScreenController().activate("login");
    }
    @FXML
    protected void closeHandler() throws IOException {
        GeneralFunctions.quit();
        HelloApplication.getStage_scene().close();
        System.exit(0);
    }
}
