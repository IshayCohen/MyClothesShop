package ProductPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaleDatabase {
	private static final String PRODUCTS_FILE = "sales.txt";

    public static List<Product> loadProducts(String branch) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 6 && parts[5].equals(branch)) {
                    String type = parts[0];
                    String name = parts[1];
                    String size = parts[2];
                    int quantity = Integer.parseInt(parts[3]);
                    double price = Double.parseDouble(parts[4]);
                    Product product = new Product(type, name, size, quantity, price, branch);
                    products.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
