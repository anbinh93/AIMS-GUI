package hust.soict.hedspi.aims.screen.customer.controller;

import java.util.Optional;
import javax.naming.LimitExceededException;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.except.MediaPlayerException;
import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.media.Playable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ItemController {
    private Media media;
    private Cart cart;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblTitle;

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlay;

    // Initialize data
    public void setData(Media media, Cart cart) {
        this.media = media;
        this.cart = cart;
        lblTitle.setText(media.getTitle());
        lblCost.setText(String.format("%.2f $", media.getCost()));
        setupButtons();
    }

    private void setupButtons() {
        if (media instanceof Playable) {
            btnPlay.setVisible(true);
        } else {
            btnPlay.setVisible(false);
            HBox.setMargin(btnAddToCart, new Insets(0, 0, 0, 60));
        }
    }

    @FXML
    private void btnAddToCartClicked(ActionEvent event) {
        showConfirmationAlert("Add media", "Do you want to add this media to cart?", media.toString(), () -> {
            try {
                cart.addMedia(media);
                showInfoAlert("Add media", "Status: ", "Success");
            } catch (LimitExceededException | IllegalArgumentException e) {
                showErrorAlert("Error", e.getMessage());
            }
        });
    }

    @FXML
    private void btnPlayClicked(ActionEvent event) {
        try {
            if (media instanceof Playable) {
                Playable playable = (Playable) media;
                showInfoAlert("Play media", "Playing " + media.getTitle(), playable.playMedia());
            } else {
                throw new ClassCastException("Selected media is not playable!");
            }
        } catch (MediaPlayerException | ClassCastException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    // Utility methods for alerts
    private void showConfirmationAlert(String title, String header, String content, Runnable onConfirm) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            onConfirm.run();
        }
    }

    private void showInfoAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
