package com.company.electro_store.temp.storage;

import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.product.Product;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class ShellController {
    @FXML
    private Label capacityLabel;
    @FXML
    private Label indexLabel;
    @FXML
    private ScrollPane scrollPane;
    public void setData(Shell shell) throws IOException {
        capacityLabel.setText(Double.toString(shell.getCapacity().get()));
        indexLabel.setText(Integer.toString(shell.getIndex().get()));
        VBox vBox=new VBox();
        for(Product product: shell.getProduct()){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MenuController.class.getResource("/views/storage/product.fxml"));

            HBox productItem = loader.load();
            ProductShellController productShellController=loader.getController();
            productShellController.setData(product);
            vBox.getChildren().add(productItem);
        }
        scrollPane.setContent(vBox);
    }
}
