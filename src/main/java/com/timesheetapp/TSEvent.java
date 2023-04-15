package com.timesheetapp;

import javafx.scene.paint.Color;

import java.time.LocalDate;

public class TSEvent {
    private LocalDate date;
    private String info;
    private Color color;

    public TSEvent(LocalDate date, String info, Color color) {
        this.date = date;
        this.info = info;
        this.color = color;
    }

    public TSEvent(LocalDate date, String info) {
        this.date = date;
        this.info = info;
    }

    public TSEvent(LocalDate date, Color color) {
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
