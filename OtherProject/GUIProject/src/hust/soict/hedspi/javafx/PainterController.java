package hust.soict.hedspi.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PainterController {
    @FXML
    private RadioButton ButtonPressed1;

    @FXML
    private RadioButton ButtonPressed2;

    @FXML
    private Pane drawingAreaPane;

    @FXML
    void Selected(ActionEvent event) {
        ToggleGroup question= new ToggleGroup();
        ButtonPressed1.setToggleGroup(question);
        ButtonPressed2.setToggleGroup(question);
        if(ButtonPressed1.isSelected()) {
            ButtonPressed2.setSelected(false);
        }
        else {
            ButtonPressed1.setSelected(false);
        }
    }

    @FXML
    void ClearButtonPressed(ActionEvent event) {
        drawingAreaPane.getChildren().clear();
    }

    @FXML
    void drawingAreaMouseDragged(MouseEvent event) {
        if(ButtonPressed1.isSelected()) {
            Circle newCircle = new Circle(event.getX(), event.getY(), 4, Color.BLACK);
            drawingAreaPane.getChildren().add(newCircle);
        }
        else {
            Circle newCircle = new Circle(event.getX(), event.getY(), 4, Color.WHITE);
            drawingAreaPane.getChildren().add(newCircle);
        }
    }

}