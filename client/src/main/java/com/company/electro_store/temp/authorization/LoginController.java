package com.company.electro_store.temp.authorization;

import com.company.electro_store.HelloApplication;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.functions.GeneralFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.user.UserController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button register;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @Getter
    private static UserDto userDto;
    @FXML
    public void initialize(){

    }
    @FXML
    private void onRegistrationButtonClick(){
        HelloApplication.getScreenController().activate("registration");
    }
    @FXML
    protected void onLoginButtonClick() throws IOException, ClassNotFoundException {
        GeneralFunctions generalFunctions=new GeneralFunctions();
        userDto=generalFunctions.login(loginField.getText(),passwordField.getText());
        if(userDto!=null){
            CheckButton.setUser_actions(userDto.getPost());
            UserController.setUser(userDto);
            MenuController menuController=new MenuController();
            menuController.initRootLayout();
            menuController.userButtonActive();
        }
    }
    @FXML
    public void closeHandler() throws IOException {
        GeneralFunctions.quit();
        HelloApplication.getStage_scene().close();
        System.exit(0);
    }
}
