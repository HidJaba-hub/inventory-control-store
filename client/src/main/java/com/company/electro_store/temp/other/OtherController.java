package com.company.electro_store.temp.other;

import com.company.electro_store.dto.accountant.SalariesDto;
import com.company.electro_store.dto.accountant.SalesDto;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.functions.Action;
import com.company.electro_store.functions.accountant.SalariesFunctions;
import com.company.electro_store.functions.accountant.SalesFunctions;
import com.company.electro_store.functions.persons.PersonFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.authorization.CheckButton;
import com.company.electro_store.temp.other.Marking.MarkingController;
import com.company.electro_store.temp.other.PersonsCount.PersonCountCotroller;
import com.company.electro_store.temp.other.PersonsCount.Today;
import com.company.electro_store.temp.other.PersonsCount.Total;
import com.company.electro_store.temp.other.ProductCount.Count;
import com.company.electro_store.temp.other.ProductCount.CountController;
import com.company.electro_store.temp.other.cheque.ChequeController;
import com.company.electro_store.temp.other.personDiagram.PersonDiagramController;
import com.company.electro_store.temp.other.productDiagram.ProductDiagramController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OtherController extends MenuController {

    private ObservableList<Count> countData= FXCollections.observableArrayList();
    private ObservableList<Today> todayData= FXCollections.observableArrayList();
    private ObservableList<Total> totalData= FXCollections.observableArrayList();

    @FXML private HBox revaluation;
    @FXML private HBox cheque;
    @FXML private HBox calculationProduct;
    @FXML private HBox accountingProduct;
    @FXML private HBox calculatingPersons;
    @FXML private HBox accountingPersons;
    @FXML private GridPane grid;
    public OtherController() throws IOException, ClassNotFoundException {
    }
    public void removeGridPane(ArrayList<HBox> hBoxes){
        grid.getChildren().remove(0,6);
        ArrayList<HBox> hb=new ArrayList<>();
        for(HBox hBox:hBoxes){
            if(hBox!=null)hb.add(hBox);
        }
        int k=0;
        if(hb.size()>0) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    grid.add(hb.get(k), i, j);
                    if (hb.size() == k+1) return;
                    k++;
                }
            }
        }
    }
    @FXML
    public void initialize(){
        ArrayList<HBox> hBoxes=new ArrayList<>();
        hBoxes.add(CheckButton.hboxList(Action.REVALUATION,revaluation));
        hBoxes.add(CheckButton.hboxList(Action.CALCULATIONPRODUCT,calculationProduct));
        hBoxes.add(CheckButton.hboxList(Action.CALCULATIONPERSONS,calculatingPersons));

        hBoxes.add(CheckButton.hboxList(Action.CHEQUE,cheque));
        hBoxes.add(CheckButton.hboxList(Action.ACCOUNTINGPRODUCT,accountingProduct));
        hBoxes.add(CheckButton.hboxList(Action.ACCOUNTINGPERSONS,accountingPersons));

        removeGridPane(hBoxes);

    }
    @FXML
    private void onCostButtonClick(){
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MenuController.class.getResource("/views/other/marking.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            MenuController.getRootPane().setCenter(personOverview);

            MarkingController controller=loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onChequeButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MenuController.class.getResource("/views/other/cheque.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        MenuController.getRootPane().setCenter(personOverview);

        ChequeController controller=loader.getController();
        controller.setMainApp(this);
    }
    @FXML
    private void onCountButtonCLick() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MenuController.class.getResource("/views/other/count.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        MenuController.getRootPane().setCenter(personOverview);
        countData=setCountData();
        CountController controller=loader.getController();
        controller.setMainApp(this);
    }
    @FXML
    private void onPersonCountButtonCLick() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MenuController.class.getResource("/views/other/salaries.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        MenuController.getRootPane().setCenter(personOverview);
        totalData=setTotalData();
        todayData=setTodayData();
        PersonCountCotroller controller=loader.getController();
        controller.setMainApp(this);
    }
    @FXML
    private void onProductDiagramClick() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MenuController.class.getResource("/views/other/productDiagram.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        MenuController.getRootPane().setCenter(personOverview);
        ProductDiagramController controller = loader.getController();
        countData=setCountData();
        controller.setTotalData(countData);
    }
    @FXML
    private void onPersonDiagramClick() throws IOException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(MenuController.class.getResource("/views/other/personDiagram.fxml"));
        AnchorPane personOverview = (AnchorPane) loader.load();
        MenuController.getRootPane().setCenter(personOverview);
        PersonDiagramController controller = loader.getController();
        todayData=setTodayData();
        controller.setMainApp(personOverview);
        controller.setPieChart(todayData);
    }
    public ObservableList<Count> setCountData() throws IOException, ClassNotFoundException {
        countData.clear();
        List<SalesDto> salesDto= SalesFunctions.readAllUsers();
        for(SalesDto sales:salesDto){
            countData.add(new Count(sales.getDate(),sales.getAmount(),sales.getProfit(),sales.getExpences()));
        }
        return countData;
    }
    public ObservableList<Today> setTodayData() throws IOException, ClassNotFoundException {
        todayData.clear();
        List<UserDto> users= PersonFunctions.readAllUsers();
        for(UserDto user:users){
            Today today=new Today();
            today.setName(user.getPerson().getName());
            today.setSurname(user.getPerson().getSurname());
            if(user.getMinutes()!=null){
                today.setHours((double)user.getMinutes());
            }
            if(user.getPost()!=null){
                today.setSalary(user.getPost().getCost());
            }
            if(user.getPost()!=null&&user.getMinutes()!=null){
                today.setTotal(user.getPost().getCost()* user.getMinutes());
            }
            todayData.add(today);
        }
        return todayData;
    }
    public ObservableList<Total> setTotalData() throws IOException, ClassNotFoundException {
        totalData.clear();
        List<SalariesDto> salaries= SalariesFunctions.readAllUsers();
        for(SalariesDto salary:salaries){
            totalData.add(new Total(salary.getDate().toString(),salary.getSalary()));
        }
        return totalData;
    }
    public ObservableList<Count> getCountData(){return countData;}
    public ObservableList<Total> getTotalData(){return totalData;}
    public ObservableList<Today> getTodayData(){return todayData;}

}
