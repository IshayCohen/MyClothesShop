package ClothesShopPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Authentication {
    private static final String USERS_FILE = "users.txt";
    private static Map<String, String> users = loadUsers();
    private static Map<String, String> branches = loadBranches();

    private static Map<String, String> loadUsers() {
        Map<String, String> usersMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length == 3) {
                    usersMap.put(parts[0], parts[1]); // username -> password
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersMap;
    }

    private static Map<String, String> loadBranches() {
        Map<String, String> branchesMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                if (parts.length == 3) {
                    branchesMap.put(parts[0], parts[2]); // username -> branch
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return branchesMap;
    }

    public static boolean isValidUser(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public static String getBranch(String username) {
        return branches.get(username);
    }

    public static void addUser(String username, String password, String branch) {
        users.put(username, password);
        branches.put(username, branch);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true));
            writer.write(username + " : " + password + " : " + branch);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
