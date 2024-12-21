package hust.soict.hedspi.aims.screen.customer.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import hust.soict.hedspi.aims.cart.Cart;
import hust.soict.hedspi.aims.media.Media;
import hust.soict.hedspi.aims.media.Playable;
import hust.soict.hedspi.aims.store.Store;

public class CartController {
    private static final String DEFAULT_COST_TEXT_FORMAT = "%.2f$";

    private Cart cart;
    private Store store;

    @FXML
    private TableView<Media> tblMedia;
    @FXML
    private Label costLabel;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnPlaceOrder;
    @FXML
    private TextField tfFilter;
    @FXML
    private ToggleGroup filterCategory;
    @FXML
    private RadioButton radioBtnFilterId;
    @FXML
    private RadioButton radioBtnFilterTitle;
    @FXML
    private TableColumn<Media, Integer> colMediaId;
    @FXML
    private TableColumn<Media, String> colMediaTitle;
    @FXML
    private TableColumn<Media, String> colMediaCategory;
    @FXML
    private TableColumn<Media, Float> colMediaCost;

    public CartController(Store store, Cart cart) {
        this.cart = cart;
        this.store = store;
    }

    @FXML
    private void initialize() {
        setupTableColumns();
        setupCartDisplay();
        setupListeners();
    }

    private void setupTableColumns() {
        colMediaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMediaTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colMediaCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colMediaCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
    }

    private void setupCartDisplay() {
        tblMedia.setItems(cart.getItemsOrdered());
        if (!isCartEmpty()) {
            tblMedia.getSelectionModel().select(cart.getItemsOrdered().get(0));
            updateButtonBar(cart.getItemsOrdered().get(0));
        } else {
            updateButtonBar(null);
        }
        updateCostLabel();
        updatePlaceOrderButton();
    }

    private void setupListeners() {
        tblMedia.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateButtonBar(newValue));
        tfFilter.textProperty().addListener((observable, oldValue, newValue) -> showFilteredMedia(newValue));
        filterCategory.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> showFilteredMedia(tfFilter.getText()));
    }

    private void updatePlaceOrderButton() {
        btnPlaceOrder.setDisable(isCartEmpty());
    }

    private void updateCostLabel() {
        costLabel.setText(String.format(DEFAULT_COST_TEXT_FORMAT, cart.getItemsOrdered().stream().mapToDouble(Media::getCost).sum()));
    }

    private void updateButtonBar(Media media) {
        btnRemove.setVisible(media != null);
        btnPlay.setVisible(media instanceof Playable);
    }

    private void showFilteredMedia(String filterText) {
        FilteredList<Media> filteredData = new FilteredList<>(cart.getItemsOrdered());
        filteredData.setPredicate(media -> toggleSelectedFilter(media, filterText.toLowerCase()));
        SortedList<Media> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblMedia.comparatorProperty());
        tblMedia.setItems(sortedData);
    }

    private boolean toggleSelectedFilter(Media media, String filterText) {
        if (filterText == null || filterText.isEmpty()) return true;
        if (filterCategory.getSelectedToggle().equals(radioBtnFilterTitle)) {
            return media.getTitle().toLowerCase().contains(filterText.trim());
        } else {
            return String.valueOf(media.getId()).contains(filterText.trim());
        }
    }

    private boolean isCartEmpty() {
        return cart.getItemsOrdered().isEmpty();
    }
}