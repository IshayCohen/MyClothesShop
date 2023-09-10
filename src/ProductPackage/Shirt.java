package ProductPackage;

import java.io.Serializable;

public class Shirt extends Product {
    private String material;

    public Shirt(String name, String size, int quantity, double price, String branch, String material) {
        super("Shirt", name, size, quantity, price, branch);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }
}
