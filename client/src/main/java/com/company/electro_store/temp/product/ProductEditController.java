package com.company.electro_store.temp.product;

import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.worker.person.Person;
import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

/*Окно для изменения информации об адресате*/
public class ProductEditController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField codeField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField nominalPriceField;
    @FXML
    private TextField numberField;
    @FXML
    private ComboBox<String> propertyScroll;
    private Stage dialogStage;
    private Product product;
    private boolean okClicked = false;
    private String action;
    private MenuController menu;
    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage, MenuController menu) {

        this.menu=menu;this.dialogStage = dialogStage;
    }

    public void setProduct(Product product) {
        this.product=product;
        nameField.setText(product.getProductName());
        weightField.setText(Double.toString(product.getWeight().get()));
        propertyScroll.setValue(product.getProperty());
        codeField.setText(product.getCode().toString());
        if(action.equals("add"))codeField.setEditable(true);
        if(action.equals("edit"))codeField.setEditable(false);
        nominalPriceField.setText(product.getNominalPrice().toString());
        numberField.setText(product.getNumber().toString());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            product.setProductCode(Integer.parseInt(codeField.getText()));
            product.setProductName(nameField.getText());
            String selectedValue=propertyScroll.getSelectionModel().getSelectedItem();
            product.setProductProperty(selectedValue);
            product.setNumber(Double.parseDouble(numberField.getText()));
            product.setWeight(Double.parseDouble(weightField.getText()));
            product.setNominalPrice(Double.parseDouble(nominalPriceField.getText()));
            okClicked = true;
            codeField.setEditable(true);
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
            errorMessage += "Некорректно введено название товара!\n";
        }
        if (codeField.getText() == null ||
                codeField.getText().length() == 0 || !Validator.correctNumber(codeField.getText())||
                Double.parseDouble(codeField.getText())<0) {
            errorMessage += "Некорректно введена фамилия!\n";
        }
        if(product==null) {
            for (Product p : menu.getProductData()) {
                if (p.getProductCode().get() == Integer.parseInt(codeField.getText())) {
                    errorMessage += "Такой штрихкод уже есть!\n";
                }
            }
        }
        if(!Validator.correctNumber(nominalPriceField.getText())||Double.parseDouble(nominalPriceField.getText())<0){
            errorMessage += "Цена не может быть меньше 0!\n";
        }
        if(!Validator.correctNumber(numberField.getText())||Double.parseDouble(numberField.getText())<0){
            errorMessage += "Неверно введено количество!\n";
        }
        if(!Validator.correctNumber(weightField.getText())||Double.parseDouble(weightField.getText())<0){
            errorMessage += "Неверно введено количество!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверно заполнены поля");
            alert.setHeaderText("Введите корректные значения полей");
                    alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
    public void setMenu(MenuController menu, String action){
        this.menu=menu;
        this.action=action;
        propertyScroll.getItems().clear();
        for(Property property: menu.getProperty()){
            propertyScroll.getItems().add(property.getPropertyName());
        }

    }
}
