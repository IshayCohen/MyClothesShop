package ClothesShopPackage;

public class Employee {
    private String userName;
	private String fullName;
    private String postalCode;
    private String phoneNumber;
    private String accountNumber;
    private String branchAffiliation;
    private String employeeNumber;
    private String position;
    private String password;
    
    
    
	public Employee(String userName,String fullName, String postalCode, String phoneNumber, String accountNumber,
			String branchAffiliation, String position, String password) {
		super();
		this.userName = userName;
		this.fullName = fullName;
		this.postalCode = postalCode;
		this.phoneNumber = phoneNumber;
		this.accountNumber = accountNumber;
		this.branchAffiliation = branchAffiliation;
		this.position = position;
		this.password = password ; 
	}
	public Employee(String fullName) {
		super();
		this.fullName = fullName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

    // Constructor, getters, setters, etc.
}
