package controlador;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import modelo.ColaRecepcion;
import modelo.Habitacion;
import modelo.ListaDobleHabitaciones;
import modelo.ListaSimpleHistorial;
import modelo.PilaCancelaciones;
import modelo.Reserva;
import persistencia.GestorArchivos;

public class MainController implements Initializable {

    // 1. Estructuras de datos propias compartidas entre todas las vistas
    public static ListaDobleHabitaciones catalogoHabitaciones;
    public static ListaSimpleHistorial historialReservas;
    public static ColaRecepcion colaRecepcion = new ColaRecepcion();
    public static PilaCancelaciones pilaCancelaciones = new PilaCancelaciones();

    // 2. Contenedor central dinámico y vista inicial del carrusel
    @FXML
    private StackPane contenedorCentral;
    private Node vistaHabitaciones;

    // 3. Componentes visuales del carrusel de habitaciones
    @FXML
    private ImageView imgHabitacion;
    @FXML
    private Label lblNumeroHabitacion;
    @FXML
    private Label lblTipoHabitacion;
    @FXML
    private Label lblPrecioHabitacion;
    @FXML
    private Label lblEstadoHabitacion;
    @FXML
    private Button btnReservar;

    // 4. Botones del menú lateral
    @FXML
    private Button btnMenuRecepcion;
    @FXML
    private Button btnMenuHabitaciones;
    @FXML
    private Button btnMenuFacturacion;
    @FXML
    private Button btnMenuCancelaciones;
    @FXML
    private Button btnMenuLimpieza;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catalogoHabitaciones = GestorArchivos.cargarHabitaciones("habitaciones.txt");
        historialReservas = GestorArchivos.cargarReservas("reservas.txt");

        if (contenedorCentral != null && !contenedorCentral.getChildren().isEmpty()) {
            vistaHabitaciones = contenedorCentral.getChildren().get(0);
        }

