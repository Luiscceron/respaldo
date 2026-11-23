/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author ferna
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

public class FacturacionController implements Initializable {

    @FXML
    private Label lblResumenFacturacion;
    @FXML
    private TextArea txtHistorialFacturas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarHistorial();
    }

    private void cargarHistorial() {
        NodoReserva actual = MainController.historialReservas.getPrimerNodoReal();
        double totalRecaudado = 0;
        int contador = 0;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-22s | %-10s | %-12s | %-12s\n", "ID", "Huésped", "Habitación", "Total", "Fecha"));
        sb.append("-----------------------------------------------------------------------------\n");

        while (actual != null) {
            Reserva r = actual.dato;
            totalRecaudado += r.getPrecioTotal();
            contador++;
            sb.append(String.format("%-10s | %-22s | %-10s | $%-11.0f | %-12s\n",
                    r.getIdReserva(), r.getNombreCliente(), "Hab " + r.getNumeroHabitacion(), r.getPrecioTotal(), r.getFecha()));
            actual = actual.siguiente;
        }

        if (contador == 0) {
            txtHistorialFacturas.setText("Aún no hay reservas confirmadas en el historial.");
        } else {
            txtHistorialFacturas.setText(sb.toString());
        }

        lblResumenFacturacion.setText(String.format("Total Facturado: $%.0f COP  |  Reservas Confirmadas: %d", totalRecaudado, contador));
    }

    @FXML
    private void exportarReporte(ActionEvent event) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("reporte_facturas.txt"))) {
            bw.write("=== REPORTE DE FACTURACIÓN HOTELMANAGER ===\n\n");
            bw.write(lblResumenFacturacion.getText() + "\n\n");
            bw.write(txtHistorialFacturas.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reporte Exportado");
            alert.setHeaderText(null);
            alert.setContentText("Archivo 'reporte_facturas.txt' generado correctamente en la carpeta del proyecto.");
            MainController.aplicarIcono(alert); 
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
