package com.company.electro_store.temp.other.cheque;

import com.company.electro_store.dto.accountant.SalesDto;
import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.functions.accountant.SalesFunctions;
import com.company.electro_store.functions.storage.ProductFunction;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.other.ProductCount.Item;
import com.company.electro_store.temp.other.ProductCount.ItemController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChequeController {
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
    private Button Ok;
    @FXML
    private ScrollPane scroll;
    @FXML
            private Label date;
    @FXML
            private Label amount;
    @FXML
            private Label all;
    MenuController menu;
    private Stage dialogStage;
    private ProductDto productDto;
    private VBox root;
    private List<ProductDto> productDtos=new ArrayList<>();
    @FXML
    public void initialize(){
        root= new VBox();
        codeTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        handleFind();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
    private void handleFind() throws IOException, ClassNotFoundException {
        if (isInputValid()) {
            productDto= ProductFunction.readById(Integer.parseInt(codeTextField.getText()));
            if(productDto!=null){
                nameField.setText(productDto.getName());
                priceField.setText(productDto.getCost().toString());
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
        productDtos.add(productDto);
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(MenuController.class.getResource("/views/other/ChequeItem.fxml"));

        HBox settingItem=loader.load();
        ItemController itemController =loader.getController();
        Item item=new Item();
        item.setCost(productDto.getCost().toString());
        item.setName(productDto.getName());
        itemController.setData(item);

        root.getChildren().add(settingItem);
        root.setPadding(new Insets(10));
        scroll.setContent(root);

        Integer number=productDtos.size();
        amount.setText(number.toString());
        Double total=Double.parseDouble(all.getText());
        total+=productDto.getCost();
        all.setText(total.toString());
        date.setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date()));
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
    }
    @FXML
    private void closeCheque() throws IOException, ClassNotFoundException {
        Double profit=0.0,expences=0.0;
        Integer amoun=productDtos.size();
        for(ProductDto p: productDtos){
            profit+=p.getCost();
            expences+=p.getNominalPrice();
            ProductFunction.deleteProduct(p);
        }
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        SalesDto salesDto=SalesFunctions.readByDate(sqlDate);
        if(salesDto!=null){
            salesDto.setAmount(salesDto.getAmount()+amoun);
            salesDto.setExpences(salesDto.getExpences()+expences);
            salesDto.setProfit(salesDto.getProfit()+profit);
            SalesFunctions.updateSales(salesDto);
        }
        else{SalesFunctions.addSales(profit,expences,amoun,sqlDate);}
        productDtos.clear();
        root.getChildren().clear();
        date.setText("");
        amount.setText("");
        all.setText("0");
    }
    private boolean isInputValid() {
        String errorMessage = "";
        if (codeTextField.getText() == null ||
                codeTextField.getText().length() == 0) {
            errorMessage += "Некорректно введен код!\n";
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
