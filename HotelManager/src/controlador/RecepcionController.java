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
import persistencia.GestorArchivos;

public class RecepcionController implements Initializable {

    @FXML
    private Label lblFrenteCola;
    @FXML
    private TextArea txtListaCola;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarVistaCola();
    }

    private void actualizarVistaCola() {
        NodoReserva actual = MainController.colaRecepcion.getFrente();
        if (actual == null) {
            lblFrenteCola.setText("Siguiente en atender: Ninguna solicitud en espera");
            txtListaCola.setText("La cola de recepción (FIFO) está vacía.\nVe a 'Habitaciones' para solicitar una reserva.");
            return;
        }

        lblFrenteCola.setText("Siguiente en atender (Frente): " + actual.dato.getNombreCliente() + " - Hab. " + actual.dato.getNumeroHabitacion());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s | %-10s | %-22s | %-10s | %-12s\n", "Turno", "ID", "Cliente", "Habitación", "Total"));
        sb.append("--------------------------------------------------------------------------\n");

        int turno = 1;
        while (actual != null) {
            Reserva r = actual.dato;
            sb.append(String.format("%-6d | %-10s | %-22s | %-10s | $%-11.0f\n",
                    turno++, r.getIdReserva(), r.getNombreCliente(), "Hab " + r.getNumeroHabitacion(), r.getPrecioTotal()));
            actual = actual.siguiente;
        }
        txtListaCola.setText(sb.toString());
    }

    @FXML
    private void confirmarSiguiente(ActionEvent event) {
        Reserva atendida = MainController.colaRecepcion.desencolar();
        if (atendida == null) {
            mostrarAlerta("Cola vacía", "No hay solicitudes pendientes en recepción.");
            return;
        }

        // 1. Pasa a la Lista Simple de Facturación
        MainController.historialReservas.insertarReserva(atendida);
        // 2. Marca la habitación como Ocupada en la Lista Doble
        MainController.catalogoHabitaciones.actualizarEstado(atendida.getNumeroHabitacion(), "Ocupada");
        // 3. Guarda ambos cambios en los .txt
        GestorArchivos.guardarReservas(MainController.historialReservas, "reservas.txt");
        GestorArchivos.guardarHabitaciones(MainController.catalogoHabitaciones, "habitaciones.txt");

        actualizarVistaCola();
        mostrarAlerta("Reserva Confirmada", "Se confirmó la reserva de " + atendida.getNombreCliente() + " y se facturó.");
    }

    @FXML
    private void rechazarSiguiente(ActionEvent event) {
        Reserva rechazada = MainController.colaRecepcion.desencolar();
        if (rechazada == null) {
            mostrarAlerta("Cola vacía", "No hay solicitudes para rechazar.");
            return;
        }

        // Pasa al tope de la Pila LIFO de Cancelaciones
        MainController.pilaCancelaciones.apilar(rechazada);
        actualizarVistaCola();
        mostrarAlerta("Solicitud Rechazada", "La solicitud de " + rechazada.getNombreCliente() + " se envió a Cancelaciones (LIFO).");
    }

   private void mostrarAlerta(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    MainController.aplicarIcono(alert); 
    alert.showAndWait();
}
}