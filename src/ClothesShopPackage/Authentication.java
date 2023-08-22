package ClothesShopPackage;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Authentication {
    private static Map<String, String> userDatabase = new HashMap<>();

    static {
        // Load existing users from a file (if any)
        loadUsersFromFile();
    }

    public static void addUser(String username, String password) {
        userDatabase.put(username, password);
        saveUsersToFile(); // Save users to file after adding a new user
    }

    public static boolean isValidUser(String username, String password) {
        if (userDatabase.containsKey(username)) {
            String storedPassword = userDatabase.get(username);
            return storedPassword.equals(password);
        }
        return false;
    }

    private static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    userDatabase.put(username, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (Map.Entry<String, String> entry : userDatabase.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

