package ProductPackage;

public class SwimmingSuit extends Product {
	private String style;

    public SwimmingSuit(String name, String size, int quantity, double price, String branch, String style) {
        super("SwimmingSuit", name, size, quantity, price, branch);
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

}
