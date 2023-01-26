package com.company.electro_store.temp.draganddrop;

import com.company.electro_store.temp.other.ProductCount.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProductItemController {
    @FXML
    private Label productIndex;
    @FXML
    private Label name;
    public ProductItemController(){

    }
    public void setData(Item item){
        name.setText(item.getName());
        productIndex.setText(item.getIndex());
    }
}
