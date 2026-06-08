package com.example.demo2;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import java.util.List;
import java.util.Optional;

public class BuyerHomeScreen {

    private static final String BG   = "#7B2D8B";
    private static final String NAVY = "#1A1A2E";
    private static final String CARD_COLOR = "#999999";

    public static void show(Buyer buyer) {
        buyer.getCart().clear();
        List<String> cartLines = FileHandler.getUserCart(buyer.getMatricNum());
        for (String line : cartLines) {
            String[] parts = line.split(",");
            String itemId = parts[1];
            int qty = Integer.parseInt(parts[2]);
            for (Product p : HelloApplication.globalCatalog) {
                if (p.getProductID().equals(itemId)) {
                    Order order = new Order((int)(Math.random() * 999999), p, qty);
                    buyer.getCart().add(order);
                    break;
                }
            }
        }

        BorderPane root = new BorderPane();
        root.setPrefSize(400, 700);
        root.setStyle("-fx-background-color: " + BG + ";");

        root.setTop(buildHeader(buyer));
        root.setCenter(buildProductGrid(buyer));
        root.setBottom(buildNavBar(buyer));

        HelloApplication.primaryStage.setScene(new Scene(root, 400, 700));
    }

    private static HBox buildHeader(Buyer buyer) {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(16, 20, 16, 20));
        header.setStyle("-fx-background-color: " + NAVY + ";");

        Label title = new Label("i-Bazaar");
        title.setFont(Font.font("System", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label greeting = new Label("Hi, " + buyer.getName() + "!");
        greeting.setStyle("-fx-text-fill: rgba(255,255,255,0.55); -fx-font-size: 12px;");

        header.getChildren().addAll(title, spacer, greeting);
        return header;
    }

    private static ScrollPane buildProductGrid(Buyer buyer) {
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(14));
        grid.setStyle("-fx-background-color: " + BG + ";");

        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col, col);

        List<Product> catalog = HelloApplication.globalCatalog;
        int column = 0, row = 0;

