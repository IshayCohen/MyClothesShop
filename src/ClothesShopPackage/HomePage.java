package ClothesShopPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    public HomePage(String username) {
        setTitle("Home Page - Welcome, " + username + "!");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        JButton telAvivButton = new JButton("Tel Aviv");
        JButton rishonButton = new JButton("Rishon LeTzion");
        JButton holonButton = new JButton("Holon");

        add(telAvivButton);
        add(rishonButton);
        add(holonButton);

        telAvivButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelAvivPage telAvivPage = new TelAvivPage("admin");
                telAvivPage.setVisible(true);
            }
        });

        rishonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RishonLeTzionPage rishonPage = new RishonLeTzionPage("admin");
                rishonPage.setVisible(true);
            }
        });

        holonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HolonPage holonPage = new HolonPage("admin");
                holonPage.setVisible(true);
            }
        });
    }
}
