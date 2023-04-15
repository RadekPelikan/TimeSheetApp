package com.timesheetapp;

import javafx.scene.layout.Pane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarSingleton {

    private static Calendar calendar;

    public CalendarSingleton(Calendar calendar) {
        setCalendar(calendar);
    }

    public static Calendar getCalendar() {
        return calendar;
    }

    public static void setCalendar(Calendar calendar) {
        CalendarSingleton.calendar = calendar;
    }

    public static void addRecord(TSEvent record) {
        calendar.addRecord(record);
    }

    public static void move(int amount) {
        calendar.move(amount);
    }

    public static void refresh() {
        calendar.refresh();
    }

    public static void clearActiveDate() {
        calendar.clearActiveDate();
    }

    public static LocalDate getActiveDate() {
        return calendar.getActiveDate();
    }

    public static HashMap<String, TSEvent> getData() {
        return calendar.getData();
    }

    public static TSEvent getItem(LocalDate date) {
        return getData().get(date.toString());
    }
}