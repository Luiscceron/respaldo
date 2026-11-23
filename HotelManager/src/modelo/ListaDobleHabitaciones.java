/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class ListaDobleHabitaciones {

    private NodoHabitacion cabeza;
    private NodoHabitacion cola;
    private NodoHabitacion actual; // Cursor para navegar en la vista (Carrusel)
    private int tamano;

    public ListaDobleHabitaciones() {
        this.cabeza = null;
        this.cola = null;
        this.actual = null;
        this.tamano = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamano() {
        return tamano;
    }

    // Insertar al final de la lista doblemente enlazada
    public boolean insertarHabitacion(Habitacion h) {
        if (h == null) {
            return false;
        }
        NodoHabitacion nuevo = new NodoHabitacion(h);
        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
            actual = nuevo; // El cursor inicia en la primera habitación cargada
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }
        tamano++;
        return true;
    }

    // Obtener la habitación donde está parado el usuario actualmente
    public Habitacion obtenerActual() {
        if (actual == null) {
            return null;
        }
        return actual.dato;
    }

    // Avanzar al siguiente nodo (Botón "Siguiente >")
    public Habitacion siguienteHabitacion() {
        if (actual != null && actual.siguiente != null) {
            actual = actual.siguiente;
        }
        return obtenerActual();
    }

    // Retroceder al nodo anterior (Botón "< Anterior")
    public Habitacion anteriorHabitacion() {
        if (actual != null && actual.anterior != null) {
            actual = actual.anterior;
        }
        return obtenerActual();
    }

    // Buscar una habitación por su número (ej. "201") - Complejidad O(n)
    public Habitacion buscarPorNumero(String numero) {
        NodoHabitacion aux = cabeza;
        while (aux != null) {
            if (aux.dato.getNumero().equalsIgnoreCase(numero)) {
                return aux.dato;
            }
            aux = aux.siguiente;
        }
        return null;
    }

    // Cambiar el estado ("Disponible" / "Ocupada") de una habitación específica
    public boolean actualizarEstado(String numero, String nuevoEstado) {
        Habitacion h = buscarPorNumero(numero);
        if (h != null) {
            h.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    // Necesario para que GestorArchivos pueda recorrer la lista desde el inicio y guardarla en .txt
    public NodoHabitacion getCabeza() {
        return cabeza;
    }
}