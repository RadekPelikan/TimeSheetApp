package com.timesheetapp;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Calendar {

    @FXML
    private final GridPane gridPane;
    @FXML
    private final Label monthLabel;

    private LocalDate activeDate;

    private final HashMap<String, Cell> data = new HashMap<>();

    private int moved = 0;

    public Calendar(GridPane gridPane, Label monthLabel, ArrayList<TSEvent> data) {
        this.gridPane = gridPane;
        this.monthLabel = monthLabel;

        for (TSEvent record : data) {
            add(record);
        }

        LocalDate date = LocalDate.now();

        ArrayList<Cell> month = createMonth(date);
        populateCalendar(month);
    }

    public Calendar(GridPane gridPane, Label monthLabel) {
        this.gridPane = gridPane;
        this.monthLabel = monthLabel;

        LocalDate date = LocalDate.now();

        ArrayList<Cell> month = createMonth(date);
        populateCalendar(month);
    }

    /**
     * Add record to calendar and refreshes it
     *
     * @param record Record to add
     */
    public void addRecord(TSEvent record) {
        add(record);
        refresh();
    }

    /**
     * Move calendar by amount of months
     * If amount is 0, it will reset to current month
     *
     * @param amount Amount of months to move
     */
    public void move(int amount) {
        moved += amount;
        if (amount == 0) moved = 0;

        refresh();
    }

    /**
     * Refresh calendar with current data
     */
    public void refresh() {
        ArrayList<Cell> month = createMonth();
        populateCalendar(month);
    }

    public void clearActiveDate() {
        activeDate = null;
        // Remove all active classes
        gridPane.getChildren().forEach(node -> {
            if (node instanceof Pane pane) {
                pane.getStyleClass().remove("cal-item-active");
            }
        });
    }

    public Cell getCell(LocalDate date) {
        return data.get(date.toString());
    }

    public LocalDate getActiveDate() {
        return activeDate;
    }

    public HashMap<String, Cell> getData() {
        return data;
    }

    private ArrayList<Cell> createMonth() {
        LocalDate date = LocalDate.now();
        date = date.plusMonths(moved);
        return createMonth(date);
    }

    private ArrayList<Cell> createMonth(LocalDate date) {

        Month month = Month.of(date.getMonthValue());
        String monthName = month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        int year = date.getYear();
        monthLabel.setText(String.format("%s %d", monthName, year));

        // Get me a first day of the month
        int firstDayOfMonth = date.withDayOfMonth(1).getDayOfWeek().getValue();

        int daysInMonth = month.length(date.isLeapYear());
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = firstDayOfMonth; i < firstDayOfMonth + daysInMonth; i++) {
            LocalDate newDate = date.withDayOfMonth(i - firstDayOfMonth + 1);

            ArrayList<DateItem> dataDateItems = getCell(newDate).getDataEntries();
            if (dataDateItems.size() > 0) {
                Cell cell = new Cell(newDate, i);
                cell.addAll(dataDateItems);
                continue;
            }

            cells.add(new Cell(newDate, i));
        }
        return cells;
    }

    private String colorToHex(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

    private String colorToStyle(Color color) {
        return String.format("-fx-background-color: %s;", colorToHex(color));
    }

    private void populateCalendar(ArrayList<Cell> cell) {
        // Clear all except first row
        gridPane.getChildren().removeIf(node -> {
            Integer rowIndex = GridPane.getRowIndex(node);
            return rowIndex != null && rowIndex > 0;
        });

        for (int i = 0; i < 7 * 6; i++) {
            int x = i % 7;
            int y = i / 7 + 1;

            Pane pane = createCell(i, cell.get(i));

            gridPane.add(pane, x, y);
        }
    }

    private Pane createCell(int i, Cell cell) {
        ArrayList<DateItem> dateItems = cell.getDataEntries();
        int firstDay = dateItems.get(0).getDay();
        int lastDay = dateItems.get(dateItems.size() - 1).getDay();

        boolean hasData = i >= firstDay && i < lastDay;

        Pane pane = new Pane();
        pane.getStyleClass().add("cal-item");

        pane.getProperties().put("index", i);
        pane.getProperties().put("hasData", hasData);

        // On click of on pane print out i
        pane.setOnMouseClicked(this::handleCellClick);

        GridPane.setHgrow(pane, javafx.scene.layout.Priority.ALWAYS);
        GridPane.setVgrow(pane, javafx.scene.layout.Priority.ALWAYS);
        GridPane.setMargin(pane, new Insets(1));

        if (!hasData) return pane;

        // If it's not empty
        DateItem dateItem = dateItems.get(i - firstDay + 1);

        int day = dateItem.getDay() - firstDay + 1;
        String info = dateItem.getInfo();
        Color color = dateItem.getColor();
        LocalDate date = dateItem.getDate();

        if (info != null) {
            System.out.println(day + " " + info);
        }

        // If the cells is active, add active class
        if (activeDate != null && activeDate.equals(date)) {
            pane.getStyleClass().add("cal-item-active");
        }

        pane.getProperties().put("date", date);

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

        return pane;
    }

    private Pane createCell(int x, int y, Cell cell) {
        int i = (y - 1) * 7 + x;
        return createCell(i, cell);
    }

    private void handleCellClick(MouseEvent event) {
        Pane pane = (Pane) event.getSource();
        boolean hasData = (boolean) pane.getProperties().get("hasData");
        if (!hasData) {
            clearActiveDate();
            return;
        }

        LocalDate date = (LocalDate) pane.getProperties().get("date");

        // If clicked on the same cell twice, clear active date
        if (activeDate != null && activeDate.equals(date)) {
            clearActiveDate();
            return;
        }

        clearActiveDate();

        pane.getStyleClass().add("cal-item-active");

        activeDate = date;
    }

    private void add(TSEvent event) {

        for (int i = 0; i < event.getDays(); i++) {
            int day = event.getDay() + i;
            LocalDate date = event.getDate().plusDays(i);
            String info = event.getInfo();
            Color color = event.getColor();

            LocalTime timeFrom = event.getTimeFrom().toLocalTime();
            LocalTime timeTo = event.getTimeTo().toLocalTime();
            // TODO: When looping through days, set the middle days time from 00:00 to 23:59

            DateItem dateItem = new DateItem(day, date, timeFrom, timeTo, info, color);
            data.get(date.toString()).add(dateItem);
        }
    }

}

class Cell {
    private LocalDate date;
    private int x;
    private int y;
    private int i;
    private ArrayList<DateItem> dataEntries = new ArrayList<>();

    public Cell(LocalDate date, int i ) {
        this.i = i;
        this.x = i % 7;
        this.y = i / 7 + 1;
        this.date = date;
    }

    public Cell(LocalDate date, int x, int y ) {
        this.x = x;
        this.y = y;
        this.i = (y - 1) * 7 + x;
        this.date = date;
    }

    public void add(DateItem dateItem) {
        dataEntries.add(dateItem);
    }

    public void addAll(ArrayList<DateItem> dateItems) {
        dataEntries.addAll(dateItems);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getI() {
        return i;
    }

    public ArrayList<DateItem> getDataEntries() {
        return dataEntries;
    }
}
