/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author ferna
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import modelo.NodoReserva;
import modelo.Reserva;

public class CancelacionesController implements Initializable {

    @FXML
    private Label lblTopePila;
    @FXML
    private TextArea txtPilaCompleta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarVistaPila();
    }

    private void actualizarVistaPila() {
        NodoReserva actual = MainController.pilaCancelaciones.getTope();
        if (actual == null) {
            lblTopePila.setText("La pila de cancelaciones está vacía");
            txtPilaCompleta.setText("No hay solicitudes rechazadas para deshacer.");
            return;
        }

        lblTopePila.setText("Reserva " + actual.dato.getIdReserva() + " - " + actual.dato.getNombreCliente() + " (Hab " + actual.dato.getNumeroHabitacion() + ")");

        StringBuilder sb = new StringBuilder();
        sb.append("Historial apilado (Del tope hacia el fondo):\n");
        sb.append("-------------------------------------------------------------\n");
        while (actual != null) {
            Reserva r = actual.dato;
            sb.append("• ").append(r.getIdReserva()).append(" | ").append(r.getNombreCliente())
              .append(" | Hab ").append(r.getNumeroHabitacion()).append(" | $").append((int) r.getPrecioTotal()).append("\n");
            actual = actual.siguiente;
        }
        txtPilaCompleta.setText(sb.toString());
    }

    @FXML
    private void reincorporarUltima(ActionEvent event) {
        Reserva recuperada = MainController.pilaCancelaciones.desapilar();
        if (recuperada == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No hay cancelaciones para deshacer.");
            MainController.aplicarIcono(alert);
            alert.showAndWait();
            return;
        }

        // Devuelve la solicitud a la Cola de Recepción y refresca la vista
        MainController.colaRecepcion.encolar(recuperada);
        actualizarVistaPila();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cancelación Revertida");
        alert.setHeaderText(null);
        alert.setContentText("La reserva de " + recuperada.getNombreCliente() + " volvió a la Cola de Recepción.");
        MainController.aplicarIcono(alert);
        alert.showAndWait();
    }
}