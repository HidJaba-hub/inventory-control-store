package com.company.electro_store;

import com.company.electro_store.functions.Functions;
import com.company.electro_store.functions.GeneralFunctions;
import com.company.electro_store.temp.ScreenController;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }
    static ScreenController screenController;
    @Getter
    static Stage stage_scene;
    @Override
    public void start(Stage stage) throws IOException, DocumentException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/views/authorization/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        screenController = new ScreenController(scene);
        screenController.addScreen("registration", FXMLLoader.load(getClass().getResource("/views/authorization/registration.fxml")));
        screenController.addScreen("login", FXMLLoader.load(getClass().getResource("/views/authorization/login.fxml")));
        screenController.activate("login");
        stage.setTitle("Hello!");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage_scene=stage;
        stage.show();
    }

    public static ScreenController getScreenController() {
        return screenController;
    }
}