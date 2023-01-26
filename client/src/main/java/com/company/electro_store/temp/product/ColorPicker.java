package com.company.electro_store.temp.product;

import com.company.electro_store.temp.draganddrop.DragIconType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class ColorPicker {
    @FXML
    private Label color;
    @FXML
    private Rectangle colorRectangle;
    private DragIconType dragColor;
    public void setColorPicker(DragIconType color){
        this.color.setText(color.name());
        this.dragColor=color;
        switch (color) {

            case blue:
                colorRectangle.setStyle("-fx-fill:  blue");
                break;

            case red:
                colorRectangle.setStyle("-fx-fill: red");
                break;

            case green:
                colorRectangle.setStyle("-fx-fill: green");
                break;

            case grey:
                colorRectangle.setStyle("-fx-fill: grey");
                break;

            case purple:
                colorRectangle.setStyle("-fx-fill: purple");
                break;

            case yellow:
                colorRectangle.setStyle("-fx-fill: yellow");
                break;

            case black:
                colorRectangle.setStyle("-fx-fill: black");
                break;

            default:
                break;
        }
    }
    public DragIconType getColor(){
        return dragColor;
    }
    public static DragIconType getStringColor(String color) {
        switch (color) {

            case "blue":
                return DragIconType.blue;
            case "red":
                return DragIconType.red;
            case "green":
                return DragIconType.green;

            case "grey":
                return DragIconType.grey;
            case "purple":
                return DragIconType.purple;
            case "yellow":
                return DragIconType.yellow;

            case "black":
                return DragIconType.black;

            default:
                break;
        }
        return null;
    }
}
