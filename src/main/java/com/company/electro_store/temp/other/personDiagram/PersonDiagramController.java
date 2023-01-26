package com.company.electro_store.temp.other.personDiagram;

import com.company.electro_store.temp.other.OtherController;
import com.company.electro_store.temp.other.PersonsCount.Today;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.List;

public class PersonDiagramController {
    @FXML
    private PieChart pieChart;
    private AnchorPane menu;
    @FXML
    public void initialize(){
        //pieChart.setPrefSize(500,500);

        pieChart.setLabelsVisible(true);
        pieChart.setLegendSide(Side.RIGHT);
    }
    public void setPieChart(List<Today> todays){
        for (Today today:todays){
            PieChart.Data slice=new PieChart.Data(today.getName().get()+" "+today.getSurname().get(),
                    today.getHours().get());
            pieChart.getData().add(slice);
            final Label caption = new Label("234");
            caption.setTextFill(Color.BLACK);
            caption.setStyle("-fx-font: 24 arial;");
            for (final PieChart.Data data : pieChart.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                        new EventHandler<MouseEvent>() {
                            @Override public void handle(MouseEvent e) {
                                caption.setTranslateX(e.getSceneX());
                                caption.setTranslateY(e.getSceneY());
                                caption.setText(String.valueOf(data.getPieValue()));
                            }
                        });
            }
            menu.getChildren().add(caption);


        }
    }
    public void setMainApp(AnchorPane menu){
        this.menu=menu;
    }
}
