/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

/**
 *
 * @author ferna
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import modelo.Habitacion;
import modelo.ListaDobleHabitaciones;
import modelo.NodoHabitacion;
import modelo.Reserva;
import modelo.ListaSimpleHistorial;
import modelo.NodoReserva;


public class GestorArchivos {

    private static final String DELIMITADOR = "::";

    // --------------------------------------------------------
    // MÉTODOS PARA HABITACIONES (Lista Doblemente Enlazada)
    // --------------------------------------------------------

    public static ListaDobleHabitaciones cargarHabitaciones(String rutaArchivo) {
        ListaDobleHabitaciones lista = new ListaDobleHabitaciones();
        File archivo = new File(rutaArchivo);

        // Si es la primera vez que se abre el programa y no hay txt, retorna la lista vacía
        if (!archivo.exists()) {
            return lista; 
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            // Lee línea por línea y rompe el texto usando el delimitador
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(DELIMITADOR);
                if (datos.length == 5) {
                    Habitacion h = new Habitacion(
                        datos[0],                           // numero
                        datos[1],                           // tipo
                        Double.parseDouble(datos[2]),       // precioNoche
                        datos[3],                           // estado
                        datos[4]                            // rutaImagen
                    );
                    lista.insertarHabitacion(h);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar habitaciones: " + e.getMessage());
        }
        return lista;
    }

    public static boolean guardarHabitaciones(ListaDobleHabitaciones lista, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            NodoHabitacion actual = lista.getCabeza();
            
            // Recorremos la lista desde la cabeza hasta el final, guardando en disco
            while (actual != null) {
                bw.write(actual.dato.toFormatoArchivo());
                bw.newLine();
                actual = actual.siguiente;
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar habitaciones: " + e.getMessage());
            return false;
        }
    }

    // --------------------------------------------------------
    // MÉTODOS PARA RESERVAS (Lista Simple con Nodo Cabecera)
    // --------------------------------------------------------

    public static ListaSimpleHistorial cargarReservas(String rutaArchivo) {
        ListaSimpleHistorial historial = new ListaSimpleHistorial();
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            return historial;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(DELIMITADOR);
                if (datos.length == 5) {
                    Reserva r = new Reserva(
                        datos[0],                           // idReserva
                        datos[1],                           // nombreCliente
                        datos[2],                           // numeroHabitacion
                        Double.parseDouble(datos[3]),       // precioTotal
                        datos[4]                            // fecha
                    );
                    historial.insertarReserva(r);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar reservas: " + e.getMessage());
        }
        return historial;
    }

    public static boolean guardarReservas(ListaSimpleHistorial historial, String rutaArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            
            // Usamos getPrimerNodoReal() para saltarnos el nodo fantasma al exportar
            NodoReserva actual = historial.getPrimerNodoReal(); 
            while (actual != null) {
                bw.write(actual.dato.toFormatoArchivo());
                bw.newLine();
                actual = actual.siguiente;
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar reservas: " + e.getMessage());
            return false;
        }
    }
}