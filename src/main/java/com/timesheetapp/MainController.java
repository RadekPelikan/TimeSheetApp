package com.timesheetapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public GridPane gridPane;
    @FXML
    public Label monthLabel;

    private Calendar calendar;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calendar = new Calendar(gridPane, monthLabel);
    }

    @FXML
    public void onForward(ActionEvent actionEvent) {
        calendar.move(1);
    }

    @FXML
    public void onBackward(ActionEvent actionEvent) {
        calendar.move(-1);
    }

    @FXML
    public void onToday(ActionEvent actionEvent) {
        calendar.move(0);
    }


}

