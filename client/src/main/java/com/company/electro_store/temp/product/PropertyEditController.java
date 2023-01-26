package com.company.electro_store.temp.product;

import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PropertyEditController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField valueField;

    private Stage dialogStage;
    private Property property;
    private boolean okClicked = false;
    @FXML
    private void initialize() {
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setProperty(Property property) {
        this.property=property;
        nameField.setText(property.getPropertyName());
        valueField.setText(property.getPropertyValue());
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            property.setPropertyName(nameField.getText());
            property.setPropertyValue(valueField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (nameField.getText() == null ||
                nameField.getText().length() == 0 || !Validator.correctBigLetter(nameField.getText())) {
            errorMessage += "Некорректно введено название!\n";
        }
        if (valueField.getText() == null ||
                valueField.getText().length() == 0 || !Validator.correctBigLetter(valueField.getText())) {
            errorMessage += "Некорректно введена величина измерения!\n";
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
    }
}
