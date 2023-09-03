package ClothesShopPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmployeeAuthentication {
    // This map will store employee usernames and their corresponding passwords
    private static Map<String, String> employeeCredentials = new HashMap<>();

    // Initialize the employee credentials from a file or a database
    static {
        loadEmployeeCredentials();
    }

    private static void loadEmployeeCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("employeeCredentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    employeeCredentials.put(username, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidEmployee(String username, String password) {
        // Check if the provided username exists and the password matches
        return employeeCredentials.containsKey(username) && employeeCredentials.get(username).equals(password);
    }
}
