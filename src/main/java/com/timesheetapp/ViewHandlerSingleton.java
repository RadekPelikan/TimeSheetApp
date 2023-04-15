package com.timesheetapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ViewHandlerSingleton {
    @FXML
    public static VBox sidePanel;


    private static ViewHandlerSingleton viewHandler;

    public ViewHandlerSingleton(VBox sidePanel) {
        if (viewHandler == null) viewHandler = this;
        ViewHandlerSingleton.sidePanel = sidePanel;
    }

    public static ViewHandlerSingleton getViewHandler() {
        return viewHandler;
    }

    private static void setView(String filePath) {
        sidePanel.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(StartApp.class.getResource(filePath));
        Pane eventView;
        try {
            eventView = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sidePanel.getChildren().add(eventView);
    }

    public static void showEvents() {
        setView("event-view.fxml");
    }

    public static void showCreateNew() {
        setView("new-event-view.fxml");
    }

}
