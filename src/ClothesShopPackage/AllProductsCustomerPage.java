package ClothesShopPackage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AllProductsCustomerPage extends JFrame {
    private List<ProductPackage.Product> cart = new ArrayList<>();
    private String username;
    private JTable table;
    private JTextField searchField;
    private String[] items;

    public AllProductsCustomerPage(String username, CustomerHomePage customerHomePage) {
        this.username = username;

        // Read data from the "products.txt" file
        String fileName = "products.txt";
        String database = readFile(fileName);

        // Split the database into individual items
        items = database.split("\n");

        // Create a JTable to display the data
        String[] columnNames = {"Type", "Name", "Size", "Quantity", "Price", "Branch"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        // Create a TableRowSorter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        // Set the TableRowSorter to the table
        table.setRowSorter(sorter);

        // Optionally, set sorting options for specific columns
        sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(1, SortOrder.ASCENDING)));

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

        // Add a search field and button
        searchField = new JTextField(20);
        
     // Add a DocumentListener to the searchField
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterAndDisplayProducts(searchField.getText().toLowerCase());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterAndDisplayProducts(searchField.getText().toLowerCase());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // This method is not used for plain text components
            }
        });

        
        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        add(searchPanel, BorderLayout.NORTH);

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

                    int quantityToBuy = askForQuantity(availableQuantity);
                    if (quantityToBuy > 0) {
                        double totalPrice = price * quantityToBuy;
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
                showCartDialog();
            }
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addToCartButton);
        buttonPanel.add(showCartButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        setTitle("All Products");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

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

    private double parsePrice(String priceString) {
        return Double.parseDouble(priceString.substring(1));
    }

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
        return 0;
    }

    private void showCartDialog() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Cart is empty.");
        } else {
            dispose();
            JDialog cartDialog = new JDialog(this, "Cart Content", true);
            cartDialog.setLayout(new BorderLayout());

            String[] columnNames = {"Type", "Name", "Size", "Quantity", "Price", "Branch"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable cartTable = new JTable(model);

            for (ProductPackage.Product product : cart) {
                model.addRow(new Object[]{product.getType(), product.getName(), product.getSize(),
                        product.getQuantity(), "$" + product.getPrice(), product.getBranch()});
            }

            JScrollPane scrollPane = new JScrollPane(cartTable);
            cartDialog.add(scrollPane, BorderLayout.CENTER);

            JButton buyButton = new JButton("Buy");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    confirmPurchase(cart);
                }
            });

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cartDialog.dispose();
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(buyButton);
            buttonPanel.add(closeButton);
            cartDialog.add(buttonPanel, BorderLayout.SOUTH);

            cartDialog.setSize(800, 400);
            cartDialog.setLocationRelativeTo(this);
            cartDialog.setVisible(true);
        }
    }
    private void filterAndDisplayProducts(String query) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (String item : items) {
            String[] parts = item.split(":");
            String type = parts[0];
            String name = parts[1];
            String size = parts[2];
            String branch = parts[5];

            if (type.contains(query) || name.contains(query) || size.contains(query) || branch.contains(query)) {
                int quantity = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);

                model.addRow(new Object[]{type, name, size, quantity, "$" + price, branch});
            }
        }
    }


    private void confirmPurchase(List<ProductPackage.Product> productsToBuy) {
        double totalPrice = calculateTotalPrice(productsToBuy);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Buy all displayed products for $" + totalPrice + "?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            updateProductQuantities(productsToBuy);
            writeBoughtItemsToFile(username, productsToBuy);
            JOptionPane.showMessageDialog(this, "Products bought successfully!");
            productsToBuy.clear();
            refreshCartTable();
        }
    }

    private void writeBoughtItemsToFile(String customerName, List<ProductPackage.Product> productsToBuy) {
        String fileName = "AllBoughtItems.txt";
        try (FileWriter writer = new FileWriter(fileName, true)) {
            for (ProductPackage.Product product : productsToBuy) {
                writer.write(product.getType() + ":" + product.getName() + ":" + product.getSize()
                        + ":" + product.getQuantity() + ":$" + product.getPrice() + ":" + product.getBranch() + ":" + customerName + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshCartTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (ProductPackage.Product product : cart) {
            model.addRow(new Object[]{product.getType(), product.getName(), product.getSize(),
                    product.getQuantity(), "$" + product.getPrice(), product.getBranch()});
        }
    }

    private double calculateTotalPrice(List<ProductPackage.Product> products) {
        double totalPrice = 0.0;
        for (ProductPackage.Product product : products) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    private void updateProductQuantities(List<ProductPackage.Product> productsToBuy) {
        String fileName = "products.txt";
        String database = readFile(fileName);

        String[] items = database.split("\n");
        StringBuilder updatedContent = new StringBuilder();

        for (String item : items) {
            String[] parts = item.split(":");
            String type = parts[0];
            String name = parts[1];
            String size = parts[2];
            int quantity = Integer.parseInt(parts[3]);
            double price = Double.parseDouble(parts[4]);
            String branch = parts[5];

            for (ProductPackage.Product product : productsToBuy) {
                if (product.getType().equals(type) && product.getName().equals(name)
                        && product.getSize().equals(size) && product.getBranch().equals(branch)) {
                    quantity -= product.getQuantity();
                }
            }

            if (quantity < 0) {
                quantity = 0;
            }

            updatedContent.append(type).append(":").append(name).append(":").append(size)
                    .append(":").append(quantity).append(":").append(price).append(":").append(branch).append("\n");
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
