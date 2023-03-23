module com.example.demo7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens com.example.c482 to javafx.fxml;
    exports com.example.c482;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}