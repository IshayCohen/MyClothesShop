package chatServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12346;
    private static Map<String, PrintWriter> onlineUsers = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Chat Server is running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter writer;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Ask the client for its username
                writer.println("Enter your username:");
                username = reader.readLine();

                // Add the client to the map of online users
                synchronized (onlineUsers) {
                    onlineUsers.put(username, writer);
                }

                // Inform all clients about the new user
                broadcast(username + " has joined the chat.");

                String message;
                while ((message = reader.readLine()) != null) {
                    if (message.startsWith("/private")) {
                        // Handle private message
                        String[] parts = message.split(" ", 3);
                        if (parts.length == 3) {
                            String recipient = parts[1];
                            String privateMessage = parts[2];
                            sendPrivateMessage(username, recipient, privateMessage);
                        }
                    } else {
                        // Broadcast regular messages to all clients
                        broadcast(username + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Remove the client from the map of online users and inform others
                synchronized (onlineUsers) {
                    onlineUsers.remove(username);
                }
                broadcast(username + " has left the chat.");
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendPrivateMessage(String sender, String recipient, String message) {
            PrintWriter recipientWriter = onlineUsers.get(recipient);
            if (recipientWriter != null) {
                recipientWriter.println(sender + " (private): " + message);
            } else {
                writer.println("Error: The user " + recipient + " is not online.");
            }
        }
    }

    private static void broadcast(String message) {
        synchronized (onlineUsers) {
            for (PrintWriter writer : onlineUsers.values()) {
                writer.println(message);
            }
        }
    }
}