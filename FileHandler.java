
import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String GLOBAL_FILE = "global_catalog.txt";
    private static final String CART_FILE = "user_cart.txt";
    private static final String SELLER_FILE = "seller_items.txt";


    public static void saveItem(String itemId, String name, double price, String sellerMatric) {
        try (FileWriter fw = new FileWriter(GLOBAL_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(itemId + "," + name + "," + price + "," + sellerMatric);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving item: " + e.getMessage());
        }
    }


    public static List<String> readAllItems() {
        List<String> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(GLOBAL_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                items.add(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading items: " + e.getMessage());
        }

        return items;
    }


    public static void addToCart(String userMatric, String itemId, int quantity) {
        try (FileWriter fw = new FileWriter(CART_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(userMatric + "," + itemId + "," + quantity);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }


    public static List<String> getUserCart(String userMatric) {
        List<String> cart = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CART_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(userMatric + ",")) {
                    cart.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading cart: " + e.getMessage());
        }

        return cart;
    }


    public static void saveSellerItem(String sellerMatric, String itemId) {
        try (FileWriter fw = new FileWriter(SELLER_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(sellerMatric + "," + itemId);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving seller item: " + e.getMessage());
        }
    }
}