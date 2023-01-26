package com.company.electro_store.temp;

import com.company.electro_store.HelloApplication;
import com.company.electro_store.dto.persons.PostDto;
import com.company.electro_store.dto.persons.UserDto;
import com.company.electro_store.dto.persons.WorkDto;
import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.dto.storage.PropertyDto;
import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.dto.storage.ShellDto;
import com.company.electro_store.functions.Action;
import com.company.electro_store.functions.GeneralFunctions;
import com.company.electro_store.functions.persons.PersonFunctions;
import com.company.electro_store.functions.persons.PostFunctions;
import com.company.electro_store.functions.storage.ProductFunction;
import com.company.electro_store.functions.storage.PropertyFunctions;
import com.company.electro_store.functions.storage.RackFunctions;
import com.company.electro_store.functions.storage.ShellFunctions;
import com.company.electro_store.temp.authorization.CheckButton;
import com.company.electro_store.temp.authorization.LoginController;
import com.company.electro_store.temp.draganddrop.ProductItemController;
import com.company.electro_store.temp.draganddrop.RackEditController;
import com.company.electro_store.temp.draganddrop.RootLayout;
import com.company.electro_store.temp.draganddrop.ShellEditController;
import com.company.electro_store.temp.other.OtherController;
import com.company.electro_store.temp.product.*;
import com.company.electro_store.temp.storage.Rack;
import com.company.electro_store.temp.storage.Shell;
import com.company.electro_store.temp.worker.person.Person;
import com.company.electro_store.temp.worker.person.PersonController;
import com.company.electro_store.temp.worker.person.PersonEditDialogController;
import com.company.electro_store.temp.worker.post.Post;
import com.company.electro_store.temp.worker.post.PostEditDialogController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MenuController extends LoginController {
    private static BorderPane rootPane;
    @FXML
    private Button  workerButton;
    @FXML
    private Button UserButton;
    @FXML
    private Button StorageButton;
    @FXML
    private Button HallButton;
    @FXML
    private Button GoodsButton;
    @FXML
    private Button CountButton;
    @FXML
    private VBox mainVbox;
    private Stage primaryStage;
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    private ObservableList<Post> postData = FXCollections.observableArrayList();
    private ObservableList<Product> productData=FXCollections.observableArrayList();
    private ObservableList<Property> propertyData=FXCollections.observableArrayList();
    private ObservableList<Rack> storagerackData=FXCollections.observableArrayList();
    private ObservableList<Rack> hallrackData=FXCollections.observableArrayList();
    private RackDto.Place place;
    private ObservableList<Shell> shellData=FXCollections.observableArrayList();
    private final List<WorkDto> worksDto=PostFunctions.readAllWorks();;

    public MenuController() throws IOException, ClassNotFoundException {
    }

    public RackDto.Place getPlace() {
        return place;
    }

    public void checkButton(){
        CheckButton.checkAndDelete(Action.SHOWCOUNT,CountButton,mainVbox);
        CheckButton.checkAndDelete(Action.SHOWHALL,HallButton,mainVbox);
        CheckButton.checkAndDelete(Action.SHOWPRODUCTS,GoodsButton,mainVbox);
        CheckButton.checkAndDelete(Action.SHOWSTORAGE,StorageButton,mainVbox);
        CheckButton.checkAndDelete(Action.SHOWWORKERS,workerButton,mainVbox);
    }

    @FXML
    public void addReport() throws IOException, DocumentException, ClassNotFoundException {

        setProductData();
        setPersonData();
        OtherController otherController=new OtherController();
        otherController.setCountData();
        otherController.setTotalData();
        ReportController reportController=new ReportController();

        reportController.addTables(getProductData(),getPersonData(),otherController.getCountData(),otherController.getTotalData());

    }
    @FXML
    public void workerButtonActive(){
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MenuController.class.getResource("/views/workers/worker.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootPane.setCenter(personOverview);
            personData=setPersonData();
            postData=setPostData();
            PersonController controller=loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void storageButtonActive(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/views/storage/storage.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootPane.setCenter(personOverview);
            RootLayout controler=loader.getController();
            productData=setProductData();
            shellData=setShellData();
            setRackData();
            place= RackDto.Place.STORAGE;


            controler.setRoot(personOverview, this,place);
            controler.setScene();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void hallButtonActive(){
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/views/storage/storage.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootPane.setCenter(personOverview);
            RootLayout controler=loader.getController();
            productData=setProductData();
            shellData=setShellData();
            place= RackDto.Place.HALL;
            setRackData();


            controler.setRoot(personOverview, this,place);
            controler.setScene();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void otherButtonActive(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/views/other/main.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootPane.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void userButtonActive(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/views/user/user.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            rootPane.setCenter(personOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void productButtonActive(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MenuController.class.getResource("/views/products/product.fxml"));
            AnchorPane productOverview = (AnchorPane) loader.load();
            rootPane.setCenter(productOverview);
            productData=setProductData();
            propertyData=setPropertyData();
            ProductController controller=loader.getController();
            controller.setMainApp(this,productOverview);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void returnHandler() throws IOException, ClassNotFoundException {
        GeneralFunctions.logout();
        primaryStage= HelloApplication.getStage_scene();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(HelloApplication.class.getResource("/views/authorization/login.fxml"));
        AnchorPane root=loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void initRootLayout() {
        try {
            primaryStage= HelloApplication.getStage_scene();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(HelloApplication.class.getResource("/views/main/menu.fxml"));
            rootPane = loader.load();
            Scene scene = new Scene(rootPane);
            primaryStage.setScene(scene);
            primaryStage.show();
            mainVbox= (VBox) rootPane.getLeft();
            UserButton= (Button) mainVbox.getChildren().get(0);
            workerButton= (Button) mainVbox.getChildren().get(1);
            StorageButton= (Button) mainVbox.getChildren().get(2);
            HallButton= (Button) mainVbox.getChildren().get(3);
            GoodsButton= (Button) mainVbox.getChildren().get(4);
            CountButton=(Button) mainVbox.getChildren().get(5);
            checkButton();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(HelloApplication.class.getResource("/views/workers/personEdit.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage,this);
            controller.setPerson(person);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showPostEditDialog(Post post) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(HelloApplication.class.getResource("/views/workers/postEdit.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PostEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage,this);
            controller.setPost(post);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showProductEditDialog(Product product, String action) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MenuController.class.getResource("/views/products/editProduct.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ProductEditController controller = loader.getController();
            controller.setMenu(this, action);
            controller.setDialogStage(dialogStage,this);
            controller.setProduct(product);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showPropertyEditDialog(Property property) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MenuController.class.getResource("/views/products/editProperty.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PropertyEditController controller = loader.getController();
            controller.setProperty(property);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showRackEditDialog(Rack rack, RackDto.Place place) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MenuController.class.getResource("/views/storage/rackEdit.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            RackEditController controller = loader.getController();
            controller.setRack(rack);
            controller.setComboBox();
            controller.setDialogStage(dialogStage,place,this);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showShellEditDialog(Shell shell) {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(MenuController.class.getResource("/views/storage/shellEdit.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            ShellEditController controller = loader.getController();
            controller.setShell(shell);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ObservableList<Person> setPersonData() throws IOException, ClassNotFoundException {
        personData.clear();
        List<UserDto> persons= PersonFunctions.readAllUsers();
        for(UserDto person:persons){
            if(person.getPost()!=null)
                personData.add(new Person(person.getPerson().getName(),person.getPerson().getSurname(),person.getPerson().getPhone(), person.getPost().getName(),person.getUserId(),person.getLogin(),person.getPassword()));
            else personData.add(new Person(person.getPerson().getName(),person.getPerson().getSurname(),person.getPerson().getPhone(), ("Нет должности"),person.getUserId(),person.getLogin(),person.getPassword()));
        }
        return personData;
    }
    public ObservableList<Post> setPostData() throws IOException, ClassNotFoundException {
        postData.clear();
        List<PostDto> posts= PostFunctions.readAllUsers();
        for(PostDto post: posts){
            List<String> works=new ArrayList<>();
            for(WorkDto workDto:post.getActions()){
                works.add(workDto.getAction().getStr());
            }
            postData.add(new Post(post.getName(),post.getCost(), post.getPost_id(),works));
        }
        return postData;
    }
    public ObservableList<Person> getPersonData(){
        return personData;
    }
    public ObservableList<Post> getPostData(){
        return postData;
    }
    public ObservableList<Product> getProductData(){return productData;}

    public ObservableList<Product> setProductData() throws IOException, ClassNotFoundException {
        productData.clear();
        List<ProductDto> products= ProductFunction.readAllUsers();
        for(ProductDto product:products){
           Product p =new Product();
           p.setNominalPrice(product.getNominalPrice());
           p.setNumber(product.getNumber());
           p.setProductName(product.getName());
           p.setProductCode(product.getCode());
           if(product.getProperty()!=null) {
               p.setProductProperty(product.getProperty().getName());
               p.setPropertyValue(product.getProperty().getValue());
           }
           if(product.getCost()!=null)p.setPrice(product.getCost());
           if(product.getShell()!=null){
               p.setShellNumber(product.getShell().getShell_id());
               p.setPlace(product.getShell().getPlace().getStr());
           }
           p.setWeight(product.getWeight());
           productData.add(p);
        }
        return productData;
    }
    public ObservableList<Property> setPropertyData() throws IOException, ClassNotFoundException {
        propertyData.clear();
        List<PropertyDto> propertys= PropertyFunctions.readAllUsers();
        for(PropertyDto property:propertys){
           propertyData.add(new Property(property.getName(),property.getValue(),property.getPropertyId()));
        }
        return propertyData;
    }
    public ObservableList<Property> getProperty(){return propertyData;}
    public List<String> getWorks(){
        List<String> works=new ArrayList<>();
        for(WorkDto workDto:worksDto){
            works.add(workDto.getAction().getStr());
        }
        return works;
    }
    public List<WorkDto> getWorksDto(){
        return worksDto;
    }
    public void setRackData() throws IOException, ClassNotFoundException {
        storagerackData.clear();
        hallrackData.clear();
        List<RackDto> racks= RackFunctions.readAllUsers();
        List<ShellDto> sh=ShellFunctions.readAllUsers();
        for(RackDto rack:racks){
            ObservableList<Shell> shells=FXCollections.observableArrayList();
            for(Shell shell:getShellData()){
                for(ShellDto shellDto:rack.getShells()){
                    if (shell.getShellId().get().equals(shellDto.getShell_id())) {
                        shells.add(shell);
                    }
                }
            }
            if(rack.getRackId().getPlace().equals(RackDto.Place.HALL)) hallrackData.add(new Rack(rack.getRackId().getName(),rack.getRackId().getPlace(), rack.getColor(), shells));
            else storagerackData.add(new Rack(rack.getRackId().getName(),rack.getRackId().getPlace(), rack.getColor(), shells));
        }
    }
    public ObservableList<Shell> setShellData() throws IOException, ClassNotFoundException {
        shellData.clear();
        List<ShellDto> shells= ShellFunctions.readAllUsers();
        for(ShellDto shell:shells){
            ObservableList<Product> products=FXCollections.observableArrayList();
            for(Product product:getProductData()){
                for(ProductDto p:shell.getProducts()){
                    if(p.getCode().equals(product.getCode())){
                        products.add(product);
                    }
                }
            }
            shellData.add(new Shell(shell.getRack().getRackId().getName(),shell.getShell_id(),
                    shell.getPlace(),shell.getIndex(),shell.getCapacity(),products));
        }
        return shellData;
    }
    public ObservableList<Shell> getShellData(){
        return shellData;
    }
    public ObservableList<Rack> getStorageRackData(){
        return storagerackData;
    }
    public ObservableList<Rack> getHallRackData(){
        return hallrackData;
    }
    public static BorderPane getRootPane(){
        return rootPane;
    }
}
