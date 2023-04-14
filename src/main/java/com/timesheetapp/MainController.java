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

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public GridPane gridPane;
    @FXML
    public Label monthLabel;

    private int moved = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate date = LocalDate.now();

        ArrayList<DateItem> month = createMonth(date);
        populateCalendar(month);
    }

    private ArrayList<DateItem> createMonth(LocalDate date) {
        Month month = Month.of(date.getMonthValue());
        String monthName = month.getDisplayName(java.time.format.TextStyle.SHORT, java.util.Locale.ENGLISH);
        int year = date.getYear();
        monthLabel.setText(String.format("%s %d", monthName, year));

        // Get me a first day of the month
        int firstDayOfMonth = date.withDayOfMonth(1).getDayOfWeek().getValue();

        int daysInMonth = month.length(date.isLeapYear());
        ArrayList<DateItem> dateItems = new ArrayList<>();
        for (int i = firstDayOfMonth; i < firstDayOfMonth + daysInMonth; i++) {
            dateItems.add(new DateItem(i, "ahoj"));
        }
        return dateItems;
    }

    private void move(int amount) {
        LocalDate date = LocalDate.now();
        date = date.plusMonths(moved + amount);

        ArrayList<DateItem> month = createMonth(date);
        populateCalendar(month);
    }

    public void onForward(ActionEvent actionEvent) {
        move(++moved);
    }
    public void onBackward(ActionEvent actionEvent) {
        move(--moved);
    }


    private void populateCalendar(ArrayList<DateItem> dateItems) {
        // Clear all except first row
        gridPane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });

        int firstDay = dateItems.get(0).getDay();
        int lastDay = dateItems.get(dateItems.size() - 1).getDay();
        for (int i = 0; i < 7 * 6; i++) {
            int x = i % 7;
            int y = i / 7 + 1;

            Pane pane = new Pane();
            pane.getStyleClass().add("cal-item");

            GridPane.setHgrow(pane, javafx.scene.layout.Priority.ALWAYS);
            GridPane.setVgrow(pane, javafx.scene.layout.Priority.ALWAYS);
            GridPane.setMargin(pane, new Insets(1));

            if (i < firstDay - 1 || i > lastDay - 1) {
                gridPane.add(pane, x, y);
                continue;
            }

            int day = dateItems.get(i - firstDay + 1).getDay() - firstDay + 1;
            String info = dateItems.get(i - firstDay + 1).getInfo();

            VBox vBox = new VBox();
            vBox.prefWidthProperty().bind(pane.widthProperty());
            vBox.prefHeightProperty().bind(pane.heightProperty());

            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.prefWidthProperty().bind(pane.widthProperty());
            dayLabel.prefHeightProperty().bind(pane.heightProperty().divide(4));

            dayLabel.setAlignment(Pos.CENTER);

            Label infoLabel = new Label(info);
            infoLabel.prefWidthProperty().bind(pane.widthProperty());
            infoLabel.prefHeightProperty().bind(pane.heightProperty());
            infoLabel.setAlignment(Pos.TOP_CENTER);

            vBox.getChildren().add(dayLabel);
            vBox.getChildren().add(infoLabel);


            pane.getChildren().add(vBox);
            gridPane.add(pane, x, y);
        }
    }
}

class DateItem {
    private final int day;
    private final String info;

    public DateItem(int day, String info) {
        this.day = day;
        this.info = info;
    }

    public int getDay() {
        return day;
    }

    public String getInfo() {
        return info;
    }
}