package chatServer;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	private static final int PORT = 12347;
	private static Set<ClientHandler> clientHandlers = new HashSet<>();

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
		private String recipient;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				writer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// Ask the client for its username
				
				username = reader.readLine();
				recipient = reader.readLine();
				synchronized (clientHandlers) {
					clientHandlers.add(this);
				}

				// Inform all clients about the new user
				broadcast(username + " has joined the chat.");

				String message;
				while ((message = reader.readLine()) != null) {
					if (true) {
						// Handle private message

						sendPrivateMessage(username, recipient, message);
					} 
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// Remove the client from the set of handlers and inform others
				synchronized (clientHandlers) {
					clientHandlers.remove(this);
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
			for (ClientHandler handler : clientHandlers) {
				if (handler.username.equals(recipient)) {
					handler.writer.println(message);
					return; // Send the message to the first matching recipient
				}
			}
			writer.println("Error: The user " + recipient + " is not online.");
		}
	}

	private static void broadcast(String message) {
		synchronized (clientHandlers) {
			for (ClientHandler handler : clientHandlers) {
				handler.writer.println(message);
			}
		}
	}
}