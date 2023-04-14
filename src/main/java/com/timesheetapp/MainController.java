package com.timesheetapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateGrid();

    }

    private void populateGrid() {
        int counter = 1;
        // 7 Columns
        for (int x = 0; x < 7; x++) {
            // 6 Rows (0 is the header)
            for (int y = 1; y < 7; y++) {
                Pane pane = new Pane();
                pane.getStyleClass().add("cal-item");

                GridPane.setHgrow(pane, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setVgrow(pane, javafx.scene.layout.Priority.ALWAYS);
                GridPane.setMargin(pane, new javafx.geometry.Insets(1));

                Label label = new Label(String.valueOf(counter++));
                label.prefWidthProperty().bind(pane.widthProperty());
                label.prefHeightProperty().bind(pane.heightProperty());
                label.setAlignment(javafx.geometry.Pos.CENTER);

                pane.getChildren().add(label);
                gridPane.add(pane, x, y);
            }
        }
    }
}