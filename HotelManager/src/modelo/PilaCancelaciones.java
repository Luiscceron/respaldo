/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class PilaCancelaciones {
    private NodoReserva tope;
    private int tamano;

    public PilaCancelaciones() {
        this.tope = null;
        this.tamano = 0;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    // Guardar una cancelación en la cima (O(1))
    public void apilar(Reserva reserva) {
        NodoReserva nuevo = new NodoReserva(reserva);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamano++;
    }

    // Recuperar la última cancelación para deshacer el error (O(1))
    public Reserva desapilar() {
        if (estaVacia()) {
            return null;
        }
        Reserva recuperada = tope.dato;
        tope = tope.siguiente;
        tamano--;
        return recuperada;
    }
    public NodoReserva getTope() {
        return tope;
    }
}