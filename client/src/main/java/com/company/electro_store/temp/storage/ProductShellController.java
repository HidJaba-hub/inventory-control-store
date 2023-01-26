package com.company.electro_store.temp.storage;

import com.company.electro_store.temp.product.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProductShellController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label weightLabel;
    @FXML
    private Label codeLabel;
    public void setData(Product product){
        this.nameLabel.setText(product.getProductName());
        this.weightLabel.setText(Double.toString(product.getWeight().get()));
        this.codeLabel.setText(Integer.toString(product.getCode()));
    }
}
