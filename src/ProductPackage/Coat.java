package ProductPackage;

public class Coat extends Product {
    private String material;
  

    public Coat(String name, String size, int quantity, double price, String branch, String material) {
        super("Coat", name, size, quantity, price, branch);
        this.material = material;
      
    }

    public String getMaterial() {
        return material;
    }

   
}
