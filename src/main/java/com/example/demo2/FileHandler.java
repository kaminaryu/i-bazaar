// FileHandler — reads and writes CSV files to save products and carts between app runs
package com.example.demo2;

import java.io.*;
import java.util.*;

public class FileHandler {

    private static final String DIR = "src/main/database/";
    private static final String GLOBAL_FILE = DIR + "global_catalog.csv";
    private static final String CART_FILE = DIR + "user_cart.csv";

    // add a new product
    public static void saveItem(String itemId, String name, double price, int stock, String sellerMatric) {
        try (FileWriter fw = new FileWriter(GLOBAL_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(itemId + "," + name + "," + price + "," + stock + "," + sellerMatric);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving item: " + e.getMessage());
        }
    }

    // read items from global catalog and convert to string
    public static List<String> readAllItems() {
        List<String> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(GLOBAL_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    items.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading items: " + e.getMessage());
        }

        return items;
    }

    // replace the entire global_catalog.csv with a new list (for edit/delete)
    public static void overwriteCatalog(List<Product> products) {
        try (FileWriter fw = new FileWriter(GLOBAL_FILE);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (Product p : products) {
                bw.write(p.toFileString());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error writing catalog: " + e.getMessage());
        }
    }

    // add items to cart
    public static void addToCart(String userMatric, String itemId, int quantity) {
        try (FileWriter fw = new FileWriter(CART_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(userMatric + "," + itemId + "," + quantity);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error saving cart: " + e.getMessage());
        }
    }

    // get user's item using matric card
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

    // delete user's card ikut matric card, for checkout usually
    public static void clearUserCart(String userMatric) {
        List<String> remaining = new ArrayList<>();

        // read all lines that doesnt have user matric num
        try (BufferedReader br = new BufferedReader(new FileReader(CART_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(userMatric + ",")) {
                    remaining.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading cart: " + e.getMessage());
        }

        // write the remaining lines back
        try (FileWriter fw = new FileWriter(CART_FILE);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String entry : remaining) {
                bw.write(entry);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing cart: " + e.getMessage());
        }
    }
}
