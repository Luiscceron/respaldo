/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class NodoLimpieza {
    public PersonalLimpieza dato;
    public NodoLimpieza siguiente;

    public NodoLimpieza(PersonalLimpieza dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}