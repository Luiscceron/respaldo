/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class NodoHabitacion {

    public Habitacion dato;
    public NodoHabitacion siguiente;
    public NodoHabitacion anterior;

    public NodoHabitacion(Habitacion dato) {
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }
}