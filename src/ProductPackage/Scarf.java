package ProductPackage;

public class Scarf extends Product {
    private String material;

    public Scarf(String name, String size, int quantity, double price, String branch, String material) {
        super("Scarf", name, size, quantity, price, branch);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }
}
