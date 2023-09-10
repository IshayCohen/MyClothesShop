package ProductPackage;

import java.io.Serializable;

public class Jacket extends Product {
    private String material;

    public Jacket(String name, String size, int quantity, double price, String branch, String material) {
        super("Jacket", name, size, quantity, price, branch);
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }
}
