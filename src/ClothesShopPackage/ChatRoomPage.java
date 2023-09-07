package ClothesShopPackage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomPage extends JFrame {
    private JComboBox<String> userComboBox;

    public ChatRoomPage(String username) {
        setTitle("Chat Room");
        setSize(400, 200);
        //setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Create a JComboBox for selecting users
        userComboBox = new JComboBox<>();
        populateUserComboBox(); // Populate the combo box with user names from users.txt

        // Create a JButton to open a chat with the selected user
        JButton openChatButton = new JButton("Open Chat");
        openChatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String selectedUser = (String) userComboBox.getSelectedItem();
            	if (selectedUser != null) {
            	    // Create a chat client with the selected user's information
            	    ChatClient chatClient = new ChatClient("10.0.0.16" , 12346 , selectedUser, username);
            	    chatClient.setVisible(true);
            	}
            }
        });

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(userComboBox, BorderLayout.CENTER);
        panel.add(openChatButton, BorderLayout.SOUTH);

        // Add the panel to the frame
        add(panel);
    }

    private void populateUserComboBox() {
        // Read user names from the users.txt file and populate the combo box
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            List<String> users = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    users.add(parts[0]);
                }
            }
            for (String user : users) {
                userComboBox.addItem(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
