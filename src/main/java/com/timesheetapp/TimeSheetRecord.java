package com.timesheetapp;

import javafx.scene.paint.Color;

import java.time.LocalDate;

public class TimeSheetRecord {
    private LocalDate date;
    private String info;
    private Color color;

    public TimeSheetRecord(LocalDate date, String info, Color color) {
        this.date = date;
        this.info = info;
        this.color = color;
    }

    public TimeSheetRecord(LocalDate date, String info) {
        this.date = date;
        this.info = info;
    }

    public TimeSheetRecord(LocalDate date, Color color) {
        this.date = date;
        this.color = color;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getInfo() {
        return info;
    }

    public Color getColor() {
        return color;
    }
}
