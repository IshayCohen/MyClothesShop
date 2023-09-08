package ClothesShopPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class UserManagementPage extends JFrame {
	private DefaultTableModel customerTableModel;
	private DefaultTableModel employeeTableModel;

	public UserManagementPage() {
		setTitle("User Management Page");
		setSize(800, 400);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// Create table models for customers and employees
		customerTableModel = new DefaultTableModel();
		employeeTableModel = new DefaultTableModel();

		// Create customer and employee tables
		JTable customerTable = new JTable(customerTableModel);
		JTable employeeTable = new JTable(employeeTableModel);

		// Add columns to customer table
		customerTableModel.addColumn("Username");
		customerTableModel.addColumn("Name");
		customerTableModel.addColumn("ID");
		customerTableModel.addColumn("Phone");

		// Add columns to employee table
		employeeTableModel.addColumn("Username");
		employeeTableModel.addColumn("Password");
		employeeTableModel.addColumn("Full Name");
		employeeTableModel.addColumn("Postal Code");
		employeeTableModel.addColumn("Phone");
		employeeTableModel.addColumn("Account Number");
		employeeTableModel.addColumn("Branch Affiliation");
		employeeTableModel.addColumn("Position");

		// Load customer data from customer.txt
		loadCustomersFromTextFile("customer.txt", customerTableModel);

		// Load employee data from employee.txt
		loadEmployeesFromTextFile("employee.txt", employeeTableModel);

		// Create a tabbed pane to display customer and employee tables
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Customers", new JScrollPane(customerTable));
		tabbedPane.addTab("Employees", createEmployeePanel(employeeTable));

		// Add the tabbed pane to the frame
		add(tabbedPane);
	}

	private JPanel createEmployeePanel(JTable employeeTable) {
		JPanel panel = new JPanel(new BorderLayout());

		// Add the employee table to the panel
		panel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);

		// Create buttons for adding, updating, and deleting employees
		JButton addEmployeeButton = new JButton("Add Employee");
		JButton updateEmployeeButton = new JButton("Update Employee");
		JButton deleteEmployeeButton = new JButton("Delete Employee");

		// Add action listeners for the buttons
		addEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openAddEmployeeDialog();
			}
		});

		updateEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateEmployee(employeeTable);
			}
		});

		deleteEmployeeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteEmployee(employeeTable);
			}
		});

		// Create a panel for the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(addEmployeeButton);
		buttonPanel.add(updateEmployeeButton);
		buttonPanel.add(deleteEmployeeButton);

		// Add the button panel to the main panel
		panel.add(buttonPanel, BorderLayout.SOUTH);

		return panel;
	}

	private void openAddEmployeeDialog() {
		// Create a dialog to input employee information
		JDialog addEmployeeDialog = new JDialog(this, "Add Employee", true);
		addEmployeeDialog.setSize(300, 250);
		addEmployeeDialog.setLayout(new GridLayout(9, 2));

		// Create input fields for employee information
		JTextField usernameField = new JTextField();
		JTextField fullNameField = new JTextField();
		JTextField postalCodeField = new JTextField();
		JTextField phoneNumberField = new JTextField();
		JTextField accountNumberField = new JTextField();
		JTextField branchAffiliationField = new JTextField();
		JTextField employeeNumberField = new JTextField();
		JTextField employeePasswordField = new JTextField();

		// Create a combo box for selecting the employee role
		String[] roles = { "Shift Manager", "Cashier", "Seller" };
		String[] branches = { "Holon", "Tel Aviv", "Rishon LeTzion" };
		JComboBox<String> roleComboBox = new JComboBox<>(roles);
		JComboBox<String> branchComboBox = new JComboBox<>(branches);

		JButton addButton = new JButton("Add");
		addEmployeeDialog.add(new JLabel("Username:"));
		addEmployeeDialog.add(usernameField);

		addEmployeeDialog.add(new JLabel("Password:"));
		addEmployeeDialog.add(employeePasswordField);

		addEmployeeDialog.add(new JLabel("Full Name:"));
		addEmployeeDialog.add(fullNameField);

		addEmployeeDialog.add(new JLabel("Postal Code:"));
		addEmployeeDialog.add(postalCodeField);

		addEmployeeDialog.add(new JLabel("Phone Number:"));
		addEmployeeDialog.add(phoneNumberField);

		addEmployeeDialog.add(new JLabel("Account Number:"));
		addEmployeeDialog.add(accountNumberField);

		addEmployeeDialog.add(new JLabel("Branch Affiliation:"));
		addEmployeeDialog.add(branchComboBox);

		addEmployeeDialog.add(new JLabel("Employee Role:"));
		addEmployeeDialog.add(roleComboBox);

		addEmployeeDialog.add(addButton);

		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Retrieve the entered employee information
				String userName = usernameField.getText();
				String fullName = fullNameField.getText();
				String postalCode = postalCodeField.getText();
				String phoneNumber = phoneNumberField.getText();
				String accountNumber = accountNumberField.getText();
				String branchAffiliation = (String) branchComboBox.getSelectedItem();
				String selectedRole = (String) roleComboBox.getSelectedItem();
				String password = employeePasswordField.getText();

				// Create an Employee object with the entered information
				Employee newEmployee = new Employee(userName, fullName, postalCode, phoneNumber, accountNumber,
						branchAffiliation, selectedRole, password);

				// Add the employee to the table
				addEmployeeToTable(newEmployee);

				// Add the employee to the employee.txt file
				writeEmployeeToTextFile(newEmployee);

				// Close the dialog
				addEmployeeDialog.dispose();
			}
		});

		addEmployeeDialog.setVisible(true);
	}

	private void addEmployeeToTable(Employee employee) {
		Vector<String> row = new Vector<>();
		row.add(employee.getUsername());
		row.add(employee.getPassword());
		row.add(employee.getFullName());
		row.add(employee.getPostalCode());
		row.add(employee.getPhoneNumber());
		row.add(employee.getAccountNumber());
		row.add(employee.getBranchAffiliation());
		row.add(employee.getPosition());
		employeeTableModel.addRow(row);
	}

	private void writeEmployeeToTextFile(Employee employee) {
		try (FileWriter writer = new FileWriter("employee.txt", true)) {
			String employeeData = employee.getUsername() + ";" + employee.getPassword() + ";" + employee.getFullName()
					+ ";" + employee.getPostalCode() + ";" + employee.getPhoneNumber() + ";"
					+ employee.getAccountNumber() + ";" + employee.getBranchAffiliation() + ";"
					+ employee.getPosition();
			writer.write(employeeData + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateEmployee(JTable employeeTable) {
		int selectedRow = employeeTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an employee to update.");
			return;
		}

		String[] roles = { "Shift Manager", "Cashier", "Seller" };
		String[] branches = { "Holon", "Tel Aviv", "Rishon LeTzion" };
		JComboBox<String> roleComboBox = new JComboBox<>(roles);
		JComboBox<String> branchComboBox = new JComboBox<>(branches);
		// Get the selected employee's data from the table
		String userName = (String) employeeTableModel.getValueAt(selectedRow, 0);
		String password = (String) employeeTableModel.getValueAt(selectedRow, 1);
		String fullName = (String) employeeTableModel.getValueAt(selectedRow, 2);
		String postalCode = (String) employeeTableModel.getValueAt(selectedRow, 3);
		String phoneNumber = (String) employeeTableModel.getValueAt(selectedRow, 4);
		String accountNumber = (String) employeeTableModel.getValueAt(selectedRow, 5);
		String branchAffiliation = (String) employeeTableModel.getValueAt(selectedRow, 6);
		String position = (String) employeeTableModel.getValueAt(selectedRow, 7);

		// Create a dialog for updating the selected employee's data
		JDialog updateEmployeeDialog = new JDialog(this, "Update Employee", true);
		updateEmployeeDialog.setSize(300, 250);
		updateEmployeeDialog.setLayout(new GridLayout(9, 2));

		JTextField updatedUsernameField = new JTextField(userName);
		JTextField updatedPasswordField = new JTextField(password);
		JTextField updatedFullNameField = new JTextField(fullName);
		JTextField updatedPostalCodeField = new JTextField(postalCode);
		JTextField updatedPhoneNumberField = new JTextField(phoneNumber);
		JTextField updatedAccountNumberField = new JTextField(accountNumber);
		JTextField updatedBranchAffiliationField = new JTextField(branchAffiliation);
		JTextField updatedPositionField = new JTextField(position);

		JButton applyButton = new JButton("Apply");

		updateEmployeeDialog.add(new JLabel("Username:"));
		updateEmployeeDialog.add(updatedUsernameField);
		updateEmployeeDialog.add(new JLabel("Password:"));
		updateEmployeeDialog.add(updatedPasswordField);
		updateEmployeeDialog.add(new JLabel("Full Name:"));
		updateEmployeeDialog.add(updatedFullNameField);
		updateEmployeeDialog.add(new JLabel("Postal Code:"));
		updateEmployeeDialog.add(updatedPostalCodeField);
		updateEmployeeDialog.add(new JLabel("Phone Number:"));
		updateEmployeeDialog.add(updatedPhoneNumberField);
		updateEmployeeDialog.add(new JLabel("Account Number:"));
		updateEmployeeDialog.add(updatedAccountNumberField);
		updateEmployeeDialog.add(new JLabel("Branch Affiliation:"));
		updateEmployeeDialog.add(branchComboBox);
		updateEmployeeDialog.add(new JLabel("Position:"));
		updateEmployeeDialog.add(roleComboBox);

		updateEmployeeDialog.add(applyButton);

		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the updated data from the dialog
				String updatedUsername = updatedUsernameField.getText();
				String updatedFullName = updatedFullNameField.getText();
				String updatedPostalCode = updatedPostalCodeField.getText();
				String updatedPhoneNumber = updatedPhoneNumberField.getText();
				String updatedAccountNumber = updatedAccountNumberField.getText();
				String updatedBranchAffiliation = (String) branchComboBox.getSelectedItem();
				String updatedPosition = (String) roleComboBox.getSelectedItem();
				String updatedPassword = updatedPasswordField.getText();

				// Update the employee data in the table
				employeeTableModel.setValueAt(updatedUsername, selectedRow, 0);
				employeeTableModel.setValueAt(updatedPassword, selectedRow, 1);
				employeeTableModel.setValueAt(updatedFullName, selectedRow, 2);
				employeeTableModel.setValueAt(updatedPostalCode, selectedRow, 3);
				employeeTableModel.setValueAt(updatedPhoneNumber, selectedRow, 4);
				employeeTableModel.setValueAt(updatedAccountNumber, selectedRow, 5);
				employeeTableModel.setValueAt(updatedBranchAffiliation, selectedRow, 6);
				employeeTableModel.setValueAt(updatedPosition, selectedRow, 7);

				// Update the employee data in the employee.txt file
				updateEmployeeInTextFile(selectedRow, updatedUsername, updatedFullName, updatedPostalCode,
						updatedPhoneNumber, updatedAccountNumber, updatedBranchAffiliation, updatedPosition,
						updatedPassword);

				// Close the dialog
				updateEmployeeDialog.dispose();

				JOptionPane.showMessageDialog(UserManagementPage.this, "Employee updated successfully.");
			}
		});

		updateEmployeeDialog.setVisible(true);
	}

	private void updateEmployeeInTextFile(int selectedRow, String updatedUsername, String updatedFullName,
			String updatedPostalCode, String updatedPhoneNumber, String updatedAccountNumber,
			String updatedBranchAffiliation, String updatedPosition, String updatedPassword) {
		try {
			// Read all lines from the employee.txt file
			BufferedReader reader = new BufferedReader(new FileReader("employee.txt"));
			String line;
			StringBuilder updatedFileContent = new StringBuilder();
			int currentRow = 0;

			while ((line = reader.readLine()) != null) {
				if (currentRow == selectedRow) {
					// Update the selected employee's data
					line = updatedUsername + ";" + updatedPassword + ";" + updatedFullName + ";" + updatedPostalCode
							+ ";" + updatedPhoneNumber + ";" + updatedAccountNumber + ";" + updatedBranchAffiliation
							+ ";" + updatedPosition;
				}
				updatedFileContent.append(line).append("\n");
				currentRow++;
			}
			reader.close();

			// Write the updated content back to the file
			FileWriter writer = new FileWriter("employee.txt");
			writer.write(updatedFileContent.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error updating employee in employee.txt.");
		}
	}

	private void deleteEmployee(JTable employeeTable) {
		int selectedRow = employeeTable.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// Confirm the deletion with a dialog
			int confirmResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?",
					"Confirm Deletion", JOptionPane.YES_NO_OPTION);

			if (confirmResult == JOptionPane.YES_OPTION) {
				// Remove the selected row from the table
				employeeTableModel.removeRow(selectedRow);

				// Remove the selected employee from the employee.txt file
				removeEmployeeFromTextFile(selectedRow);

				JOptionPane.showMessageDialog(this, "Employee deleted successfully.");
			}
		}
	}

	private void removeEmployeeFromTextFile(int selectedRow) {
		try {
			// Read all lines from the employee.txt file
			BufferedReader reader = new BufferedReader(new FileReader("employee.txt"));
			String line;
			StringBuilder updatedFileContent = new StringBuilder();
			int currentRow = 0;

			while ((line = reader.readLine()) != null) {
				if (currentRow != selectedRow) {
					// Exclude the selected employee's data
					updatedFileContent.append(line).append("\n");
				}
				currentRow++;
			}
			reader.close();

			// Write the updated content back to the file
			FileWriter writer = new FileWriter("employee.txt");
			writer.write(updatedFileContent.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error removing employee from employee.txt.");
		}
	}

	private void loadEmployeesFromTextFile(String fileName, DefaultTableModel tableModel) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] userData = line.split(";");
				if (!userData[0].equals("admin")) {
					tableModel.addRow(userData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadCustomersFromTextFile(String fileName, DefaultTableModel tableModel) {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] userData = line.split(";");
				if (userData.length == 5) {
					tableModel.addRow(userData);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			UserManagementPage userManagementPage = new UserManagementPage();
			userManagementPage.setVisible(true);
		});
	}
}
