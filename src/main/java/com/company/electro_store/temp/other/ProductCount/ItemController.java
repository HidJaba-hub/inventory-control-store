package com.company.electro_store.temp.other.ProductCount;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemController {
    @FXML
    private Label name;
    @FXML
    private Label cost;
    public ItemController(){

    }
    public void setData(Item item){
        name.setText(item.getName());
        cost.setText(item.getCost());
    }
}
