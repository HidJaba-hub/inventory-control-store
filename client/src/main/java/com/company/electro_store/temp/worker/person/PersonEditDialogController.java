package com.company.electro_store.temp.worker.person;

import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.worker.person.Person;
import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/*Окно для изменения информации об адресате*/
public class PersonEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;
    private MenuController menu;

    public PersonEditDialogController() throws IOException, ClassNotFoundException {
    }


    public void setDialogStage(Stage dialogStage, MenuController menu) {

        this.dialogStage = dialogStage;
        this.menu=menu;
    }

    public void setPerson(Person person) {
        this.person = person;
        firstNameField.setText(person.getFirstName());
        lastNameField.setText(person.getLastName());
        phoneField.setText(person.getPhone());
        loginField.setText(person.getLogin());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setPhone(phoneField.getText());
            person.setLogin(loginField.getText());
            person.setPassword(passwordField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }
    /**
     * Вызывается, когда пользователь кликнул по кнопке Cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    /**
     * Метод проверяет пользовательский ввод в текстовых полях.
     *
     * @return true, если пользовательский ввод корректен
     */
    private boolean isInputValid() {
        String errorMessage = "";
        if (firstNameField.getText() == null ||
                firstNameField.getText().length() == 0 || !Validator.correctBigLetter(firstNameField.getText())) {
            errorMessage += "Некорректно введено имя!\n";
        }
        if (lastNameField.getText() == null ||
                lastNameField.getText().length() == 0 || !Validator.correctBigLetter(lastNameField.getText())) {
            errorMessage += "Некорректно введена фамилия!\n";
        }
        if (phoneField.getText() == null ||
                phoneField.getText().length() == 0 || !Validator.correctPhone(phoneField.getText())) {
            errorMessage += "Номер телефона должен быть в формате 375(xx)xxx-xx-xx!\n";
        }
        for(Person p:menu.getPersonData()){
            if(p.getPhone().equals(phoneField.getText())){
                errorMessage += "Уже есть такой номер телефона!\n";
            }
        }
        for(Person p:menu.getPersonData()){
            if(p.getLogin().equals(loginField.getText())){
                errorMessage += "Уже есть такой логин!\n";
            }
        }
        if (loginField.getText() == null ||
                loginField.getText().length() == 0) {
            errorMessage += "Поле для логина пустое\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверно заполнены поля");
            alert.setHeaderText("Введите корректные значения полей");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }}
