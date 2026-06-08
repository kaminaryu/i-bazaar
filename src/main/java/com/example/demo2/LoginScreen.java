package com.example.demo2;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class LoginScreen {
    private static final String PURPLE = "#6B21A8";
    private static final String NAVY   = "#1A1A2E";

    // define JavaFx window and stuff
    public static void show() {
        VBox root = new VBox();
        root.setPrefSize(400, 700);
        root.setStyle("-fx-background-color: " + PURPLE + ";");

        VBox header = buildHeader();
        VBox form = buildForm();
        VBox.setVgrow(form, Priority.ALWAYS);

        root.getChildren().addAll(header, form);
        HelloApplication.primaryStage.setScene(new Scene(root, 400, 700));
    }

    // building header
    private static VBox buildHeader() {
        VBox header = new VBox(6);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(70, 20, 50, 20));

        // add title
        Label title = new Label("i-Bazaar");
        title.setFont(Font.font("System", FontWeight.BOLD, 42));
        title.setStyle("-fx-text-fill: white;");

        // add subtitle
        Label sub = new Label("IIUM Campus Marketplace");
        sub.setStyle("-fx-text-fill: rgba(255,255,255,0.65); -fx-font-size: 14px;");

        header.getChildren().addAll(title, sub);
        return header;
    }

    // auth form witb field and radio and submit button
    private static VBox buildForm() {
        // create form container
        VBox form = new VBox(14);
        form.setPadding(new Insets(32, 28, 28, 28));
        form.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 28 28 0 0;"
        );

        // heading => welcome message
        Label welcome = new Label("Welcome! Please login");
        welcome.setFont(Font.font("System", FontWeight.BOLD, 20));
        welcome.setStyle("-fx-text-fill: #1A1A2E;");

        // text fields for name and matric number
        TextField nameField   = makeTextField("Enter your name");
        TextField matricField = makeTextField("e.g. 2512913");

        // password field because secret
        PasswordField passField = new PasswordField();
        passField.setPromptText("Enter password");
        styleInputField(passField);

        // radio buttons to select if buyer or seller
        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton buyerRb  = new RadioButton("🛒  Buyer");
        RadioButton sellerRb = new RadioButton("🏪  Seller");

        buyerRb.setToggleGroup(roleGroup);
        sellerRb.setToggleGroup(roleGroup);

        buyerRb.setSelected(true); // default

        String radioStyle = "-fx-font-size: 14px; -fx-cursor: hand;";
        buyerRb.setStyle(radioStyle);
        sellerRb.setStyle(radioStyle);

        // row for radio
        HBox radioRow = new HBox(24, buyerRb, sellerRb);
        radioRow.setAlignment(Pos.CENTER_LEFT);

        // err messages
        Label errorLbl = new Label();
        errorLbl.setStyle("-fx-text-fill: #CC0000; -fx-font-size: 12px;");
        errorLbl.setVisible(false);
        errorLbl.setManaged(false);

        // submit button
        Button loginBtn = new Button("Login  →");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setStyle(
            "-fx-background-color: " + PURPLE + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px; -fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-padding: 13 0;" +
            "-fx-cursor: hand;"
        );

        // on submit
        loginBtn.setOnAction(e -> {
            // get data
            String name   = nameField.getText().trim();
            String matric = matricField.getText().trim();
            String pass   = passField.getText().trim();

            // check if inputs are empty
            if (name.isEmpty() || matric.isEmpty() || pass.isEmpty()) {
                errorLbl.setText("Please fill in all fields.");
                errorLbl.setVisible(true);
                errorLbl.setManaged(true);
                return;
            }

            // check if mats num is int
            try {
                Integer.parseInt(matric);
            } catch (NumberFormatException ex) {
                errorLbl.setText("Please input an integer for Matric Number.");
                errorLbl.setVisible(true);
                errorLbl.setManaged(true);
                return;
            }

            // change screens
            if (buyerRb.isSelected()) {
                Buyer buyer = new Buyer(name, matric, pass);
                BuyerHomeScreen.show(buyer);
            } else {
                Seller seller = new Seller(name, matric, pass);
                SellerDashScreen.show(seller);
            }
        });

        // add fields to form
        form.getChildren().addAll(
            welcome,
            labeledField("Full Name",     nameField),
            labeledField("Matric Number", matricField),
            labeledField("Password",      passField),
            labeledField("I am a...",     radioRow),
            errorLbl,
            loginBtn
        );

        return form;
    }

    // making fields for the form
    private static TextField makeTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        styleInputField(tf);
        return tf;
    }

    // consistent stylings
    private static void styleInputField(TextInputControl field) {
        field.setStyle(
            "-fx-background-color: #F7F7F7;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 10 14;" +
            "-fx-font-size: 13px;" +
            "-fx-pref-height: 44px;"
        );
    }

    // add labels to fields
    private static VBox labeledField(String labelText, javafx.scene.Node field) {
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-text-fill: #888; -fx-font-size: 11px; -fx-font-weight: bold;");
        return new VBox(4, lbl, field);
    }
}
