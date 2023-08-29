package ProductPackage;

public class Pants extends Product {
    private String material;

    public Pants(String name, String size, int quantity, double price, String branch, String material) {
        super("Pants", name, size, quantity, price, branch);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }
}
