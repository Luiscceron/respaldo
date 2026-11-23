/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class ListaSimpleHistorial {
    
    // Este puntero SIEMPRE apunta al nodo fantasma, nunca es null
    private  final NodoReserva cabeza; 
    private int tamano;

    public ListaSimpleHistorial() {
        // Inicialización del nodo cabecera (Dummy Node) con dato null
        this.cabeza = new NodoReserva(null); 
        this.tamano = 0;
    }

    // Retorna el primer nodo real (útil para guardar en el archivo de texto)
    public NodoReserva getPrimerNodoReal() {
        return cabeza.siguiente;
    }

    // Inserta al final del historial (O(n))
    public void insertarReserva(Reserva reserva) {
        NodoReserva nuevo = new NodoReserva(reserva);
        NodoReserva actual = cabeza;
        
        // Recorremos hasta el último nodo. Gracias al nodo fantasma, 
        // "actual" nunca es null y no necesitamos un 'if(cabeza == null)'
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }
        actual.siguiente = nuevo;
        tamano++;
    }

    // Busca una reserva confirmada por su ID (O(n))
    public Reserva buscarReserva(String idReserva) {
        NodoReserva actual = cabeza.siguiente; // Saltamos el nodo fantasma
        
        while (actual != null) {
            if (actual.dato.getIdReserva().equals(idReserva)) {
                return actual.dato;
            }
            actual = actual.siguiente;
        }
        return null; // No encontrada
    }
}