package ClothesShop;

import java.io.Serializable;

public class Employee implements Serializable {
    private String username;
    private String password;
    private String fullName;
    private String postalCode;
    private String phoneNumber;
    private String accountNumber;
    private String branchAffiliation;
    private String position;

    public Employee(String username, String password, String fullName, String postalCode, String phoneNumber,
                    String accountNumber, String branchAffiliation, String position) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.accountNumber = accountNumber;
        this.branchAffiliation = branchAffiliation;
        this.position = position;
    }

    // Getters and setters for all fields
    
    // Convert an Employee object to a custom text format
    public String toText() {
        return username + ";" + password + ";" + fullName + ";" + postalCode + ";" + phoneNumber + ";" + accountNumber + ";" + branchAffiliation + ";" + position;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranchAffiliation() {
		return branchAffiliation;
	}

	public void setBranchAffiliation(String branchAffiliation) {
		this.branchAffiliation = branchAffiliation;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	// Create an Employee object from a custom text format
    public static Employee fromText(String text) {
        String[] parts = text.split(";");
        return new Employee(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7]);
    }

    // ... (other methods)
}