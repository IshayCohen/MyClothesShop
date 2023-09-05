import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class StoreServer {
    private static final int PORT = 12345;
    private static Map<String, String> employeeDatabase = new HashMap<>();
    private static Map<String, String> customerDatabase = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String request;
                while ((request = in.readLine()) != null) {
                    if (request.equals("employee_login")) {
                        String username = in.readLine();
                        String password = in.readLine();
                        if (authenticateEmployee(username, password)) {
                            out.println("Employee login successful");
                        } else {
                            out.println("Employee login failed");
                        }
                    } else if (request.equals("customer_login")) {
                        String username = in.readLine();
                        String password = in.readLine();
                        if (authenticateCustomer(username, password)) {
                            out.println("Customer login successful");
                        } else {
                            out.println("Customer login failed");
                        }
                    } else if (request.equals("employee_register")) {
                        String username = in.readLine();
                        String password = in.readLine();
                        if (registerEmployee(username, password)) {
                            out.println("Employee registration successful");
                        } else {
                            out.println("Employee registration failed");
                        }
                    } else if (request.equals("customer_register")) {
                        String username = in.readLine();
                        String password = in.readLine();
                        if (registerCustomer(username, password)) {
                            out.println("Customer registration successful");
                        } else {
                            out.println("Customer registration failed");
                        }
                    }
                }

                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean authenticateEmployee(String username, String password) {
            return employeeDatabase.containsKey(username) && employeeDatabase.get(username).equals(password);
        }

        private boolean authenticateCustomer(String username, String password) {
            return customerDatabase.containsKey(username) && customerDatabase.get(username).equals(password);
        }

        private boolean registerEmployee(String username, String password) {
            if (!employeeDatabase.containsKey(username)) {
                employeeDatabase.put(username, password);
                return true;
            }
            return false;
        }

        private boolean registerCustomer(String username, String password) {
            if (!customerDatabase.containsKey(username)) {
                customerDatabase.put(username, password);
                return true;
            }
            return false;
        }
    }
}

