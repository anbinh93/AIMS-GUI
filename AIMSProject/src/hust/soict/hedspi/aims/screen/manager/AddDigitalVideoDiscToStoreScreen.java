package hust.soict.hedspi.aims.screen.manager;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import hust.soict.hedspi.aims.media.DigitalVideoDisc;
import hust.soict.hedspi.aims.store.Store;

public class AddDigitalVideoDiscToStoreScreen extends JFrame {
    private final Store store;
    private final JTextField titleField;
    private final JTextField categoryField;
    private final JTextField directorField;
    private final JTextField lengthField;
    private final JTextField costField;
    private final JButton btnSubmit;
    private final JButton btnBack;

    public AddDigitalVideoDiscToStoreScreen(Store store) {
        this.store = store;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(6, 2, 10, 10));

        cp.add(new JLabel("Enter title:"));
        titleField = new JTextField(20);
        cp.add(titleField);

        cp.add(new JLabel("Enter category:"));
        categoryField = new JTextField(20);
        cp.add(categoryField);

        cp.add(new JLabel("Enter director:"));
        directorField = new JTextField(20);
        cp.add(directorField);

        cp.add(new JLabel("Enter length (integer):"));
        lengthField = new JTextField(20);
        cp.add(lengthField);

        cp.add(new JLabel("Enter cost (float):"));
        costField = new JTextField(20);
        cp.add(costField);

        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            new StoreManagerScreen(store);
            dispose();
        });
        cp.add(btnBack);

        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(this::handleSubmit);
        cp.add(btnSubmit);

        setTitle("Add Digital Video Disc");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void handleSubmit(ActionEvent e) {
        try {
            String title = titleField.getText().trim();
            String category = categoryField.getText().trim();
            String director = directorField.getText().trim();
            int length = Integer.parseInt(lengthField.getText().trim());
            float cost = Float.parseFloat(costField.getText().trim());

            if (title.isEmpty() || category.isEmpty() || director.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            DigitalVideoDisc dvd = new DigitalVideoDisc(1, title, category, director, length, cost);
            store.addMedia(dvd);

            JOptionPane.showMessageDialog(this, "DVD added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new StoreManagerScreen(store);
            dispose();
        } catch (NumberFormatException ex) {
            showErrorDialog("Length must be an integer and Cost must be a valid float.");
        } catch (IllegalArgumentException ex) {
            showErrorDialog(ex.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
