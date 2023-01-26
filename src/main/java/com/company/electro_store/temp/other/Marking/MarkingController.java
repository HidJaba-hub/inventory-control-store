package com.company.electro_store.temp.other.Marking;

import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.functions.storage.ProductFunction;
import com.company.electro_store.functions.storage.PropertyFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.product.Product;
import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MarkingController {
    @FXML
    private Label nameField;
    @FXML
    private Label priceField;
    @FXML
    private Label nominalPriceField;
    @FXML
    private Label placeField;
    @FXML
    private Label shellField;
    @FXML
    private Label propertyField;
    @FXML
    private Label numberField;
    @FXML
    private Label valueField;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private Button Ok;
    MenuController menu;
    private Stage dialogStage;
    private ProductDto productDto;

    @FXML
    private void handleFind() throws IOException, ClassNotFoundException {
        if (isInputValid()) {
            productDto= ProductFunction.readById(Integer.parseInt(codeTextField.getText()));
            if(productDto!=null){
                nameField.setText(productDto.getName());
                if(priceField.getText()!=null)priceField.setText(productDto.getCost().toString());
                numberField.setText(productDto.getNumber().toString());
                nominalPriceField.setText(productDto.getNominalPrice().toString());
                if(productDto.getShell()!=null) {
                    shellField.setText(productDto.getShell().getShell_id());
                    placeField.setText(productDto.getShell().getPlace().getStr());
                }
                if(productDto.getProperty()!=null) {
                    valueField.setText(productDto.getProperty().getValue());
                    propertyField.setText(productDto.getProperty().getName());
                }
                Ok.setVisible(true);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(dialogStage);
                alert.setTitle("Нет товара");
                alert.setContentText("Такого товара нет");
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void handleOk()throws IOException, ClassNotFoundException{
        if(Validator.correctNumber(priceTextField.getText())&&Double.parseDouble(priceTextField.getText())>0) {
            productDto.setCost(Double.parseDouble(priceTextField.getText()));
            ProductFunction.updateProduct(productDto);
            menu.setProductData();
        }
        nameField.setText("");
        placeField.setText("");
        priceField.setText("");
        shellField.setText("");
        nominalPriceField.setText("");
        numberField.setText("");
        valueField.setText("");
        propertyField.setText("");
        productDto=null;
        Ok.setVisible(false);
        codeTextField.setText("");
        priceTextField.setText("");

    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (codeTextField.getText() == null ||
                codeTextField.getText().length() == 0) {
            errorMessage += "Некорректно введено имя!\n";
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
    @FXML
    private void handleCancel(){
        menu.otherButtonActive();
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    public void setMainApp(MenuController menu) {
        this.menu = menu;
    }
}
