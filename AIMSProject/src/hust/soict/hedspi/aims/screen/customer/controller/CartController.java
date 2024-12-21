package hust.soict.hedspi.aims.screen.customer.controller;

import java.io.IOException;
import java.util.Optional;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.except.MediaPlayerException;
import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.media.Playable;
import hust.soict.hedspi.aims.store.Store;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CartController {
    private final Cart cart;
    private final Store store;

    @FXML
    private TableView<Media> tblMedia;

    @FXML
    private TableColumn<Media, String> colMediaTitle;

    @FXML
    private Label costLabel;

    @FXML
    private TableColumn<Media, Integer> colMediaId;

    @FXML
    private TableColumn<Media, String> colMediaCategory;

    @FXML
    private TableColumn<Media, Float> colMediaCost;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private RadioButton radioBtnFilterId;

    @FXML
    private RadioButton radioBtnFilterTitle;

    @FXML
    private TextField tfFilter;

    @FXML
    private ToggleGroup filterCategory;

    public CartController(Store store, Cart cart) {
        this.cart = cart;
        this.store = store;
    }

    @FXML
    private void initialize() {
        setupTableColumns();
        setupTableData();
        setupEventListeners();
        updateCostLabel();
        updateBtnPlaceOrder();
    }

    private void setupTableColumns() {
        colMediaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMediaTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMediaCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colMediaCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void setupTableData() {
        tblMedia.setItems(cart.getItemsOrdered());
        if (!cart.getItemsOrdered().isEmpty()) {
            tblMedia.getSelectionModel().select(cart.getItemsOrdered().get(0));
            updateButtonBar(cart.getItemsOrdered().get(0));
        } else {
            updateButtonBar(null);
        }
    }

    private void setupEventListeners() {
        tblMedia.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateButtonBar(newValue));

        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> filterMedia(newValue));

        filterCategory.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> filterMedia(tfFilter.getText()));
    }

    private void updateBtnPlaceOrder() {
        btnPlaceOrder.setDisable(cart.getItemsOrdered().isEmpty());
    }

    private void filterMedia(String filter) {
        FilteredList<Media> filteredData = new FilteredList<>(cart.getItemsOrdered(), media -> {
            if (filter == null || filter.isEmpty()) return true;

            String lowerCaseFilter = filter.toLowerCase().trim();

            if (filterCategory.getSelectedToggle() == radioBtnFilterTitle) {
                return media.getTitle().toLowerCase().contains(lowerCaseFilter);
            } else {
                return String.valueOf(media.getId()).contains(lowerCaseFilter);
            }
        });

        SortedList<Media> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblMedia.comparatorProperty());
        tblMedia.setItems(sortedData);
    }

    private void updateCostLabel() {
        float cost = (float) cart.getItemsOrdered().stream().mapToDouble(Media::getCost).sum();
        costLabel.setText(String.format("%.2f$", cost));
    }

    private void updateButtonBar(Media media) {
        boolean isMediaSelected = media != null;
        btnRemove.setVisible(isMediaSelected);
        btnPlay.setVisible(isMediaSelected && media instanceof Playable);
    }

    @FXML
    private void btnPlayPressed(ActionEvent event) {
        try {
            Media media = tblMedia.getSelectionModel().getSelectedItem();

            if (!(media instanceof Playable)) {
                throw new ClassCastException("Selected media is not playable!");
            }

            Playable playable = (Playable) media;
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Play Media");
            alert.setHeaderText("Playing " + media.getTitle());
            alert.setContentText(playable.playMedia());
            alert.showAndWait();

        } catch (MediaPlayerException | ClassCastException e) {
            showErrorAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void btnRemovePressed(ActionEvent event) {
        Media media = tblMedia.getSelectionModel().getSelectedItem();
        if (media != null) {
            cart.removeMedia(media);
            updateCostLabel();
            updateButtonBar(null);
        }
    }

    @FXML
    private void btnViewStorePressed(ActionEvent event) {
        try {
            final String STORE_FXML_FILE_PATH = "/hust/soict/hedspi/aims/screen/customer/view/Store.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(STORE_FXML_FILE_PATH));
            loader.setController(new ViewStoreController(store, cart));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Store");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnPlaceOrderPressed(ActionEvent event) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Place Order");
        confirmationAlert.setHeaderText("Do you want to place the order?");
        confirmationAlert.setContentText(cart.printCart());

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            cart.emptyCart();
            showInfoAlert("Order Status", "Order placed successfully!");
            updateCostLabel();
            updateBtnPlaceOrder();
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
