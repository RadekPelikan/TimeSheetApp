package com.timesheetapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public GridPane gridPane;
    @FXML
    public Label monthLabel;
    @FXML
    public VBox sidePanel;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: Multiple data points per single day
        // TODO: On click of a day cell, list all data points in right panel
        // TODO: On click of a day cell, add ability to create new data point
        // TODO: On click of a data point, add ability to edit or delete it
        new CalendarSingleton(new Calendar(gridPane, monthLabel));
        new ViewHandlerSingleton(sidePanel);

        CalendarSingleton.addRecord(new TSEvent(LocalDate.now(), "Today", Color.valueOf("#88cc77")));
        CalendarSingleton.addRecord(new TSEvent(LocalDate.now().plusDays(1), "Tomorrow", Color.valueOf("#88cc77")));

        ViewHandlerSingleton.showEvents();
    }

    @FXML
    public void onForward(ActionEvent actionEvent) {
        CalendarSingleton.move(1);
    }

    @FXML
    public void onBackward(ActionEvent actionEvent) {
        CalendarSingleton.move(-1);
    }

    @FXML
    public void onToday(ActionEvent actionEvent) {
        CalendarSingleton.move(0);
    }

    @FXML
    public void onCalendarClick(MouseEvent mouseEvent) {

        // If clicked on first row
        double firstRowHeight = gridPane.getRowConstraints().get(0).getPrefHeight();
        if (mouseEvent.getY() < firstRowHeight) {
            CalendarSingleton.clearActiveDate();
            ViewHandlerSingleton.showEvents();
            return;
        }

        // If there is no active date show events
        LocalDate date = CalendarSingleton.getActiveDate();
        if (date == null) {
            ViewHandlerSingleton.showEvents();
            return;
        }

        // If clicked on a date with events, show events
        TSEvent item = CalendarSingleton.getItem(date);
        if (item != null) {
            ViewHandlerSingleton.showEvents();
        } else {
            ViewHandlerSingleton.showCreateNew();
        }
    }
}

