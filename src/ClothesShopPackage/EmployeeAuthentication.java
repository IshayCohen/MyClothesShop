package ClothesShopPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmployeeAuthentication {
	
	private static final String EMPLOYEE_DB_FILE = "employee.txt";

    public static String getBranch(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_DB_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 8 && parts[0].equals(username)) {
                    // If the username matches, return the branch information
                    return parts[6]; // Assuming branch information is at index 6
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Return null if no branch information is found for the given username
        return null;
    }
	
    // This map will store employee usernames and their corresponding passwords
    private static Map<String, String> employeeCredentials = new HashMap<>();

    // Initialize the employee credentials from a file or a database
    static {
        loadEmployeeCredentials();
    }

    private static void loadEmployeeCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 8) {
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