        actualizarVistaHabitacion();
    }

    // --- MÉTODO REUTILIZABLE PARA PONER EL LOGO EN CUALQUIER VENTANA EMERGENTE ---
    public static void aplicarIcono(Dialog<?> dialog) {
        try {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:imagenes/logo.png"));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono en el diálogo: " + e.getMessage());
        }
    }

    // --- CONTROLES DEL CARRUSEL (LISTA DOBLEMENTE ENLAZADA) ---
    @FXML
    private void btnSiguienteClick(ActionEvent event) {
        catalogoHabitaciones.siguienteHabitacion();
        actualizarVistaHabitacion();
    }

    @FXML
    private void btnAnteriorClick(ActionEvent event) {
        catalogoHabitaciones.anteriorHabitacion();
        actualizarVistaHabitacion();
    }

    private void actualizarVistaHabitacion() {
        Habitacion actual = catalogoHabitaciones.obtenerActual();
        if (actual != null) {
            lblNumeroHabitacion.setText("Habitación: " + actual.getNumero());
            lblTipoHabitacion.setText("Tipo: " + actual.getTipo());
            lblPrecioHabitacion.setText("Precio: $" + actual.getPrecioNoche() + " / noche");
            lblEstadoHabitacion.setText("Estado: " + actual.getEstado());

            String nombreImg = actual.getImagen();
            String rutaImagen = nombreImg.startsWith("imagenes/") ? "file:" + nombreImg : "file:imagenes/" + nombreImg;
            try {
                imgHabitacion.setImage(new Image(rutaImagen));
            } catch (Exception e) {
                System.out.println("No se pudo cargar la imagen: " + rutaImagen);
            }

            boolean disponible = actual.getEstado().equalsIgnoreCase("Disponible");
            btnReservar.setDisable(false);
            if (disponible) {
                btnReservar.setText("Reservar esta habitación");
                btnReservar.setStyle("-fx-background-color: #e59a16; -fx-text-fill: white; -fx-font-size: 17px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 6, 0, 0, 2); -fx-cursor: hand;");
            } else {
                btnReservar.setText("Liberar habitación (Check-Out)");
                btnReservar.setStyle("-fx-background-color: #10b981; -fx-text-fill: white; -fx-font-size: 17px; -fx-font-weight: bold; -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 6, 0, 0, 2); -fx-cursor: hand;");
            }
        } else {
            lblNumeroHabitacion.setText("No hay habitaciones registradas");
            lblTipoHabitacion.setText("");
            lblPrecioHabitacion.setText("");
            lblEstadoHabitacion.setText("");
            btnReservar.setDisable(true);
        }
    }

    // --- BOTÓN PRINCIPAL: RESERVAR (CON NOCHES Y MONTO) O LIBERAR (CHECK-OUT) ---
    @FXML
    private void abrirFormularioReserva(ActionEvent event) {
        Habitacion actual = catalogoHabitaciones.obtenerActual();
        if (actual == null) {
            return;
        }

        // CASO 1: Habitación OCUPADA -> Check-Out
        if (!actual.getEstado().equalsIgnoreCase("Disponible")) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Check-Out de Habitación");
            confirmacion.setHeaderText("¿Deseas liberar la Habitación " + actual.getNumero() + "?");
            confirmacion.setContentText("El huésped finalizará su estadía y la habitación volverá a estar 'Disponible'.");
            aplicarIcono(confirmacion);

            confirmacion.showAndWait().ifPresent(respuesta -> {
                if (respuesta == ButtonType.OK) {
                    actual.setEstado("Disponible");
                    GestorArchivos.guardarHabitaciones(catalogoHabitaciones, "habitaciones.txt");
                    actualizarVistaHabitacion();

                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                    info.setTitle("Check-Out Completado");
                    info.setHeaderText(null);
                    info.setContentText("La Habitación " + actual.getNumero() + " ha sido liberada correctamente.");
                    aplicarIcono(info);
                    info.showAndWait();
                }
            });
            return;
        }

        // CASO 2: Habitación DISPONIBLE -> Formulario de Reserva (Nombre, Noches y Pago)
        Dialog<Reserva> dialog = new Dialog<>();
        dialog.setTitle("Nueva Solicitud de Reserva");
        dialog.setHeaderText("Habitación " + actual.getNumero() + " (" + actual.getTipo() + ") — Tarifa: $" + String.format("%.0f", actual.getPrecioNoche()) + " / noche");
        aplicarIcono(dialog);

        ButtonType btnConfirmarType = new ButtonType("Enviar a Recepción", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnConfirmarType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setPadding(new Insets(20, 30, 10, 20));

        TextField txtNombre = new TextField();
        txtNombre.setPromptText("Ej. Carlos Gómez");

        TextField txtNoches = new TextField("1");
        txtNoches.setPromptText("Número de noches");

        TextField txtTotalPagar = new TextField(String.format("%.0f", actual.getPrecioNoche()));
        txtTotalPagar.setPromptText("Monto total en COP");

        // Recalcula automáticamente el total a pagar cuando cambia el número de noches
        txtNoches.textProperty().addListener((obs, anterior, nuevoValor) -> {
            try {
                int noches = Integer.parseInt(nuevoValor.trim());
                if (noches > 0) {
                    double totalCalculado = actual.getPrecioNoche() * noches;
                    txtTotalPagar.setText(String.format("%.0f", totalCalculado));
                }
            } catch (NumberFormatException ignored) {
                // Si borra el número temporalmente, no hace nada hasta que escriba uno válido
            }
        });

        grid.add(new Label("Nombre del huésped:"), 0, 0);
        grid.add(txtNombre, 1, 0);
        grid.add(new Label("Cantidad de noches:"), 0, 1);
        grid.add(txtNoches, 1, 1);
        grid.add(new Label("Total a pagar ($ COP):"), 0, 2);
        grid.add(txtTotalPagar, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(botonPresionado -> {
            if (botonPresionado == btnConfirmarType) {
                String nombre = txtNombre.getText().trim();
                if (nombre.isEmpty()) {
                    return null;
                }
                try {
                    double montoFinal = Double.parseDouble(txtTotalPagar.getText().trim());
                    if (montoFinal <= 0) {
                        return null;
                    }
                    String id = "R-" + (int) (Math.random() * 900 + 100);
                    return new Reserva(id, nombre, actual.getNumero(), montoFinal, LocalDate.now().toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(nuevaReserva -> {
            colaRecepcion.encolar(nuevaReserva);

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Solicitud Registrada");
            info.setHeaderText(null);
            info.setContentText("Solicitud #" + nuevaReserva.getIdReserva() + " de " + nuevaReserva.getNombreCliente()
                    + " por $" + String.format("%.0f", nuevaReserva.getPrecioTotal()) + " enviada a la Cola de Recepción.");
            aplicarIcono(info);
            info.showAndWait();
        });
    }

    // --- NAVEGACIÓN DEL MENÚ LATERAL ---
    @FXML
    private void mostrarRecepcion(ActionEvent event) {
        cambiarSeccion(btnMenuRecepcion, "/vista/RecepcionView.fxml");
    }

    @FXML
    private void mostrarHabitaciones(ActionEvent event) {
        cambiarSeccion(btnMenuHabitaciones, null);
    }

    @FXML
    private void mostrarFacturacion(ActionEvent event) {
        cambiarSeccion(btnMenuFacturacion, "/vista/FacturacionView.fxml");
    }

    @FXML
    private void mostrarCancelaciones(ActionEvent event) {
        cambiarSeccion(btnMenuCancelaciones, "/vista/CancelacionesView.fxml");
    }

    @FXML
    private void mostrarLimpieza(ActionEvent event) {
        cambiarSeccion(btnMenuLimpieza, "/vista/LimpiezaView.fxml");
    }

    private void cambiarSeccion(Button botonActivo, String rutaFxml) {
        String estiloNormal = "-fx-background-color: #243046; -fx-text-fill: white; -fx-alignment: BASELINE_LEFT; -fx-padding: 0 0 0 20; -fx-font-size: 15px; -fx-background-radius: 6; -fx-cursor: hand;";
        String estiloActivo = "-fx-background-color: #e59a16; -fx-text-fill: white; -fx-alignment: BASELINE_LEFT; -fx-padding: 0 0 0 20; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand;";

        btnMenuRecepcion.setStyle(estiloNormal);
        btnMenuHabitaciones.setStyle(estiloNormal);
        btnMenuFacturacion.setStyle(estiloNormal);
        btnMenuCancelaciones.setStyle(estiloNormal);
        btnMenuLimpieza.setStyle(estiloNormal);

        botonActivo.setStyle(estiloActivo);

        try {
            if (rutaFxml == null) {
                contenedorCentral.getChildren().setAll(vistaHabitaciones);
                actualizarVistaHabitacion();
            } else {
                URL recurso = getClass().getResource(rutaFxml);
                if (recurso != null) {
                    Parent nuevaVista = FXMLLoader.load(recurso);
                    contenedorCentral.getChildren().setAll(nuevaVista);
                } else {
                    Alert aviso = new Alert(Alert.AlertType.INFORMATION);
                    aviso.setTitle("Vista pendiente");
                    aviso.setHeaderText(null);
                    aviso.setContentText("Aún falta crear el archivo: " + rutaFxml);
                    aplicarIcono(aviso);
                    aviso.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}