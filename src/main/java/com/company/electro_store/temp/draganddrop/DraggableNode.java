package com.company.electro_store.temp.draganddrop;

import com.company.electro_store.dto.storage.ProductDto;
import com.company.electro_store.dto.storage.RackDto;
import com.company.electro_store.dto.storage.RackIdDto;
import com.company.electro_store.dto.storage.ShellDto;
import com.company.electro_store.functions.storage.RackFunctions;
import com.company.electro_store.temp.MenuController;
import com.company.electro_store.temp.other.ProductCount.Item;
import com.company.electro_store.temp.other.ProductCount.ItemController;
import com.company.electro_store.temp.product.Product;
import com.company.electro_store.temp.storage.Shell;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;


public class DraggableNode extends AnchorPane {

        @FXML
        AnchorPane root_pane;
        @FXML
        AnchorPane scrollBack;
        @FXML
        ScrollPane scroll;

        private EventHandler<DragEvent> mContextDragOver;
        private EventHandler<DragEvent> mContextDragDropped;

        private DragIconType mType = null;

        private Point2D mDragOffset = new Point2D(0.0, 0.0);

        @FXML
        private Label title_bar;
        @FXML
        private Label close_button;
        private ArrayList<String> strings;
        private RackDto rack=new RackDto();
        private final DraggableNode self;

        private RackDto.Place place;
        public DraggableNode() {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/views/draganddrop/DraggableNode.fxml")
            );

            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            self = this;

            try {
                fxmlLoader.load();

            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

        public String getText() {
            return title_bar.getText();
        }
        public RackDto getShell(){
            return rack;
        }
        @FXML
        private void initialize() {
        }

        public void relocateToPoint(Point2D p) {

            //relocates the object to a point that has been converted to
            //scene coordinates
            Point2D localCoords = root_pane.getParent().sceneToLocal(p);

            relocate(
                    (int) (localCoords.getX() - mDragOffset.getX()),
                    (int) (localCoords.getY() - mDragOffset.getY())
            );
        }

        public DragIconType getType() {
            return mType;
        }

        public void setType(DragIconType type, String text, RackDto rack) throws IOException {

            mType = type;
            title_bar.setText(text);
            VBox vBox=new VBox();
            place=rack.getRackId().getPlace();
            for (ShellDto shell:rack.getShells()) {
                if (shell != null) {
                    for (ProductDto product : shell.getProducts()) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(MenuController.class.getResource("/views/draganddrop/productNode.fxml"));

                        HBox settingItem = loader.load();
                        ProductItemController itemController = loader.getController();
                        Item item = new Item();
                        item.setIndex(shell.getIndex().toString());
                        item.setName(product.getName());
                        itemController.setData(item);

                        vBox.getChildren().add(settingItem);
                    }
                    scroll.setContent(vBox);
                }
            }
            this.rack=rack;

            this.strings=strings;

            getStyleClass().clear();
            getStyleClass().add("dragicon");

            switch (mType) {

                case blue:
                    getStyleClass().add("icon-blue");
                    scrollBack.setStyle("-fx-background-color: blue");
                    break;

                case red:
                    getStyleClass().add("icon-red");
                    scrollBack.setStyle("-fx-background-color: red");
                    break;

                case green:
                    getStyleClass().add("icon-green");
                    scrollBack.setStyle("-fx-background-color: green");
                    break;

                case grey:
                    getStyleClass().add("icon-grey");
                    scrollBack.setStyle("-fx-background-color: grey");
                    break;

                case purple:
                    getStyleClass().add("icon-purple");
                    scrollBack.setStyle("-fx-background-color: purple");
                    break;

                case yellow:
                    getStyleClass().add("icon-yellow");
                    scrollBack.setStyle("-fx-background-color: yellow");
                    break;

                case black:
                    getStyleClass().add("icon-black");
                    scrollBack.setStyle("-fx-background-color: #000");
                    break;

                default:
                    break;
            }

        }

        public void buildNodeDragHandlers() {

            mContextDragOver = new EventHandler<DragEvent>() {

                //dragover to handle node dragging in the right pane view
                @Override
                public void handle(DragEvent event) {

                    event.acceptTransferModes(TransferMode.ANY);
                    relocateToPoint(new Point2D(event.getSceneX(), event.getSceneY()));

                    event.consume();
                }
            };

            //dragdrop for node dragging
            mContextDragDropped = new EventHandler<DragEvent>() {

                @Override
                public void handle(DragEvent event) {

                    getParent().setOnDragOver(null);
                    getParent().setOnDragDropped(null);

                    event.setDropCompleted(true);
                    System.out.println(title_bar.getText() + "relocate to " + event.getX() + event.getY());
                    RackIdDto rackIdDto=new RackIdDto(title_bar.getText(),place);
                    try {
                        RackDto rackDto=RackFunctions.readByIdandPlace(rackIdDto);
                        rackDto.setCoordX(event.getX());
                        rackDto.setCoordY(event.getY());
                        RackFunctions.updateRack(rackDto);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    event.consume();
                }
            };
            //close button click
            close_button.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    AnchorPane parent = (AnchorPane) self.getParent();
                    parent.getChildren().remove(self);
                }

            });

            //drag detection for node dragging
            title_bar.setOnDragDetected(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {

                    root_pane.getParent().setOnDragOver(null);
                    root_pane.getParent().setOnDragDropped(null);

                    root_pane.getParent().setOnDragOver(mContextDragOver);
                    root_pane.getParent().setOnDragDropped(mContextDragDropped);

                    //begin drag ops
                    mDragOffset = new Point2D(event.getX(), event.getY());

                    relocateToPoint(
                            new Point2D(event.getSceneX(), event.getSceneY())
                    );

                    ClipboardContent content = new ClipboardContent();
                    DragContainer container = new DragContainer();

                    container.addData("type", mType.toString());
                    content.put(DragContainer.AddNode, container);

                    startDragAndDrop(TransferMode.ANY).setContent(content);

                    event.consume();
                }

            });
        }
}
