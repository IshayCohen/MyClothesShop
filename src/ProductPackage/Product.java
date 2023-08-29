package ProductPackage;

public class Product {
    private String type;
    private String name;
    private String size;
    private int quantity;
    private double price;
    private String branch;

    public Product(String type, String name, String size, int quantity, double price, String branch) {
        this.type = type;
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.branch = branch;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
    
    public String getBranch() {
        return branch;
    }
}
