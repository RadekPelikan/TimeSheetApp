module com.timesheetapp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.timesheetapp to javafx.fxml;
    exports com.timesheetapp;
}