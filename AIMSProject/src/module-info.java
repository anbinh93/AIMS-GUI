module AIMSProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens hust.soict.hedspi.aims.screen.customer.controller to javafx.fxml;
    exports hust.soict.hedspi.aims;
}