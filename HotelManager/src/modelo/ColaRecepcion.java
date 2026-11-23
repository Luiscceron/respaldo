/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class ColaRecepcion {
    private NodoReserva frente;
    private NodoReserva finalCola;
    private int tamano;

    public ColaRecepcion() {
        this.frente = null;
        this.finalCola = null;
        this.tamano = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    // Insertar un cliente al final de la fila (O(1))
    public void encolar(Reserva reserva) {
        NodoReserva nuevo = new NodoReserva(reserva);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            finalCola.siguiente = nuevo;
        }
        finalCola = nuevo;
        tamano++;
    }

    // Atender y retirar al primer cliente de la fila (O(1))
    public Reserva desencolar() {
        if (estaVacia()) {
            return null;
        }
        Reserva atendida = frente.dato;
        frente = frente.siguiente;
        if (frente == null) {
            finalCola = null;
        }
        tamano--;
        return atendida;
    }
    public NodoReserva getFrente() {
        return frente;
    }
}