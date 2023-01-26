package com.company.electro_store.temp.other.productDiagram;

import com.company.electro_store.temp.other.ProductCount.Count;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.util.Arrays;
import java.util.List;

public class ProductDiagramController {
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private CategoryAxis dateBar;
    private ObservableList<String> dates = FXCollections.observableArrayList();
    @FXML
    private void initialize() {
        // Получаем массив с английскими именами месяцев.
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Nov","Dec"};
        //Преобразуем его в список и добавляем в ObservableList месяцев
        dates.addAll(Arrays.asList(months));
        // Назначаем имена месяцев категориями для горизонтальнойоси
        dateBar.setCategories(dates);
    }
    public void setTotalData(List<Count> counts) {

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for(Count count:counts){

            Double d=count.getProfit().get()-count.getExpences().get();
            int m=count.getDate().get().getMonthValue()-1;
            series.getData().add(new XYChart.Data<>( dates.get(m),d));
        }
        barChart.getData().add(series);
        barChart.setLegendVisible(false);
    }
}
