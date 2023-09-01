package ClothesShopPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManagementPage extends JFrame {
    private DefaultListModel<String> userListModel;

    public UserManagementPage() {
        setTitle("User Management Page");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Create a panel to display user information and delete buttons
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());

        // Load user data from the users.txt file
        userListModel = new DefaultListModel<>();
        loadUsersFromTextFile();

        JList<String> userList = new JList<>(userListModel);
        JScrollPane userListScrollPane = new JScrollPane(userList);

        // Create a delete button
        JButton deleteButton = new JButton("Delete User");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected user(s) for deletion
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null) {
                    // Remove the selected user from the list model
                    userListModel.removeElement(selectedUser);
                    // Update the users.txt file to remove the user
                    deleteUserFromTextFile(selectedUser);
                }
            }
        });

        userPanel.add(userListScrollPane, BorderLayout.CENTER);
        userPanel.add(deleteButton, BorderLayout.SOUTH);

        add(userPanel);
    }

    private void loadUsersFromTextFile() {
        // Read user data from the users.txt file and add them to the list model
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                userListModel.addElement(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserFromTextFile(String userToDelete) {
        // Read all users from the users.txt file and write them back without the user to delete
        List<String> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(userToDelete)) {
                    users.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated user list back to the users.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (String user : users) {
                writer.write(user);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
