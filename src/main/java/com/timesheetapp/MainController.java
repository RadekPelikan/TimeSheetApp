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

    private int moved = 0;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDate date = LocalDate.now();

        ArrayList<DateItem> month = createMonth(date);
        populateCalendar(month);
    }

    @FXML
    public void onForward(ActionEvent actionEvent) {
        move(1);
    }

    @FXML
    public void onBackward(ActionEvent actionEvent) {
        move(-1);
    }

    @FXML
    public void onToday(ActionEvent actionEvent) {
        move(0);
    }

    private ArrayList<DateItem> createMonth(LocalDate date) {
        Month month = Month.of(date.getMonthValue());
        String monthName = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        int year = date.getYear();
        monthLabel.setText(String.format("%s %d", monthName, year));

        // Get me a first day of the month
        int firstDayOfMonth = date.withDayOfMonth(1).getDayOfWeek().getValue();

        int daysInMonth = month.length(date.isLeapYear());
        ArrayList<DateItem> dateItems = new ArrayList<>();
        for (int i = firstDayOfMonth; i < firstDayOfMonth + daysInMonth; i++) {
            if (i < 10) {
                dateItems.add(new DateItem(i, "Test", Color.valueOf("#aaffaa")));
                continue;
            }
            if (i < 20) {
                dateItems.add(new DateItem(i, "Test 2"));
                continue;
            }
            if (i < 23) {
                dateItems.add(new DateItem(i, Color.valueOf("#ffffaa")));
                continue;
            }
            dateItems.add(new DateItem(i));
        }
        return dateItems;
    }

    /**
     * Move calendar by amount of months
     * If amount is 0, it will reset to current month
     * @param amount Amount of months to move
     */
    private void move(int amount) {
        LocalDate date = LocalDate.now();
        moved += amount;
        if (amount == 0) moved = 0;
        date = date.plusMonths(moved);

        ArrayList<DateItem> month = createMonth(date);
        populateCalendar(month);
    }

    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

    private String colorToStyle(Color color) {
        return String.format("-fx-background-color: %s;", colorToHex(color));
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
            Color color = dateItems.get(i - firstDay + 1).getColor();

            if (color != null) {
                pane.setStyle(colorToStyle(color));

                // On hover darken color
                pane.setOnMouseEntered(event -> {
                    Color darkerColor = color.darker();
                    pane.setStyle(String.format("-fx-background-color: %s;", colorToHex(darkerColor)));
                });
                pane.setOnMouseExited(event -> {
                    pane.setStyle(colorToStyle(color));
                });
            }

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
    private String info;
    private Color color;

    public DateItem(int day, String info, Color color) {
        this.day = day;
        this.info = info;
        this.color = color;
    }

    public DateItem(int day, String info) {
        this.day = day;
        this.info = info;
    }

    public DateItem(int day, Color color) {
        this.day = day;
        this.color = color;
    }

    public DateItem(int day) {
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public String getInfo() {
        return info;
    }

    public Color getColor() {
        return color;
    }
}