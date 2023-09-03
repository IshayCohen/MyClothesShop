
package ClothesShopPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomerAuthentication {
    // This map will store customer usernames and their corresponding passwords
    private static Map<String, String> customerCredentials = new HashMap<>();

    // Initialize the customer credentials from a file or a database
    static {
        loadCustomerCredentials();
    }

    private static void loadCustomerCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("customerCredentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    customerCredentials.put(username, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidCustomer(String username, String password) {
        // Check if the provided username exists and the password matches
        return customerCredentials.containsKey(username) && customerCredentials.get(username).equals(password);
    }
}
