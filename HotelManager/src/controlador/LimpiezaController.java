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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import modelo.ListaCircularLimpieza;
import modelo.NodoLimpieza;
import modelo.PersonalLimpieza;

public class LimpiezaController implements Initializable {

    private static ListaCircularLimpieza listaLimpieza;

    @FXML
    private Label lblTurnoActual;
    @FXML
    private TextArea txtEquipoCircular;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (listaLimpieza == null) {
            listaLimpieza = new ListaCircularLimpieza();
            listaLimpieza.insertar(new PersonalLimpieza("Marta Díaz", "Asignada a Habitación 101"));
            listaLimpieza.insertar(new PersonalLimpieza("Luis Cerón", "Asignado a Habitación 102"));
            listaLimpieza.insertar(new PersonalLimpieza("Juan Bonilla", "Asignado a Habitación 201"));
            listaLimpieza.insertar(new PersonalLimpieza("Juan Cadavid", "Asignado a Habitación 202"));
            listaLimpieza.insertar(new PersonalLimpieza("Fernando Rojas", "Asignado a Suite 301"));
        }
        actualizarVistaCircular();
    }

    private void actualizarVistaCircular() {
        PersonalLimpieza actual = listaLimpieza.obtenerActual();
        if (actual != null) {
            lblTurnoActual.setText("Turno Actual: " + actual.getNombre() + " — " + actual.getAreaAsignada());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Ciclo continuo del personal de mantenimiento:\n");
        sb.append("-------------------------------------------------------------\n");

        NodoLimpieza inicio = listaLimpieza.getInicio();
        if (inicio != null) {
            NodoLimpieza aux = inicio;
            do {
                String marca = (aux.dato == actual) ? "  <-- [EN TURNO]" : "";
                sb.append("→ ").append(aux.dato.getNombre()).append(" (").append(aux.dato.getAreaAsignada()).append(")").append(marca).append("\n");
                aux = aux.siguiente;
            } while (aux != inicio);
        }
        txtEquipoCircular.setText(sb.toString());
    }

    @FXML
    private void rotarTurno(ActionEvent event) {
        listaLimpieza.rotarSiguienteTurno();
        actualizarVistaCircular();
    }
}
