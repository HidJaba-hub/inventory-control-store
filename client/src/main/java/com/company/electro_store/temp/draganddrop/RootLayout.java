package com.company.electro_store.temp.draganddrop;

import com.company.electro_store.dto.storage.*;
import com.company.electro_store.functions.storage.ProductFunction;
import com.company.electro_store.functions.storage.RackFunctions;
import com.company.electro_store.functions.storage.ShellFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.authorization.CheckButton;
import com.company.electro_store.temp.product.Product;
import com.company.electro_store.temp.storage.Rack;
import com.company.electro_store.temp.storage.Shell;
import com.company.electro_store.temp.storage.ShellController;
import com.company.electro_store.util.managers.GsonBuilder;
import com.company.electro_store.util.managers.Validator;
import com.google.gson.Gson;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class RootLayout extends AnchorPane{
    public AnchorPane root_pane;
    @FXML
    private SplitPane base_pane;
    @FXML
    private AnchorPane right_pane;
    @FXML
    private Label changer;
    @FXML
    private VBox left_pane;
    @FXML
    private TableView<Rack> rackTable;
    @FXML
    private TableColumn<Rack,String > nameColumn;
    @FXML
    private TableColumn<Rack,String> colorColumn;
    @FXML
    private ScrollPane shellScroll;
    private static Gson gson = GsonBuilder.getGson();
    private DragIcon mDragOverIcon = null;
    private Product selectedProduct;

    private EventHandler<DragEvent> mIconDragOverRoot = null;
    private EventHandler<DragEvent> mIconDragDropped = null;
    private EventHandler<DragEvent> mIconDragOverRightPane = null;
    private MenuController menu;
    private ShellDto selectedShell;
    private VBox vBox;
    private RackDto.Place place;
    private Stage dialogStage;
    @FXML
    private Button addRack;
    @FXML
    private Button deleteRack;
    @FXML private Button addShell;
    @FXML private Button deleteShell;
    @FXML private Button editShell;
    public RootLayout() {
    }
    public void checkButton(){
        CheckButton.check(null,addRack);
        CheckButton.check(null,deleteRack);
        CheckButton.check(null,editShell);
        CheckButton.check(null,addShell);
        CheckButton.check(null,deleteShell);
    }
    public void setRoot(AnchorPane root, MenuController menu, RackDto.Place place){
        this.root_pane=root;
        this.menu=menu;
        this.place=place;
        if(place.equals(RackDto.Place.STORAGE))rackTable.setItems(menu.getStorageRackData());
        else rackTable.setItems(menu.getHallRackData());
    }
    private void showShellDetails(Rack rack) throws IOException {
        VBox vbox=new VBox();
        if (rack != null&&rack.getShells().size()>0) {
                for(Shell shell:rack.getShells()) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(MenuController.class.getResource("/views/storage/shell.fxml"));

                    HBox shellItem = loader.load();
                    ShellController shellController = loader.getController();
                    shellController.setData(shell);
                    vbox.getChildren().add(shellItem);
                    shellItem.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED,
                            new EventHandler<javafx.scene.input.MouseEvent>() {
                                @Override public void handle(MouseEvent e) {
                                    if(vBox!=null)vBox.setStyle("-fx-background-color: transparent");
                                    VBox vbox=(VBox) shellItem.getChildren().get(0);
                                    HBox hBox=(HBox) vbox.getChildren().get(0);
                                    vbox=(VBox) hBox.getChildren().get(0);
                                    vbox.setStyle("-fx-background-color: #fd9e44");
                                    vBox=vbox;
                                    Label label=(Label) vbox.getChildren().get(0);
                                    try {
                                        if(place.equals(RackDto.Place.HALL)) selectedShell=ShellFunctions.readById(rack.getRackName().get()+"H"+label.getText());
                                        else selectedShell=ShellFunctions.readById(rack.getRackName().get()+"S"+label.getText());
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (ClassNotFoundException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    System.out.println("picked"+label.getText());
                                }
                            });
                }
                shellScroll.setContent(vbox);
        } else {
            HBox shellItem=new HBox();
            vbox.getChildren().add(shellItem);
            shellScroll.setContent(vbox);
        }
    }
    @FXML
    private void handleNewRack() throws IOException, ClassNotFoundException {
        Rack rack=new Rack();
        boolean okClicked = menu.showRackEditDialog(rack,place);
        if (okClicked) {
            RackFunctions.addRack(rack.getRackName().get(), place,rack.getColor());
            menu.setRackData();
            menu.storageButtonActive();
            menu.storageButtonActive();
            //setScene();
        }
    }
    @FXML
    private void handleNewShell() throws IOException, ClassNotFoundException {
        Shell shell=new Shell();
        Rack selectedRack=rackTable.getSelectionModel().getSelectedItem();
        if(selectedRack!=null) {
            boolean okClicked = menu.showShellEditDialog(shell);
            if (okClicked) {
                RackDto rackDto = RackFunctions.readByIdandPlace(new RackIdDto(selectedRack.getRackName().get(), place));
                ShellFunctions.addShell(rackDto, Double.toString(shell.getCapacity().get()));
                menu.setShellData();
                menu.setRackData();
                initialize();
            }
        }
        else {
            Validator.fieldBlank(menu, Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void handleEditShell() throws IOException, ClassNotFoundException {
        Rack selectedRack=rackTable.getSelectionModel().getSelectedItem();
        if(selectedShell!=null){
            Shell shell=new Shell(selectedRack.getRackName().get(),selectedShell.getShell_id(),place,selectedShell.getIndex(),
                    selectedShell.getCapacity(),null);
            boolean okClicked = menu.showShellEditDialog(shell);
            if (okClicked) {
                selectedShell.setCapacity(shell.getCapacity().get());
                ShellFunctions.updateShell(selectedShell);
                menu.setShellData();
                menu.setRackData();
            }
        } else {
            Validator.fieldBlank(menu, Alert.AlertType.WARNING);
        }
    }
    @FXML
    private void handleDeleteShell() throws IOException, ClassNotFoundException {
        Shell shell=new Shell();
        if(selectedShell!=null){
            ShellFunctions.deleteShell(selectedShell);
            selectedShell=null;
            vBox.setStyle("-fx-background-color: transparent");
            menu.setShellData();
            menu.setRackData();
            menu.storageButtonActive();
        }
        else{
            Validator.fieldBlank(menu, Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void changelocation(){
        if(changer.getText().equals("ПОЛКИ СКЛАДА")) {
            place= RackDto.Place.HALL;
            rackTable.setItems(menu.getHallRackData());
            changer.setText("ПОЛКИ ЗАЛА");
        }
        else{
            place= RackDto.Place.STORAGE;
            rackTable.setItems(menu.getStorageRackData());
            changer.setText("ПОЛКИ СКЛАДА");
        }
    }
    @FXML
    private void handleCancel(){dialogStage.close();}
    @FXML
    private void handleAddProduct() throws IOException, ClassNotFoundException {
        Rack selectedRack=rackTable.getSelectionModel().getSelectedItem();
        if(selectedShell.getCapacity()-selectedProduct.getWeight().get()<0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(menu.getPrimaryStage());
            alert.setTitle("Перевес");
            alert.setHeaderText("Недостаточно места");
            alert.setContentText("Выберите другую полку");
            alert.showAndWait();
            return;
        }
        if(selectedRack!=null&&selectedShell!=null) {
            ProductDto productDto=ProductFunction.readById(selectedProduct.getCode());
            productDto.setShell(selectedShell);
            ProductFunction.updateProduct(productDto);
            dialogStage.close();
        }
        else{
            Validator.fieldBlank(menu, Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void handleDeleteRack() throws IOException, ClassNotFoundException {
        // int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        Rack selectedRack = rackTable.getSelectionModel().getSelectedItem();
        if (selectedRack!=null) {
            RackDto rackDto= RackFunctions.readByIdandPlace(new RackIdDto(selectedRack.getRackName().get(),selectedRack.getPlace()));
            RackFunctions.deleteRack(rackDto);
            menu.setRackData();
            menu.storageButtonActive();
        }
        else{
            Validator.fieldBlank(menu, Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    private void initialize() {
        checkButton();
        this.colorColumn.setCellValueFactory(cellData ->
                cellData.getValue().getColorString());
        // Инициализация столбца с данными об фамилиях
        this.nameColumn.setCellValueFactory(cellData ->
                cellData.getValue().getRackName());
        rackTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                showShellDetails(newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void setScene() throws IOException, ClassNotFoundException {
        mDragOverIcon = new DragIcon();

        mDragOverIcon.setVisible(false);
        mDragOverIcon.setOpacity(0.65);
        root_pane.getChildren().add(mDragOverIcon);
        left_pane.getChildren().clear();
        for(RackDto rack: RackFunctions.readAllUsers()){
            if(rack.getRackId().getPlace().equals(menu.getPlace())) {
                DraggableNode icn = new DraggableNode();

                ArrayList<String> strings = new ArrayList<String>();
                icn.setType(rack.getColor(),rack.getRackId().getName(),rack);

                if(rack.getCoordX()!=null){
                    right_pane.getChildren().add(icn);
                    icn.relocateToPoint(new Point2D(rack.getCoordX()-32,rack.getCoordY()-10));
                    icn.buildNodeDragHandlers();
                }
                else {
                    addDragDetection(icn);
                    left_pane.getChildren().add(icn);
                }

                DropShadow dropShadow = new DropShadow();
                dropShadow.setBlurType(BlurType.GAUSSIAN);
                dropShadow.setColor(Color.color(0, 0, 0, 0.4));
                dropShadow.setHeight(3);
                dropShadow.setWidth(3);
                dropShadow.setRadius(3);

                dropShadow.setOffsetX(3);
                dropShadow.setOffsetY(2);

                dropShadow.setSpread(6);
                icn.setEffect(dropShadow);
            }
        }
        buildDragHandlers();
    }
    private void addDragDetection(DraggableNode dragIcon) {

        dragIcon.setOnDragDetected (new EventHandler <MouseEvent> () {

            @Override
            public void handle(MouseEvent event) {

                // set drag event handlers on their respective objects
                base_pane.setOnDragOver(mIconDragOverRoot);
                right_pane.setOnDragOver(mIconDragOverRightPane);
                right_pane.setOnDragDropped(mIconDragDropped);

                // get a reference to the clicked DragIcon object
                DraggableNode icn = (DraggableNode) event.getSource();
                left_pane.getChildren().remove(event.getSource());
                //begin drag ops
                mDragOverIcon.setType(icn.getType());
                mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                DragContainer container = new DragContainer();

                container.addData ("type", mDragOverIcon.getType().toString());
                container.addData("text",icn.getText());
                container.addData("shells",gson.toJson(icn.getShell()));
                content.put(DragContainer.AddNode, container);

                mDragOverIcon.startDragAndDrop (TransferMode.ANY).setContent(content);
                mDragOverIcon.setVisible(true);
                mDragOverIcon.setMouseTransparent(true);
                event.consume();
            }
        });
    }

    private void buildDragHandlers() {

        //drag over transition to move widget form left pane to right pane
        mIconDragOverRoot = new EventHandler <DragEvent>() {

            @Override
            public void handle(DragEvent event) {

                Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

                //turn on transfer mode and track in the right-pane's context
                //if (and only if) the mouse cursor falls within the right pane's bounds.
                if (!right_pane.boundsInLocalProperty().get().contains(p)) {

                    event.acceptTransferModes(TransferMode.ANY);
                    mDragOverIcon.relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));
                    return;
                }

                event.consume();
            }
        };

        mIconDragOverRightPane = new EventHandler <DragEvent> () {

            @Override
            public void handle(DragEvent event) {

                event.acceptTransferModes(TransferMode.ANY);

                //convert the mouse coordinates to scene coordinates,
                //then convert back to coordinates that are relative to
                //the parent of mDragIcon.  Since mDragIcon is a child of the root
                //pane, coodinates must be in the root pane's coordinate system to work
                //properly.
                mDragOverIcon.relocateToPoint(
                        new Point2D(event.getSceneX(), event.getSceneY())
                );
                event.consume();
            }
        };

        mIconDragDropped = new EventHandler <DragEvent> () {

            @Override
            public void handle(DragEvent event) {

                DragContainer container =
                        (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

                container.addData("scene_coords",
                        new Point2D(event.getSceneX(), event.getSceneY()));

                ClipboardContent content = new ClipboardContent();
                content.put(DragContainer.AddNode, container);

                event.getDragboard().setContent(content);
                event.setDropCompleted(true);
            }
        };

        root_pane.setOnDragDone (new EventHandler <DragEvent> (){

            @Override
            public void handle (DragEvent event) {

                right_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
                right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
                base_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);

                mDragOverIcon.setVisible(false);

                DragContainer container =
                        (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);
                if (container != null) {
                    if (container.getValue("scene_coords") != null) {

                        DraggableNode node = new DraggableNode();

                        try {
                            node.setType(DragIconType.valueOf(container.getValue("type")),container.getValue("text"),gson.fromJson((String) container.getValue("shells"),RackDto.class));
                            DropShadow dropShadow = new DropShadow();
                           // node.setStyle("dropshadow(three-pass-box, derive(cadetblue, -20%), 10, 0, 4, 4)");
                            dropShadow.setBlurType(BlurType.GAUSSIAN);
                            dropShadow.setColor(Color.color(0,0,0,0.4));
                            dropShadow.setHeight(3);
                            dropShadow.setWidth(3);
                            dropShadow.setRadius(3);

                            dropShadow.setOffsetX(3);
                            dropShadow.setOffsetY(2);

                            dropShadow.setSpread(6);
                            node.setEffect(dropShadow);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        right_pane.getChildren().add(node);

                        Point2D cursorPoint = container.getValue("scene_coords");

                        node.relocateToPoint(
                                new Point2D(cursorPoint.getX() - 32, cursorPoint.getY() - 32)
                        );
                        node.buildNodeDragHandlers();
                        System.out.println ("Moved node " + container.getValue("scene_coords"));
                    }
                }

                container = (DragContainer) event.getDragboard().getContent(DragContainer.DragNode);

                if (container != null) {
                    if (container.getValue("type") != null)
                        System.out.println ("Moved node " + container.getValue("type"));
                }

                event.consume();
            }
        });
    }
    public void setProduct(Product product){
        selectedProduct=product;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
