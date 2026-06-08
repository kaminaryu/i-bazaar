package com.example.demo2;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {

    public static Stage primaryStage;
    public static final ArrayList<Product> globalCatalog = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("i-Bazaar");
        primaryStage.setResizable(false);

        List<String> lines = FileHandler.readAllItems();
        for (String line : lines) {
            globalCatalog.add(Product.fromFileString(line));
        }

        LoginScreen.show();
        primaryStage.show();
    }
}