        for (Product p : catalog) {
            VBox card = buildProductCard(p, buyer);
            grid.add(card, column, row);
            column++;
            if (column == 2) { column = 0; row++; }
        }

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.setStyle(
            "-fx-background: " + BG + ";" +
            "-fx-background-color: " + BG + ";" +
            "-fx-border-color: transparent;"
        );
        return scroll;
    }

    private static VBox buildProductCard(Product p, Buyer buyer) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(12));
        card.setStyle(
            "-fx-background-color: " + CARD_COLOR + ";" +
            "-fx-background-radius: 16;" +
            "-fx-cursor: hand;"
        );

        StackPane imgBox = new StackPane();
        imgBox.setPrefSize(105, 95);
        imgBox.setStyle(
            "-fx-background-color: rgba(0,0,0,0.15);" +
            "-fx-background-radius: 12;"
        );
        Label letter = new Label(p.getProductName().substring(0, 1).toUpperCase());
        letter.setFont(Font.font("System", FontWeight.BOLD, 38));
        letter.setStyle("-fx-text-fill: white;");
        imgBox.getChildren().add(letter);

        Label nameLbl = new Label(p.getProductName());
        nameLbl.setFont(Font.font("System", FontWeight.BOLD, 13));
        nameLbl.setStyle("-fx-text-fill: white;");
        nameLbl.setWrapText(true);
        nameLbl.setAlignment(Pos.CENTER);
        nameLbl.setMaxWidth(145);

        Label priceLbl = new Label(String.format("RM%.2f", p.getPrice()));
        priceLbl.setStyle("-fx-text-fill: rgba(255,255,255,0.85); -fx-font-size: 12px;");

        card.getChildren().addAll(imgBox, nameLbl, priceLbl);
        card.setOnMouseClicked(e -> showOrderDialog(p, buyer));
        return card;
    }

    private static void showOrderDialog(Product p, Buyer buyer) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Order Item");
        dialog.setHeaderText(p.getProductName());

        ButtonType confirmBtn = new ButtonType("Add to Cart ✓", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmBtn, ButtonType.CANCEL);

        VBox content = new VBox(12);
        content.setPadding(new Insets(16));

        Label info = new Label(String.format(
            "Price: RM%.2f\nAvailable stock: %d units",
            p.getPrice(), p.getStock()));
        info.setStyle("-fx-font-size: 13px;");

        Spinner<Integer> spinner = new Spinner<>(1, Math.max(1, p.getStock()), 1);
        spinner.setEditable(true);
        spinner.setPrefWidth(110);

        HBox qtyRow = new HBox(12, new Label("Quantity:"), spinner);
        qtyRow.setAlignment(Pos.CENTER_LEFT);

        content.getChildren().addAll(info, new Separator(), qtyRow);
        dialog.getDialogPane().setContent(content);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == confirmBtn) {
            int qty = spinner.getValue();
            Order order = new Order((int)(Math.random() * 999999), p, qty);
            buyer.getCart().add(order);
            FileHandler.addToCart(buyer.getMatricNum(), p.getProductID(), qty);
            showAlert(Alert.AlertType.INFORMATION,
                qty + "x " + p.getProductName() + " added to cart! 🛒");
        }
    }

    private static void showCartDialog(Buyer buyer) {
        List<Order> cart = buyer.getCart();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("My Cart");
        dialog.setHeaderText("Cart Items");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        VBox content = new VBox(10);
        content.setPadding(new Insets(16));
        content.setPrefWidth(320);

        if (cart.isEmpty()) {
            content.getChildren().add(new Label("🛒  Your cart is empty."));
        } else {
            double total = 0;

            for (Order order : cart) {
                HBox row = new HBox();
                row.setAlignment(Pos.CENTER_LEFT);

                Label nameLbl  = new Label(order.getProduct().getProductName());
                Label qtyLbl   = new Label("  x" + order.getQuantity());
                qtyLbl.setStyle("-fx-text-fill: #888;");

                Region sp = new Region();
                HBox.setHgrow(sp, Priority.ALWAYS);

                Label priceLbl = new Label(String.format("RM%.2f", order.getTotalPrice()));
                priceLbl.setFont(Font.font("System", FontWeight.BOLD, 13));

                row.getChildren().addAll(nameLbl, qtyLbl, sp, priceLbl);
                content.getChildren().add(row);
                total += order.getTotalPrice();
            }

            Label totalLbl = new Label(String.format("Total:  RM%.2f", total));
            totalLbl.setFont(Font.font("System", FontWeight.BOLD, 15));

            Button checkoutBtn = new Button("Checkout  →");
            checkoutBtn.setMaxWidth(Double.MAX_VALUE);
            checkoutBtn.setStyle(
                "-fx-background-color: #6B21A8; -fx-text-fill: white;" +
                "-fx-background-radius: 8; -fx-padding: 11 0;" +
                "-fx-font-size: 13px; -fx-cursor: hand;"
            );
            double finalTotal = total;
            checkoutBtn.setOnAction(e -> {
                cart.clear();
                FileHandler.clearUserCart(buyer.getMatricNum());
                dialog.close();
                showAlert(Alert.AlertType.INFORMATION,
                    "Order placed!\nTotal paid: RM" + String.format("%.2f", finalTotal) + " 🎉");
            });

            content.getChildren().addAll(new Separator(), totalLbl, checkoutBtn);
        }

        dialog.getDialogPane().setContent(content);
        dialog.showAndWait();
    }

    private static HBox buildNavBar(Buyer buyer) {
        HBox nav = new HBox();
        nav.setStyle("-fx-background-color: " + NAVY + "; -fx-padding: 10 20;");

        Button cartBtn   = makeNavButton("🛒  Cart");
        Button logoutBtn = makeNavButton("🚪  Logout");

        cartBtn.setOnAction(e -> showCartDialog(buyer));
        logoutBtn.setOnAction(e -> LoginScreen.show());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        nav.getChildren().addAll(cartBtn, spacer, logoutBtn);
        return nav;
    }

    private static Button makeNavButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: rgba(255,255,255,0.6);" +
            "-fx-font-size: 13px;" +
            "-fx-cursor: hand;" +
            "-fx-padding: 10 0;"
        );
        return btn;
    }

    private static void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
