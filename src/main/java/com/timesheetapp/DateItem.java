package com.timesheetapp;

import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalTime;

class DateItem {
    private final int day;
    private final LocalDate date;
    private LocalTime timeFrom;
    private LocalTime timeTo;
    private String info;
    private Color color;

    public DateItem(int day, LocalDate date, LocalTime timeFrom, LocalTime timeTo, String info, Color color) {
        this.day = day;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.info = info;
        this.color = color;
    }

    public DateItem(int day, LocalDate date, LocalTime timeFrom, LocalTime timeTo, String info) {
        this.day = day;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.info = info;
    }

    public DateItem(int day, LocalDate date, LocalTime timeFrom, LocalTime timeTo, Color color) {
        this.day = day;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.color = color;
    }

    public DateItem(int day, LocalDate date) {
        this.day = day;
        this.date = date;
    }

    public int getDay() {
        return day;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public String getInfo() {
        return info;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "DateItem{" +
                "day=" + day +
                ", date=" + date +
                ", timeFrom=" + timeFrom +
                ", timeTo=" + timeTo +
                ", info='" + info + '\'' +
                ", color=" + color +
                '}';
    }
}
