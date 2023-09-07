package serverStore;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import ClothesShop.Customer;
import ClothesShop.Employee;

public class StoreServer {
    private static final int SERVER_PORT = 12345;
    private static List<Customer> customers;
    private static List<Employee> employees;

    public static void main(String[] args) {
        loadCustomersFromFile();
        loadEmployeesFromFile();

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server listening on port " + SERVER_PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                	System.out.println("Here");
                    String requestType = in.readLine();

                    if ("customer_login".equals(requestType)) {
                        // Handle customer login
                    	loadCustomersFromFile();
                        String username = in.readLine();
                        String password = in.readLine();
                        boolean customerAuthenticated = authenticateCustomer(username, password);
                        if (customerAuthenticated) {
                            out.println("login successful.");
                            System.out.println("Customer login successful.");
                        } else {
                            out.println("Customer login failed. Invalid credentials.");
                            System.out.println("Customer login failed. Invalid credentials.");
                        }
                    } else if ("employee_login".equals(requestType)) {
                        // Handle employee login
                    	 loadEmployeesFromFile();
                        String username = in.readLine();
                        String password = in.readLine();
                        boolean employeeAuthenticated = authenticateEmployee(username, password);
                        if (employeeAuthenticated) {
                            out.println("login successful.");
                            System.out.println("Employee login successful.");
                        } else {
                            out.println("Employee login failed. Invalid credentials.");
                            System.out.println("Employee login failed. Invalid credentials.");
                            
                        }
                    } else {
                        // Invalid request type
                        out.println("Invalid request.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean authenticateCustomer(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static boolean authenticateEmployee(String username, String password) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static void loadCustomersFromFile() {
        customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("customer.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                customers.add(Customer.fromText(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadEmployeesFromFile() {
        employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("employee.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                employees.add(Employee.fromText(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ... (other methods)

    public static void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("customer.txt"))) {
            for (Customer customer : customers) {
                writer.write(customer.toText());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employee.txt"))) {
            for (Employee employee : employees) {
                writer.write(employee.toText());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}