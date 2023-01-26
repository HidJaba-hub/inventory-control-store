package com.company.electro_store.temp.product;

import com.company.electro_store.HelloApplication;
import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.functions.Action;
import com.company.electro_store.functions.storage.ProductFunction;
import com.company.electro_store.functions.storage.PropertyFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.authorization.CheckButton;
import com.company.electro_store.temp.draganddrop.RootLayout;
import com.company.electro_store.util.managers.Validator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> codeColumn;
    @FXML
    private Label nameField;
    @FXML
    private Label weightLabel;
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
    //продукты
    @FXML
    private  TableView<Property> propertyTable;
    @FXML
    private TableColumn<Property,String> propertyNameColumn;
    @FXML
    private TableColumn<Property, String> propertyValueColumn;
    @FXML
    private Label enterPrice;
    @FXML
            private HBox buttonBar;
    @FXML
            private Button okButton;
    @FXML
            private TextField priceEnterField;
    @FXML private Button addProduct;
    @FXML private Button editProduct;
    @FXML private Button deleteProduct;
    @FXML private Button addPrice;
    @FXML private Button addProductPlace;
    @FXML private Button addProperty;
    @FXML private Button deleteProperty;
    @FXML private  Button editProperty;
    @FXML private Button addPropertyToProduct;
    MenuController menu;
    AnchorPane anchorPane;
    public void checkButtons(){
        CheckButton.check(Action.ADDPRODUCT,addProduct);
        CheckButton.check(Action.DELETEPRODUCT,deleteProduct);
        CheckButton.check(Action.EDITPRODUCT,editProduct);
        CheckButton.check(Action.ADDPRICE,addPrice);
        CheckButton.check(Action.ADDPRODUCTPLACE,addProductPlace);
        CheckButton.check(Action.ADDPROPERTYTOPRODUCT,addPropertyToProduct);
        CheckButton.check(null,addProperty);
        CheckButton.check(null,deleteProperty);
        CheckButton.check(null,editProperty);
    }
    public ProductController(){

    }
    @FXML
    private void initialize() {
        checkButtons();
        //метод setCellValueFactory определяет, как необходимо заполнить данные каждой ячейки
        // Инициализация столбца с данными об именах
        this.nameColumn.setCellValueFactory(cellData ->
                cellData.getValue().productNameProperty());
        // Инициализация столбца с данными об фамилиях
        this.codeColumn.setCellValueFactory(cellData ->
                cellData.getValue().getProductCode().asObject());
        this.propertyNameColumn.setCellValueFactory(cellData->
                cellData.getValue().propertyNameProperty());
        this.propertyValueColumn.setCellValueFactory(cellData->
                cellData.getValue().propertyValueProperty());
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> showProductDetails(newValue));

    }
    //handlers
    @FXML
    private void handleNewProduct() throws IOException, ClassNotFoundException {
        Product product=new Product();
        boolean okClicked = menu.showProductEditDialog(product,"add");
        if (okClicked) {
            PropertyDto propertyDto=PropertyFunctions.readByName(product.getProperty());
            ProductFunction.addProduct(product.getCode().toString(),product.getNominalPrice().toString(),propertyDto,product.getProductName(),product.getNumber(),product.getWeight().get());
            menu.setProductData();
        }
    }
    @FXML
    private void handleDeleteProduct() throws IOException, ClassNotFoundException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct!=null) {
            ProductDto productDto=ProductFunction.readById(selectedProduct.getCode());
            ProductFunction.deleteProduct(productDto);
            menu.setProductData();
        }
        else{
            Validator.fieldBlank(menu,Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void handleEditProduct() throws IOException, ClassNotFoundException {
        Product selectedProduct=productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            boolean okClicked = menu.showProductEditDialog(selectedProduct,"edit");
            if (okClicked) {
                ProductDto productDto=ProductFunction.readById(selectedProduct.getCode());
                productDto.setName(selectedProduct.getProductName());
                productDto.setNominalPrice(selectedProduct.getNominalPrice());
                productDto.setNumber(selectedProduct.getNumber());
                PropertyDto propertyDto=PropertyFunctions.readByName(selectedProduct.getProperty());
                productDto.setProperty(propertyDto);
                ProductFunction.updateProduct(productDto);
                menu.setPostData();
            }
        } else {
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void handleOk() throws IOException, ClassNotFoundException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct!=null) {
            if(priceEnterField.getText()!=null&&priceEnterField.getText().length()!=0&&
            Validator.correctNumber(priceEnterField.getText())&&Double.parseDouble(priceEnterField.getText())>0) {
                ProductDto productDto = ProductFunction.readById(selectedProduct.getCode());
                productDto.setCost(Double.parseDouble(priceEnterField.getText()));
                ProductFunction.updateProduct(productDto);
                menu.setProductData();
            }

        }
        else{
            Validator.fieldBlank(menu,Alert.AlertType.INFORMATION);
        }
        addProduct.setVisible(true);
        deleteProduct.setVisible(true);
        editProduct.setVisible(true);
        addProductPlace.setVisible(true);
        okButton.setVisible(false);
        enterPrice.setVisible(false);
        priceEnterField.setVisible(false);
    }
    @FXML
    private void handleNewCost()throws IOException, ClassNotFoundException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct!=null) {
            addProduct.setVisible(false);
            deleteProduct.setVisible(false);
            editProduct.setVisible(false);
            addProductPlace.setVisible(false);
            okButton.setVisible(true);
            enterPrice.setVisible(true);
            priceEnterField.setVisible(true);
        }
        else{
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void handleAddProduct() throws IOException, ClassNotFoundException {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct!=null) {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(HelloApplication.class.getResource("/views/storage/storage_one.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(menu.getPrimaryStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            RootLayout controller = loader.getController();
            menu.setProductData();
            menu.setShellData();
            RackDto.Place place = RackDto.Place.HALL;
            menu.setRackData();
            controller.setRoot(anchorPane, menu, place);

            controller.setDialogStage(dialogStage);
            controller.setProduct(selectedProduct);
            dialogStage.showAndWait();
        }else {
            Validator.fieldBlank(menu,Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void handleNewProperty() throws IOException, ClassNotFoundException {
        Property property=new Property();
        boolean okClicked = menu.showPropertyEditDialog(property);
        if (okClicked) {
            PropertyFunctions.addProperty(property.getPropertyName(),property.getPropertyValue());
            menu.setPropertyData();
        }
    }
    @FXML
    private void handleDeleteProperty() throws IOException, ClassNotFoundException {//setNumber=0
        Property selectedProperty=propertyTable.getSelectionModel().getSelectedItem();
        if (selectedProperty!=null) {
            PropertyDto propertyDto=PropertyFunctions.readById(selectedProperty.getPropertyId());
            for(Product product:menu.getProductData()){
                if(product.getProperty()!=null) {
                    if (product.getProperty().equals(selectedProperty.getPropertyName())) {
                        ProductDto productDto = ProductFunction.readById(product.getCode());
                        productDto.setNumber(0.0);
                        ProductFunction.updateProduct(productDto);
                    }
                }
            }
            PropertyFunctions.deleteProperty(propertyDto);
            menu.setPropertyData();
            menu.setProductData();
        }
        else{
            Validator.fieldBlank(menu,Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void handleEditProperty() throws IOException, ClassNotFoundException {
        Property selectedProperty=propertyTable.getSelectionModel().getSelectedItem();
        if (selectedProperty!=null) {
            boolean okClicked = menu.showPropertyEditDialog(selectedProperty);
            if (okClicked) {
                PropertyDto propertyDto=PropertyFunctions.readById(selectedProperty.getPropertyId());
                propertyDto.setValue(selectedProperty.getPropertyValue());
                propertyDto.setName(selectedProperty.getPropertyName());
                PropertyFunctions.updateProperty(propertyDto);
                menu.setPropertyData();
                menu.setProductData();
            }
        } else {
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void handleAddProductToProperty() throws IOException, ClassNotFoundException {
        Property selectedProperty=propertyTable.getSelectionModel().getSelectedItem();
        Product selectedProduct=productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && selectedProperty!=null) {
            ProductDto productDto=ProductFunction.readById(selectedProduct.getCode());
            PropertyDto propertyDto=PropertyFunctions.readById(selectedProperty.getPropertyId());
            productDto.setProperty(propertyDto);
            ProductFunction.updateProduct(productDto);
            menu.setProductData();
        }else {
            Validator.fieldBlank(menu,Alert.AlertType.WARNING);
        }
    }
    private void showProductDetails(Product product) {
        if (product != null) {
            nameField.setText(product.getProductName());
            placeField.setText(product.getPlace());
            priceField.setText(product.getPrice().toString());
            nominalPriceField.setText(product.getNominalPrice().toString());
            shellField.setText(product.getShell());
            numberField.setText(product.getNumber().toString());
            valueField.setText(product.getValue());
            propertyField.setText(product.getProperty());
            weightLabel.setText(Double.toString(product.getWeight().get()));

        } else {
            nameField.setText("");
            placeField.setText("");
            priceField.setText("");
            nominalPriceField.setText("");
            numberField.setText("");
            valueField.setText("");
            propertyField.setText("");
        }
    }
    public void setMainApp(MenuController menu, AnchorPane anchorPane) throws IOException, ClassNotFoundException {
        this.menu = menu;
        this.anchorPane=anchorPane;
        productTable.setItems(menu.getProductData());
        propertyTable.setItems(menu.getProperty());
    }
}
