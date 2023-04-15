package com.timesheetapp;

import javafx.scene.paint.Color;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TSEvent {
    private final LocalDateTime timeFrom;
    private final LocalDateTime timeTo;
    private String info;
    private Color color;

    public TSEvent(LocalDate date, String info, Color color) {
        this.timeFrom = LocalDateTime.of(date, LocalDateTime.now().toLocalTime());
        this.timeTo = LocalDateTime.of(date, LocalDateTime.now().toLocalTime());
        this.info = info;
        this.color = color;
    }

    public TSEvent(LocalDate date, String info) {
        this.timeFrom = LocalDateTime.of(date, LocalDateTime.now().toLocalTime());
        this.timeTo = LocalDateTime.of(date, LocalDateTime.now().toLocalTime());
        this.info = info;
    }

    public TSEvent(LocalDate date, Color color) {
        this.timeFrom = LocalDateTime.of(date, LocalDateTime.now().toLocalTime());
        this.timeTo = LocalDateTime.of(date, LocalDateTime.now().toLocalTime());
        this.color = color;
    }

    public TSEvent(LocalDateTime timeFrom, LocalDateTime timeTo, String info, Color color) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.info = info;
        this.color = color;
    }

    public TSEvent(LocalDateTime timeFrom, LocalDateTime timeTo, String info) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.info = info;
    }

    public TSEvent(LocalDateTime timeFrom, LocalDateTime timeTo, Color color) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.color = color;
    }

    public LocalDate getDate() {
        return timeFrom.toLocalDate();
    }

    public int getDay() {
        return timeFrom.getDayOfWeek().getValue();
    }

    public int getDays() {
        int days;
        if (timeFrom.toLocalDate().equals(timeTo.toLocalDate())) {
            days = 1;
        } else {
            days = timeFrom.toLocalDate().until(timeTo.toLocalDate()).getDays();
        }
        return days;
    }

    public LocalDateTime getTimeFrom() {
        return timeFrom;
    }

    public LocalDateTime getTimeTo() {
        return timeTo;
    }

    public String getInfo() {
        return info;
    }

    public Color getColor() {
        return color;
    }
}
