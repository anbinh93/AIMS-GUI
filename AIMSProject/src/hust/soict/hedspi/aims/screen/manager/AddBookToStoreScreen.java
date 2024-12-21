package hust.soict.hedspi.aims.screen.manager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import hust.soict.hedspi.aims.media.Book;
import hust.soict.hedspi.aims.store.Store;

public class AddBookToStoreScreen extends JFrame {
    private final Store store;
    private final JTextField titleField;
    private final JTextField categoryField;
    private final JTextField authorsField;
    private final JTextField costField;
    private final JButton btnSubmit;
    private final JButton btnBack;

    public AddBookToStoreScreen(Store store) {
        this.store = store;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(6, 2, 10, 10));

        // Title
        cp.add(new JLabel("Enter title:"));
        titleField = new JTextField(20);
        cp.add(titleField);

        // Authors
        cp.add(new JLabel("Enter authors (separated by space):"));
        authorsField = new JTextField(20);
        cp.add(authorsField);

        // Category
        cp.add(new JLabel("Enter category:"));
        categoryField = new JTextField(20);
        cp.add(categoryField);

        // Cost
        cp.add(new JLabel("Enter cost (float):"));
        costField = new JTextField(20);
        cp.add(costField);

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
        setTitle("Add Book to Store");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void handleSubmit(ActionEvent e) {
        try {
            // Validate and parse input fields
            String title = titleField.getText().trim();
            String category = categoryField.getText().trim();
            String authorsText = authorsField.getText().trim();
            float cost = Float.parseFloat(costField.getText().trim());

            if (title.isEmpty() || category.isEmpty() || authorsText.isEmpty()) {
                throw new IllegalArgumentException("All fields must be filled.");
            }

            // Create authors list
            List<String> authors = new ArrayList<>();
            for (String author : authorsText.split(" ")) {
                if (!author.trim().isEmpty()) {
                    authors.add(author.trim());
                }
            }

            // Create book object and add to store
            Book book = new Book(1, title, category, cost);
            for (String author : authors) {
                book.addAuthor(author);
            }
            store.addMedia(book);

            JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new StoreManagerScreen(store);
            dispose();

        } catch (NumberFormatException ex) {
            showErrorDialog("Cost must be a valid float value.");
        } catch (IllegalArgumentException ex) {
            showErrorDialog(ex.getMessage());
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
