package com.example.demo2;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.util.List;
import java.util.Optional;

public class SellerDashScreen {

    private static final String BG   = "#7B2D8B";
    private static final String NAVY = "#1A1A2E";

    private static VBox productListBox;

    public static void show(Seller seller) {
        BorderPane root = new BorderPane();
        root.setPrefSize(400, 700);
        root.setStyle("-fx-background-color: " + BG + ";");

        root.setTop(buildHeader(seller));
        root.setCenter(buildProductSection(seller));
        root.setBottom(buildNavBar());

        HelloApplication.primaryStage.setScene(new Scene(root, 400, 700));
    }

    private static HBox buildHeader(Seller seller) {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(16, 20, 16, 20));
        header.setStyle("-fx-background-color: " + NAVY + ";");

        Label title = new Label("i-Bazaar");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: white;");

        Label badge = new Label(" SELLER ");
        badge.setStyle(
            "-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #BB86FC;" +
            "-fx-background-color: rgba(187,134,252,0.15);" +
            "-fx-background-radius: 4; -fx-padding: 3 6;"
        );

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label shopName = new Label(seller.getName() + "'s Shop");
        shopName.setStyle("-fx-text-fill: rgba(255,255,255,0.55); -fx-font-size: 12px;");

        header.getChildren().addAll(title, badge, spacer, shopName);
        return header;
    }

    private static VBox buildProductSection(Seller seller) {
        VBox section = new VBox();

        HBox titleRow = new HBox();
        titleRow.setAlignment(Pos.CENTER_LEFT);
        titleRow.setPadding(new Insets(16, 16, 8, 16));

        Label title = new Label("My Products");
        title.setFont(Font.font("System", FontWeight.BOLD, 18));
        title.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addBtn = new Button("＋ Add Product");
        addBtn.setStyle(
            "-fx-background-color: rgba(255,255,255,0.18);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-border-color: rgba(255,255,255,0.35);" +
            "-fx-border-radius: 20;" +
            "-fx-padding: 7 14;" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        addBtn.setOnAction(e -> showAddDialog(seller));

        titleRow.getChildren().addAll(title, spacer, addBtn);

        productListBox = new VBox(10);
        productListBox.setPadding(new Insets(8, 16, 16, 16));
        productListBox.setStyle("-fx-background-color: " + BG + ";");
        refreshList(seller);

        ScrollPane scroll = new ScrollPane(productListBox);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background: " + BG + ";" +
            "-fx-background-color: " + BG + ";" +
            "-fx-border-color: transparent;"
        );
        VBox.setVgrow(scroll, Priority.ALWAYS);

        section.getChildren().addAll(titleRow, scroll);
        return section;
    }

    private static void refreshList(Seller seller) {
        productListBox.getChildren().clear();

        List<Product> shop = seller.getShopCatalog();

        if (shop.isEmpty()) {
            Label empty = new Label("No products yet.\nTap '＋ Add Product' to start selling!");
            empty.setStyle("-fx-text-fill: rgba(255,255,255,0.45); -fx-font-size: 13px;");
            empty.setWrapText(true);
            productListBox.getChildren().add(empty);
            return;
        }

        for (Product p : shop) {
            productListBox.getChildren().add(buildProductRow(p, seller));
        }
    }

    private static HBox buildProductRow(Product p, Seller seller) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 14, 12, 14));
        row.setStyle(
            "-fx-background-color: rgba(255,255,255,0.11);" +
            "-fx-background-radius: 12;"
        );

        VBox info = new VBox(3);
        Label nameLbl = new Label(p.getProductName());
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 14));
        nameLbl.setStyle("-fx-text-fill: white;");

        Label detailLbl = new Label(String.format(
            "RM%.2f  ·  Stock: %d  ·  %s", p.getPrice(), p.getStock(), p.getCategory()));
        detailLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: rgba(255,255,255,0.65);");

        info.getChildren().addAll(nameLbl, detailLbl);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button editBtn = new Button("✎");
        editBtn.setStyle(
            "-fx-background-color: rgba(255,255,255,0.18); -fx-text-fill: white;" +
            "-fx-background-radius: 50; -fx-font-size: 14px; -fx-cursor: hand;" +
            "-fx-pref-width: 34; -fx-pref-height: 34;"
        );
        editBtn.setOnAction(e -> showEditDialog(p, seller));

        Button delBtn = new Button("✕");
        delBtn.setStyle(
            "-fx-background-color: #CC3333; -fx-text-fill: white;" +
            "-fx-background-radius: 50; -fx-font-weight: bold; -fx-cursor: hand;" +
            "-fx-pref-width: 34; -fx-pref-height: 34;"
        );
        delBtn.setOnAction(e -> confirmDelete(p, seller));

        row.getChildren().addAll(info, spacer, editBtn, delBtn);
        return row;
    }

    private static void showAddDialog(Seller seller) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Product");
        dialog.setHeaderText("Fill in the product details");

        ButtonType addBtn = new ButtonType("Add Product", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(16));

        TextField idField    = tf("e.g. P007");
        TextField nameField  = tf("Product name");
        TextField priceField = tf("e.g. 25.00");
        TextField stockField = tf("e.g. 10");
        TextField catField   = tf("e.g. Clothing");

        form.add(new Label("Product ID:"), 0, 0); form.add(idField,    1, 0);
        form.add(new Label("Name:"),       0, 1); form.add(nameField,  1, 1);
        form.add(new Label("Price (RM):"), 0, 2); form.add(priceField, 1, 2);
        form.add(new Label("Stock:"),      0, 3); form.add(stockField, 1, 3);
        form.add(new Label("Category:"),   0, 4); form.add(catField,   1, 4);

        dialog.getDialogPane().setContent(form);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == addBtn) {
            try {
                String id    = idField.getText().trim();
                String name  = nameField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                int    stock = Integer.parseInt(stockField.getText().trim());
                String cat   = catField.getText().trim();

                if (id.isEmpty() || name.isEmpty() || cat.isEmpty()) {
                    alert("All fields are required."); return;
                }

                Product newProd = new Product(id, name, price, stock, cat);
                seller.getShopCatalog().add(newProd);
                HelloApplication.globalCatalog.add(newProd);
                refreshList(seller);

            } catch (NumberFormatException ex) {
                alert("Price and Stock must be valid numbers.");
            }
        }
    }

    private static void showEditDialog(Product p, Seller seller) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit: " + p.getProductName());
        dialog.setHeaderText("Update price or stock");

        ButtonType saveBtn = new ButtonType("Save Changes", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveBtn, ButtonType.CANCEL);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(16));

        TextField priceField = new TextField(String.format("%.2f", p.getPrice()));
        TextField stockField = new TextField(String.valueOf(p.getStock()));

        form.add(new Label("New Price (RM):"), 0, 0); form.add(priceField, 1, 0);
        form.add(new Label("New Stock:"),      0, 1); form.add(stockField, 1, 1);

        dialog.getDialogPane().setContent(form);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == saveBtn) {
            try {
                double newPrice = Double.parseDouble(priceField.getText().trim());
                int    newStock = Integer.parseInt(stockField.getText().trim());
                p.setPrice(newPrice);
                p.updateStock(newStock - p.getStock());
                refreshList(seller);
            } catch (NumberFormatException ex) {
                alert("Enter valid numbers.");
            }
        }
    }

    private static void confirmDelete(Product p, Seller seller) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Product");
        confirm.setHeaderText("Delete \"" + p.getProductName() + "\"?");
        confirm.setContentText("This will remove it from the marketplace.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            seller.getShopCatalog().remove(p);
            HelloApplication.globalCatalog.remove(p);
            refreshList(seller);
        }
    }

    private static HBox buildNavBar() {
        HBox nav = new HBox();
        nav.setStyle("-fx-background-color: " + NAVY + "; -fx-padding: 10 0;");
        nav.setAlignment(Pos.CENTER);

        Button dashBtn   = makeNavBtn("🏪  Dashboard");
        Button logoutBtn = makeNavBtn("🚪  Logout");
        logoutBtn.setOnAction(e -> LoginScreen.show());

        for (Button b : new Button[]{dashBtn, logoutBtn}) {
            HBox.setHgrow(b, Priority.ALWAYS);
            b.setMaxWidth(Double.MAX_VALUE);
        }

        nav.getChildren().addAll(dashBtn, logoutBtn);
        return nav;
    }

    private static Button makeNavBtn(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: rgba(255,255,255,0.6);" +
            "-fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10 0;"
        );
        return btn;
    }

    private static TextField tf(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        return tf;
    }

    private static void alert(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}
