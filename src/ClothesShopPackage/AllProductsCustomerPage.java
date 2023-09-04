package ClothesShopPackage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllProductsCustomerPage extends JFrame {
    private List<ProductPackage.Product> cart = new ArrayList<>();
    private String username;

    public AllProductsCustomerPage(String username) {
        this.username = username;
        // Read data from the "products.txt" file
        String fileName = "products.txt";
        String database = readFile(fileName);

        // Split the database into individual items
        String[] items = database.split("\n");

        // Create a JTable to display the data
        String[] columnNames = {"Type", "Name", "Size", "Quantity", "Price", "Branch"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        for (String item : items) {
            String[] parts = item.split(":");
            String type = parts[0];
            String name = parts[1];
            String size = parts[2];
            int quantity = Integer.parseInt(parts[3]);
            double price = Double.parseDouble(parts[4]);
            String branch = parts[5];

            model.addRow(new Object[]{type, name, size, quantity, "$" + price, branch});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Add the "Add to Cart" button
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String type = (String) table.getValueAt(selectedRow, 0);
                    String name = (String) table.getValueAt(selectedRow, 1);
                    String size = (String) table.getValueAt(selectedRow, 2);
                    int availableQuantity = (int) table.getValueAt(selectedRow, 3);
                    double price = parsePrice((String) table.getValueAt(selectedRow, 4));
                    String branch = (String) table.getValueAt(selectedRow, 5);

                    // Ask the customer for the quantity they want to buy
                    int quantityToBuy = askForQuantity(availableQuantity);
                    if (quantityToBuy > 0) {
                        // Calculate the total price
                        double totalPrice = price * quantityToBuy;

                        // Create a Product object and add it to the cart
                        ProductPackage.Product product = new ProductPackage.Product(type, name, size, quantityToBuy, totalPrice, branch);
                        cart.add(product);

                        JOptionPane.showMessageDialog(AllProductsCustomerPage.this, "Product added to cart");
                    }
                } else {
                    JOptionPane.showMessageDialog(AllProductsCustomerPage.this, "Please select a product to add to the cart");
                }
            }
        });

        // Add the "Show Cart" button
        JButton showCartButton = new JButton("Show Cart");
        showCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCartDialog(); // Display the cart content
            }
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addToCartButton);
        buttonPanel.add(showCartButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setTitle("All Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setSize(800, 400);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    // Function to read the contents of a file and return as a string using BufferedReader
    private static String readFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    // Helper function to parse the price from the table cell
    private double parsePrice(String priceString) {
        // Remove the "$" sign and parse as a double
        return Double.parseDouble(priceString.substring(1));
    }

    // Function to ask the customer for the quantity they want to buy
    private int askForQuantity(int availableQuantity) {
        String input = JOptionPane.showInputDialog(AllProductsCustomerPage.this,
                "Enter the quantity (up to " + availableQuantity + "):");
        try {
            int quantity = Integer.parseInt(input);
            if (quantity > 0 && quantity <= availableQuantity) {
                return quantity;
            } else {
                JOptionPane.showMessageDialog(AllProductsCustomerPage.this,
                        "Please enter a valid quantity (between 1 and " + availableQuantity + ")");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(AllProductsCustomerPage.this, "Please enter a valid number.");
        }
        return 0; // Return 0 if the input is invalid
    }

    // Function to display the cart content in a dialog
    private void showCartDialog() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
        } else {
            // Create a dialog to display the cart content
            JDialog cartDialog = new JDialog(this, "Cart Content", true);
            cartDialog.setLayout(new BorderLayout());

            // Create a table to display the cart content
            String[] columnNames = {"Type", "Name", "Size", "Quantity", "Price", "Branch"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable cartTable = new JTable(model);

            // Add the cart items to the table
            for (ProductPackage.Product product : cart) {
                model.addRow(new Object[]{product.getType(), product.getName(), product.getSize(),
                        product.getQuantity(), "$" + product.getPrice(), product.getBranch()});
            }

            JScrollPane scrollPane = new JScrollPane(cartTable);
            cartDialog.add(scrollPane, BorderLayout.CENTER);

            // Create a buy button for the dialog
            JButton buyButton = new JButton("Buy");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    confirmPurchase(cart); // Ask for confirmation to purchase all displayed products
                }
            });

            // Create a close button for the dialog
            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cartDialog.dispose(); // Close the dialog
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(buyButton);
            buttonPanel.add(closeButton);
            cartDialog.add(buttonPanel, BorderLayout.SOUTH);

            // Set dialog properties
            cartDialog.setSize(800, 400);
            cartDialog.setLocationRelativeTo(this); // Center the dialog on the parent frame
            cartDialog.setVisible(true);
        }
    }

    // Function to ask for confirmation to purchase all displayed products
    private void confirmPurchase(List<ProductPackage.Product> productsToBuy) {
        double totalPrice = calculateTotalPrice(productsToBuy);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Buy all displayed products for $" + totalPrice + "?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Perform the purchase action here (e.g., update inventory, process payment, etc.)
            updateProductQuantities(productsToBuy); // Update the quantities in products.txt
            JOptionPane.showMessageDialog(this, "Products bought successfully!");

            // Clear the cart after the purchase
            productsToBuy.clear();
        }
    }

    // Function to calculate the total price of products in the cart
    private double calculateTotalPrice(List<ProductPackage.Product> products) {
        double totalPrice = 0.0;
        for (ProductPackage.Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    // Function to update the quantities of products in the products.txt file
    private void updateProductQuantities(List<ProductPackage.Product> productsToBuy) {
        String fileName = "products.txt";
        String database = readFile(fileName);

        // Split the database into individual items
        String[] items = database.split("\n");

        // Create a new StringBuilder to store the updated content
        StringBuilder updatedContent = new StringBuilder();

        for (String item : items) {
            String[] parts = item.split(":");
            String type = parts[0];
            String name = parts[1];
            String size = parts[2];
            int quantity = Integer.parseInt(parts[3]);
            double price = Double.parseDouble(parts[4]);
            String branch = parts[5];

            // Check if this product is in the cart
            for (ProductPackage.Product product : productsToBuy) {
                if (product.getType().equals(type) && product.getName().equals(name)
                        && product.getSize().equals(size) && product.getBranch().equals(branch)) {
                    // Reduce the quantity based on what was bought
                    quantity -= product.getQuantity();
                }
            }

            // Ensure quantity does not go negative
            if (quantity < 0) {
                quantity = 0;
            }

            // Append the updated product information to the StringBuilder
            updatedContent.append(type).append(":").append(name).append(":").append(size)
                    .append(":").append(quantity).append(":").append(price).append(":").append(branch).append("\n");
        }

        // Write the updated content back to the file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
