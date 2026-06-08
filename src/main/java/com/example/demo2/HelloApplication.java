package com.example.demo2;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;

public class HelloApplication extends Application {

    public static Stage primaryStage;
    public static final ArrayList<Product> globalCatalog = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("i-Bazaar");
        primaryStage.setResizable(false);

        globalCatalog.add(new Product("P001", "IIUM Hoodie",   55.00, 10, "Clothing"));
        globalCatalog.add(new Product("P002", "Lanyard",       15.00, 50, "Accessories"));
        globalCatalog.add(new Product("P003", "CS Textbook",   35.00,  8, "Books"));
        globalCatalog.add(new Product("P004", "Tumbler",       25.00, 20, "Accessories"));
        globalCatalog.add(new Product("P005", "Kurta",         45.00, 15, "Clothing"));
        globalCatalog.add(new Product("P006", "Wireless Buds", 89.00,  5, "Electronics"));

        LoginScreen.show();
        primaryStage.show();
    }
}
