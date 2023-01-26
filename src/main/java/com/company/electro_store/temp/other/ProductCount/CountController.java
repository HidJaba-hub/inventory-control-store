package com.company.electro_store.temp.other.ProductCount;

import com.company.electro_store.dto.accountant.SalesDto;
import com.company.electro_store.functions.accountant.SalesFunctions;
import com.company.electro_store.temp.other.OtherController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class CountController {
    @FXML
    private TableView<Count> countTable;
    @FXML
    private TableColumn<Count, String> dateColumn;
    @FXML
    private TableColumn<Count, Double> expencesColumn;
    @FXML
    private TableColumn<Count, Double> profitColumn;
    @FXML
    private TableColumn<Count, Integer> amountColumn;
    @FXML
            private Label dayLabel;
    @FXML
            private Label mounthLabel;
    @FXML
            private Label yearLabel;
    OtherController menu;
    @FXML
    private void initialize() throws IOException, ClassNotFoundException {
        //метод setCellValueFactory определяет, как необходимо заполнить данные каждой ячейки
        // Инициализация столбца с данными об именах
        this.dateColumn.setCellValueFactory(cellData ->
                cellData.getValue().getDate().asString());
        // Инициализация столбца с данными об фамилиях
        this.expencesColumn.setCellValueFactory(cellData ->
                cellData.getValue().getExpences().asObject());
        this.profitColumn.setCellValueFactory(cellData->
                cellData.getValue().getProfit().asObject());
        this.amountColumn.setCellValueFactory(cellData->
                cellData.getValue().getAmount().asObject());
        CountMoney();

    }
    private void CountMoney() throws IOException, ClassNotFoundException {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        SalesDto salesDto= SalesFunctions.readByDate(sqlDate);
        if(salesDto!=null) {
            double c=salesDto.getProfit()-salesDto.getExpences();
            dayLabel.setText(Double.toString(c));
        }
        LocalDate localDate = LocalDate.now();
        List<SalesDto> salesDtoList=SalesFunctions.readAllUsers();
        for(SalesDto s:salesDtoList){
            Calendar cal = Calendar.getInstance();
            cal.setTime(s.getDate());
            int month = cal.get(Calendar.MONTH)+1;
            int year=cal.get(Calendar.YEAR);
            if(month==localDate.getMonthValue()){
                double c=Double.parseDouble(mounthLabel.getText());
                double cm=s.getProfit()-s.getExpences()+c;
                mounthLabel.setText(Double.toString(cm));
            }
            if(year==localDate.getYear()){
                double c=Double.parseDouble(yearLabel.getText());
                double cy=s.getProfit()-s.getExpences()+c;
                yearLabel.setText(Double.toString(cy));
            }
        }
    }
    public void setMainApp(OtherController menu) throws IOException, ClassNotFoundException {
        this.menu = menu;
        countTable.setItems(menu.getCountData());
    }
}
