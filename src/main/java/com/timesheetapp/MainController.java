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
        double firstRowHeight = gridPane.getRowConstraints().get(0).getPrefHeight();
        if (mouseEvent.getY() < firstRowHeight) {
            ViewHandlerSingleton.showEvents();
            return;
        }


        LocalDate date = CalendarSingleton.getActiveDate();
        TSEvent item = CalendarSingleton.getItem(date);
        if (item != null) {
            System.out.println("Show events");
            ViewHandlerSingleton.showEvents();
        } else {
            System.out.println("Show Create new");
            ViewHandlerSingleton.showCreateNew();
        }
    }
}

