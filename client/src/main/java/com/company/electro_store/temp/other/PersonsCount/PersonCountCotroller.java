package com.company.electro_store.temp.other.PersonsCount;

import com.company.electro_store.dto.accountant.SalariesDto;
import com.company.electro_store.dto.accountant.SalesDto;
import com.company.electro_store.functions.accountant.SalariesFunctions;
import com.company.electro_store.functions.accountant.SalesFunctions;
import com.company.electro_store.temp.other.OtherController;
import com.company.electro_store.temp.other.ProductCount.Count;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class PersonCountCotroller {
    @FXML
    private TableView<Total> totalTable;
    @FXML
    private TableColumn<Total, String> dateColumn;
    @FXML
    private TableColumn<Total, Double> totalColumn;
    @FXML
    private TableView<Today> todayTable;
    @FXML
    private TableColumn<Today, Double> hoursColumn;
    @FXML
    private TableColumn<Today, Double> totalTodayColumn;
    @FXML
    private TableColumn<Today, Double> costColumn;
    @FXML
    private TableColumn<Today, String> nameColumn;
    @FXML
    private TableColumn<Today, String> surnameColumn;
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
                cellData.getValue().getDate());
        // Инициализация столбца с данными об фамилиях
        this.totalColumn.setCellValueFactory(cellData ->
                cellData.getValue().getSalary().asObject());
        this.hoursColumn.setCellValueFactory(cellData->
                cellData.getValue().getHours().asObject());
        this.totalTodayColumn.setCellValueFactory(cellData->
                cellData.getValue().getTotal().asObject());
        this.nameColumn.setCellValueFactory(cellData->
                cellData.getValue().getName());
        this.surnameColumn.setCellValueFactory(cellData->
                cellData.getValue().getSurname());
        this.costColumn.setCellValueFactory(cellData->
                cellData.getValue().getSalary().asObject());
        CountMoney();
    }
    public void setMainApp(OtherController menu) throws IOException, ClassNotFoundException {
        this.menu = menu;
        totalTable.setItems(menu.getTotalData());
        todayTable.setItems(menu.getTodayData());
    }
    private void CountMoney() throws IOException, ClassNotFoundException {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        SalariesDto salariesDto= SalariesFunctions.readByDate(sqlDate);
        if(salariesDto!=null) {
            dayLabel.setText(Double.toString(salariesDto.getSalary()));
        }
        LocalDate localDate = LocalDate.now();
        List<SalariesDto> salariesDtos=SalariesFunctions.readAllUsers();
        for(SalariesDto s:salariesDtos){
            Calendar cal = Calendar.getInstance();
            cal.setTime(s.getDate());
            int month = cal.get(Calendar.MONTH)+1;
            int year=cal.get(Calendar.YEAR);
            if(month==localDate.getMonthValue()){
                double c=Double.parseDouble(mounthLabel.getText());
                double cm=s.getSalary()+c;
                mounthLabel.setText(Double.toString(cm));
            }
            if(year==localDate.getYear()){
                double c=Double.parseDouble(yearLabel.getText());
                double cy=s.getSalary()+c;
                yearLabel.setText(Double.toString(cy));
            }
        }
    }
}
