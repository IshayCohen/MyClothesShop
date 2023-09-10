package ClothesShop;

import javax.swing.*;

import ClothesShopPackage.CustomerHomePage;
import ClothesShopPackage.EmployeeAuthentication;
import ClothesShopPackage.HolonPage;
import ClothesShopPackage.HomePage;
import ClothesShopPackage.RishonLeTzionPage;
import ClothesShopPackage.TelAvivPage;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class StoreClient {
	private static final String SERVER_IP = "10.0.0.16";
	private static final int SERVER_PORT = 12348;
	private static Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;
	private static JFrame mainFrame;
	private static JTabbedPane tabbedPane = new JTabbedPane();
	private static List<Customer> customers = new ArrayList<>();
	private static List<Employee> employees = new ArrayList<>();
	private static int flag = 1;

	public static void main(String[] args) {
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			SwingUtilities.invokeLater(() -> createGUI(tabbedPane));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createGUI(JTabbedPane tabbedPane) {
	    mainFrame = new JFrame("Store Login and Register");
	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    mainFrame.setSize(400, 300); // Increased height for better visibility
	    mainFrame.setLayout(new BorderLayout());

	    // Create employee login and customer login panels
	    JPanel employeePanel = createLoginPanel("Employee", "employee_login", tabbedPane);
	    JPanel customerPanel = createLoginPanel("Customer", "customer_login", tabbedPane);

		if (flag == 1) {
			tabbedPane.addTab("Employee", employeePanel);
			tabbedPane.addTab("Customer", customerPanel);
		}
		flag++;
	    mainFrame.add(tabbedPane, BorderLayout.CENTER);

	    // Center the frame on the screen
	    mainFrame.setLocationRelativeTo(null);

	    mainFrame.setVisible(true);
	}

	private static JPanel createLoginPanel(String userType, String requestType, JTabbedPane tabbedPane) {
		JPanel panel = new JPanel();
		JTextField usernameField = new JTextField(20);
		JPasswordField passwordField = new JPasswordField(20);
		JButton loginButton = new JButton("Login");
		JButton registerButton = new JButton("Register");

		loginButton.addActionListener(e -> {
			String username = usernameField.getText();
			String password = new String(passwordField.getPassword());
			out.println(requestType);
			out.println(username);
			out.println(password);
			int selectedIndex = tabbedPane.getSelectedIndex();
			String selectedTabText = tabbedPane.getTitleAt(selectedIndex);
			try {

				String response = in.readLine();
				if (response != null) { // Check if 'response' is not null
					if (response.equals("login successful.")) {
						if (selectedTabText.equals("Customer")) {
							CustomerHomePage customerHomePage = new CustomerHomePage(username);
							customerHomePage.setVisible(true);
							SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
						} else {
							if (username.equals("admin") && password.equals("admin")) {
								HomePage homePage = new HomePage(username);
								homePage.setVisible(true);
							} else {
								String branch = EmployeeAuthentication.getBranch(username);
								if (branch != null) {
									if (branch.equals("Holon")) {
										HolonPage holonPage = new HolonPage(username);
										holonPage.setVisible(true);
										SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
									} else if (branch.equals("Rishon LeTzion")) {
										RishonLeTzionPage rishonPage = new RishonLeTzionPage(username);
										rishonPage.setVisible(true);
										SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
									} else if (branch.equals("Tel Aviv")) {
										TelAvivPage telAvivPage = new TelAvivPage(username);
										telAvivPage.setVisible(true);
										SwingUtilities.getWindowAncestor((Component) e.getSource()).dispose();
									} else {
										JOptionPane.showMessageDialog(null, "Invalid user branch.");
									}
								} else {
									JOptionPane.showMessageDialog(null, "User branch not found.");
								}
							}
						}
					} else {
						// Handle unsuccessful login
						// ...
						// Close the current socket and reconnect
						closeSocket();
						reconnectToServer();
					}
				} else {
					// Handle the case where 'response' is null
					JOptionPane.showMessageDialog(mainFrame, "Server response is null");

				}
				JOptionPane.showMessageDialog(mainFrame, response);

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});

		registerButton.addActionListener(e -> {
			int selectedIndex = tabbedPane.getSelectedIndex();
			String selectedTabText = tabbedPane.getTitleAt(selectedIndex);
			if (selectedTabText.equals("Customer")) {
				openCustomerRegistrationWindow(tabbedPane);
			} else {
				openEmployeeRegistrationWindow();
			}
		});

		panel.setLayout(new FlowLayout());
		panel.add(new JLabel(userType + " Username:"));
		panel.add(usernameField);
		panel.add(new JLabel(userType + " Password:"));
		panel.add(passwordField);
		panel.add(loginButton);
		panel.add(registerButton);

		return panel;
	}
	
	private static void openCustomerRegistrationWindow(JTabbedPane tabbedPane) {
	    JFrame registerFrame = new JFrame("Customer Registration");
	    registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    registerFrame.setSize(400, 250); // Set an appropriate size
	    registerFrame.setLayout(new BorderLayout());

	    JPanel registerPanel = new JPanel();
	    registerPanel.setLayout(new GridLayout(6, 2));

	    JTextField usernameField = new JTextField(20);
	    JPasswordField passwordField = new JPasswordField(20);
	    JTextField nameField = new JTextField(20);
	    JTextField idField = new JTextField(20);
	    JTextField phoneField = new JTextField(20);

	    JButton registerButton = new JButton("Register");

	    registerButton.addActionListener(e -> {
	        String username = usernameField.getText();
	        String password = new String(passwordField.getPassword());
	        String name = nameField.getText();
	        String id = idField.getText();
	        String phone = phoneField.getText();

	        // Create a Customer instance
	        Customer customer = new Customer(username, password, name, id, phone);

	        // Save the customer to the list and file
	        customers.add(customer);
	        saveCustomersToFile();

	        // Display a success message
	        JOptionPane.showMessageDialog(registerFrame, "Customer registration successful");
	        registerFrame.dispose();
	    });

	    registerPanel.add(new JLabel("Customer Username:"));
	    registerPanel.add(usernameField);
	    registerPanel.add(new JLabel("Customer Password:"));
	    registerPanel.add(passwordField);
	    registerPanel.add(new JLabel("Name:"));
	    registerPanel.add(nameField);
	    registerPanel.add(new JLabel("ID:"));
	    registerPanel.add(idField);
	    registerPanel.add(new JLabel("Phone:"));
	    registerPanel.add(phoneField);

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    buttonPanel.add(registerButton);

	    registerFrame.add(registerPanel, BorderLayout.CENTER);
	    registerFrame.add(buttonPanel, BorderLayout.SOUTH);

	    // Center the frame on the screen
	    registerFrame.setLocationRelativeTo(null);

	    registerFrame.setVisible(true);
	}

	private static void openEmployeeRegistrationWindow() {
	    JFrame registerFrame = new JFrame("Employee Registration");
	    registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    registerFrame.setSize(400, 350); // Set an appropriate size
	    registerFrame.setLayout(new BorderLayout());

	    JPanel registerPanel = new JPanel();
	    registerPanel.setLayout(new GridLayout(8, 2));

	    JTextField usernameField = new JTextField(20);
	    JPasswordField passwordField = new JPasswordField(20);
	    JTextField fullNameField = new JTextField(20);
	    JTextField postalCodeField = new JTextField(20);
	    JTextField phoneNumberField = new JTextField(20);
	    JTextField accountNumberField = new JTextField(20);

	    String[] branches = { "Holon", "Tel Aviv", "Rishon LeTzion" };
	    JComboBox<String> branchAffiliationComboBox = new JComboBox<>(branches);

	    String[] positions = { "Seller", "Cashier", "Shift manager" };
	    JComboBox<String> positionComboBox = new JComboBox<>(positions);

	    JButton registerButton = new JButton("Register");

	    registerButton.addActionListener(e -> {
	        String username = usernameField.getText();
	        String password = new String(passwordField.getPassword());
	        String fullName = fullNameField.getText();
	        String postalCode = postalCodeField.getText();
	        String phoneNumber = phoneNumberField.getText();
	        String accountNumber = accountNumberField.getText();
	        String branchAffiliation = (String) branchAffiliationComboBox.getSelectedItem();
	        String position = (String) positionComboBox.getSelectedItem();

	        // Create an Employee instance
	        Employee employee = new Employee(username, password, fullName, postalCode, phoneNumber, accountNumber,
	                branchAffiliation, position);

	        // Save the employee to the list and file
	        employees.add(employee);
	        saveEmployeesToFile();

	        // Display a success message
	        JOptionPane.showMessageDialog(registerFrame, "Employee registration successful");
	        registerFrame.dispose();
	    });

	    registerPanel.add(new JLabel("Employee Username:"));
	    registerPanel.add(usernameField);
	    registerPanel.add(new JLabel("Employee Password:"));
	    registerPanel.add(passwordField);
	    registerPanel.add(new JLabel("Full Name:"));
	    registerPanel.add(fullNameField);
	    registerPanel.add(new JLabel("Postal Code:"));
	    registerPanel.add(postalCodeField);
	    registerPanel.add(new JLabel("Phone Number:"));
	    registerPanel.add(phoneNumberField);
	    registerPanel.add(new JLabel("Account Number:"));
	    registerPanel.add(accountNumberField);
	    registerPanel.add(new JLabel("Branch Affiliation:"));
	    registerPanel.add(branchAffiliationComboBox);
	    registerPanel.add(new JLabel("Position:"));
	    registerPanel.add(positionComboBox);

	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    buttonPanel.add(registerButton);

	    registerFrame.add(registerPanel, BorderLayout.CENTER);
	    registerFrame.add(buttonPanel, BorderLayout.SOUTH);

	    // Center the frame on the screen
	    registerFrame.setLocationRelativeTo(null);

	    registerFrame.setVisible(true);
	}


	private static void saveCustomersToFile() {
		try (PrintWriter writer = new PrintWriter(new FileWriter("customer.txt", true))) {
			for (Customer customer : customers) {
				writer.println(customer.toText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void saveEmployeesToFile() {
		try (PrintWriter writer = new PrintWriter(new FileWriter("employee.txt", true))) {
			for (Employee employee : employees) {
				writer.println(employee.toText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void start() {
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			SwingUtilities.invokeLater(() -> createGUI(tabbedPane));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void resetLoginPage() {
		// Close the current socket
		closeSocket();

		// Re-create and display the login page
		tabbedPane.removeAll();
		createGUI(tabbedPane);
	}

	private static void closeSocket() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void reconnectToServer() {
		closeSocket(); // Close the existing socket
		try {
			socket = new Socket(SERVER_IP, SERVER_PORT); // Create a new socket
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}