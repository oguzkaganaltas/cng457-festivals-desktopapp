module com.mustafaoguz.cng457festivalsdesktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;


    opens com.mustafaoguz.cng457festivalsdesktopapp to javafx.fxml;
    exports com.mustafaoguz.cng457festivalsdesktopapp;
}