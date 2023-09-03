package ClothesShopPackage;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private static List<Employee> employees;
    
    public  EmployeeManager() {
        employees = new ArrayList<>();
    }

    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void updateEmployee(Employee updatedEmployee) {
        // Implement logic to find and update the employee
    }

    public void deleteEmployee(String employeeNumber) {
        // Implement logic to find and delete the employee
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Employee getEmployeeByEmployeeNumber(String employeeNumber) {
        for (Employee employee : employees) {
            if (employee.getEmployeeNumber().equals(employeeNumber)) {
                return employee;
            }
        }
        return null; // Return null if no employee with the given employeeNumber is found
    }

    // Additional methods for data validation, saving/loading data, etc.
}
