package hust.soict.hedspi.aims.screen.customer.controller;

import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.media.Playable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

public class ItemController {

    private Media media;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblTitle;

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlay;



    public void setData(Media media) {
        this.media = media;
        lblTitle.setText(media.getTitle());
        lblCost.setText(String.format("%.2f $", media.getCost()));
        btnPlay.setVisible(media instanceof Playable);

        if (!(media instanceof Playable)) {
            HBox.setMargin(btnAddToCart, new Insets(0, 0, 0, 60));
        }
    }

    @FXML
    private void btnAddToCartClicked(ActionEvent event) {
        System.out.println("Add to Cart button clicked for: " + media.getTitle());
    }

    @FXML
    private void btnPlayClicked(ActionEvent event) {
        if (media instanceof Playable) {
            System.out.println("Playing: " + media.getTitle());
            ((Playable) media).play();
        }
    }
}
