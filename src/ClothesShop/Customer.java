package ClothesShop;

import java.io.Serializable;

public class Customer implements Serializable {
    private String username;
    private String password;
    private String name;
    private String id;
    private String phone;
    private static final long serialVersionUID = 1L;

    public Customer(String username, String password, String name, String id, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.id = id;
        this.phone = phone;
    }

    // Getters and setters for all fields
    
    // Convert a Customer object to a custom text format
    public String toText() {
        return username + ";" + password + ";" + name + ";" + id + ";" + phone;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// Create a Customer object from a custom text format
    public static Customer fromText(String text) {
        String[] parts = text.split(";");
        return new Customer(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    // ... (other methods)
}
