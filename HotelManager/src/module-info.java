module HotelManager {
    requires javafx.controls;
    requires javafx.fxml;

    opens hotelmanager to javafx.fxml;
    opens controlador to javafx.fxml;
    opens vista to javafx.fxml;
    opens modelo to javafx.base;

    exports hotelmanager;
    exports modelo;
    exports controlador;
    exports persistencia;
}