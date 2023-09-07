package ClothesShopPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends JFrame {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private JTextArea chatArea;
    private JTextField messageField;
    private String username , selectedUser;

    public ChatClient(String serverAddress, int serverPort, String selectedUser, String user) {
        this.username = user;
        this.selectedUser = selectedUser;
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            setTitle("Chat Client - " + selectedUser);
            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            chatArea = new JTextArea();
            chatArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(chatArea);

            messageField = new JTextField();
            JButton sendButton = new JButton("Send");
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendMessage();
                }
            });

            JPanel inputPanel = new JPanel(new BorderLayout());
            inputPanel.add(messageField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);

            add(scrollPane, BorderLayout.CENTER);
            add(inputPanel, BorderLayout.SOUTH);

            // Listen for incoming messages from the server
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String message;
                        while ((message = in.readLine()) != null) {
                            appendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String messageText = messageField.getText().trim();
        if (!messageText.isEmpty()) {
            if (messageText.startsWith("/private")) {
                // Send a private message
                String[] parts = messageText.split(" ", 3);
                if (parts.length == 3) {
                    String recipient = parts[1];
                    String privateMessage = parts[2];
                    String message = "/private " + recipient + " " + privateMessage;
                    out.println(message);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid private message format. Use /private username message");
                }
            } else {
                // Send a regular message
                String message = username + ": " + messageText + "^" + username + "^" + selectedUser;
                out.println(message);
            }
            messageField.setText("");
        }
    }

    private void appendMessage(String message) {
        chatArea.append(message + "\n");
    }
}
