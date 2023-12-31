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
	private String username;
	private String recipient;

	public ChatClient(String serverAddress, int serverPort, String username, String recipient) {
		this.username = username;
		this.recipient = recipient;
		try {
			socket = new Socket(serverAddress, serverPort);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out.println(username);
			out.println(recipient);
			setTitle(username + " chat with - " + recipient);
			setSize(400, 300);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			chatArea = new JTextArea();
			chatArea.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(chatArea);

			messageField = new JTextField();
			JButton sendButton = new JButton("Send");
			JButton saveButton = new JButton("Save"); // New "Save" button

			sendButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					sendMessage();
				}
			});

			saveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					saveChat();
				}
			});

			JPanel inputPanel = new JPanel(new BorderLayout());
			inputPanel.add(messageField, BorderLayout.CENTER);
			inputPanel.add(sendButton, BorderLayout.EAST);
			inputPanel.add(saveButton, BorderLayout.WEST); // Add the "Save" button

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
	        if (true) {
	            // Send a private message
	            String parts = messageText;

	            String message = username + ": " + parts;
	            out.println(message);

	            // Display the sent message in the sender's chat window
	            appendMessage("You: " + messageText);
	        } 
	    } else {
	        // Send a regular message
	        String message = username + ": " + messageText;
	        out.println(message);

	        // Display the sent message in the sender's chat window
	        appendMessage("You: " + messageText);
	    }
	    messageField.setText("");
	}


	private void appendMessage(String message) {
		chatArea.append(message + "\n");
	}

	private void saveChat() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showSaveDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			try (PrintWriter writer = new PrintWriter(selectedFile)) {
				String chatText = chatArea.getText();
				writer.println(chatText);
				JOptionPane.showMessageDialog(this, "Chat saved to " + selectedFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error saving chat to file.");
			}
		}
	}
}