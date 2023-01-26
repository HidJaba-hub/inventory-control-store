package com.company.electro_store.temp.draganddrop;

import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.product.ColorPicker;
import com.company.electro_store.temp.storage.Rack;
import com.company.electro_store.temp.storage.ShellController;
import com.company.electro_store.temp.worker.person.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RackEditController {
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<HBox> comboBox;
    private Stage dialogStage;
    private Rack rack;
    private boolean okClicked = false;
    private MenuController menu;
    private RackDto.Place place;
    @FXML
    private void initialize() {
    }
    public void setDialogStage(Stage dialogStage, RackDto.Place place, MenuController menu) {
        this.menu=menu;
        this.dialogStage = dialogStage;
        this.place=place;
    }
    public void setRack(Rack rack) {
        this.rack = rack;
        nameField.setText(rack.getRackName().get());
    }
    public boolean isOkClicked() {
        return okClicked;
    }
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            VBox box=(VBox) comboBox.getSelectionModel().getSelectedItem().getChildren().get(1);
            Label label=(Label) box.getChildren().get(0);
            String color=label.getText();
            rack.setName(nameField.getText());
            rack.setColor(ColorPicker.getStringColor(color));
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
                nameField.getText().length() == 0) {
            errorMessage += "Пустое поля для название!\n";
        }
        if(place.equals(RackDto.Place.HALL)) {
            for (Rack r : menu.getHallRackData()) {
                if (r.getRackName().equals(nameField.getText())) {
                    errorMessage += "Такое имя существует!\n";
                }
            }
        }
        else{
            for (Rack r : menu.getStorageRackData()) {
                if (r.getRackName().equals(nameField.getText())) {
                    errorMessage += "Такое имя существует!\n";
                }
            }
        }
        if(comboBox.getSelectionModel().getSelectedItem()==null){
            errorMessage += "Не выбран цвет!\n";
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
    public void setComboBox() throws IOException {
        DragIconType[] colors=DragIconType.values();
        for(DragIconType color:colors){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MenuController.class.getResource("/views/storage/colorBox.fxml"));

            HBox colorItem = loader.load();
            ColorPicker colorPicker = loader.getController();
            colorPicker.setColorPicker(color);
            comboBox.getItems().add(colorItem);
        }
    }
}
