package hust.soict.hedspi.aims.screen.manager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import hust.soict.hedspi.aims.media.CompactDisc;
import hust.soict.hedspi.aims.store.Store;

public class AddCompactDiscToStoreScreen extends JFrame {
    private final Store store;
    private final JTextField titleField;
    private final JTextField categoryField;
    private final JTextField directorField;
    private final JTextField lengthField;
    private final JTextField costField;
    private final JTextField artistField;
    private final JButton btnSubmit;
    private final JButton btnBack;

    public AddCompactDiscToStoreScreen(Store store) {
        this.store = store;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(7, 2, 10, 10));

        // Title
        cp.add(new JLabel("Enter title:"));
        titleField = new JTextField(20);
        cp.add(titleField);

        // Category
        cp.add(new JLabel("Enter category:"));
        categoryField = new JTextField(20);
        cp.add(categoryField);

        // Director
        cp.add(new JLabel("Enter director:"));
        directorField = new JTextField(20);
        cp.add(directorField);

        // Length
        cp.add(new JLabel("Enter length (integer):"));
        lengthField = new JTextField(20);
        cp.add(lengthField);

        // Cost
        cp.add(new JLabel("Enter cost (float):"));
        costField = new JTextField(20);
        cp.add(costField);

        // Artist
        cp.add(new JLabel("Enter artist:"));
        artistField = new JTextField(20);
        cp.add(artistField);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            new StoreManagerScreen(store);
            dispose();
        });
        cp.add(btnBack);

        // Submit Button
        btnSubmit = new JButton("Submit");
        btnSubmit.addActionListener(this::handleSubmit);
        cp.add(btnSubmit);

        // Frame Settings
        setTitle("Add Compact Disc");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void handleSubmit(ActionEvent e) {
        try {
            // Parse and validate inputs
            String title = titleField.getText().trim();
            String category = categoryField.getText().trim();
            String director = directorField.getText().trim();
            String artist = artistField.getText().trim();
            int length = Integer.parseInt(lengthField.getText().trim());
            float cost = Float.parseFloat(costField.getText().trim());

            if (title.isEmpty() || category.isEmpty() || director.isEmpty() || artist.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            // Create CompactDisc object and add to store
            CompactDisc cd = new CompactDisc(1, title, artist, category, director, cost);
            cd.setLength(length);
            store.addMedia(cd);

            JOptionPane.showMessageDialog(this, "Compact Disc added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
