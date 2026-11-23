package hotelmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HotelManager extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vista/Mainview.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("HotelManager - Sistema de Reservas");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.getIcons().add(new javafx.scene.image.Image("file:imagenes/logo.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}