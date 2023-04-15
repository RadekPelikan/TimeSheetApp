package com.timesheetapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public GridPane gridPane;
    @FXML
    public Label monthLabel;

    private Calendar calendar;

    private int counter = 1;


    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: Multiple data points per single day
        // TODO: On click of a day cell, list all data points in right panel
        // TODO: On click of a day cell, add ability to create new data point
        // TODO: On click of a data point, add ability to edit or delete it
        ArrayList<TSEvent> data = new ArrayList<>();
        LocalDate date = LocalDate.now();
        data.add(new TSEvent(date, "Today", Color.valueOf("#88cc77")));
        calendar = new Calendar(gridPane, monthLabel, data);
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

    public void onTest(ActionEvent actionEvent) {
        calendar.addRecord(new TSEvent(LocalDate.now().plusDays(counter), "Today " + counter, Color.valueOf("#88cc77")));
        counter++;
    }
}

