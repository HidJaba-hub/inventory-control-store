package com.company.electro_store.temp.draganddrop;

import com.company.electro_store.temp.product.ColorPicker;
import com.company.electro_store.temp.storage.Rack;
import com.company.electro_store.temp.storage.Shell;
import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShellEditController {
    @FXML
    private TextField capacityField;
    private Stage dialogStage;
    private Shell shell;
    private Rack rack;
    private boolean okClicked = false;
    @FXML
    private void initialize() {
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setShell(Shell shell) {
        this.shell = shell;
        capacityField.setText(Double.toString(shell.getCapacity().get()));
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            shell.setCapacity(Double.parseDouble(capacityField.getText()));
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
        if (capacityField.getText() == null ||
                capacityField.getText().length() == 0 || !Validator.correctNumber(capacityField.getText())||
                Double.parseDouble(capacityField.getText())<0) {
            errorMessage += "Некорректно введена вместимость!\n";
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
