package com.timesheetapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    public GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Grid has 7 columnds and 6 rows, populate every cell with a label x and y position


        int counter = 1;
        for (int x = 0; x < 7; x++) {
            for (int y = 1; y < 7; y++) {
                Pane pane = new Pane();

                pane.setPrefSize(100, 100);
                GridPane.setMargin(pane, new javafx.geometry.Insets(5));

                pane.setStyle("-fx-border-color: black;");

                Label label = new Label(String.valueOf(counter++));
                label.prefWidthProperty().bind(pane.widthProperty());
                label.prefHeightProperty().bind(pane.heightProperty());
                label.setAlignment(javafx.geometry.Pos.CENTER);

                // On hover color the pane black and set labels text color to white
                pane.setOnMouseEntered(e -> {
                    pane.setStyle("-fx-background-color: black;");
                    label.setTextFill(javafx.scene.paint.Color.WHITE);
                });
                pane.setOnMouseExited(e -> {
                    pane.setStyle("-fx-background-color: white; -fx-border-color: black;");
                    label.setTextFill(javafx.scene.paint.Color.BLACK);
                });

                pane.getChildren().add(label);
                gridPane.add(pane, x, y);
            }
        }
    }
}