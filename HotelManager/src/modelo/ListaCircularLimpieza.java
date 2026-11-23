/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class ListaCircularLimpieza {
    private NodoLimpieza ultimo;
    private NodoLimpieza actual;

    public ListaCircularLimpieza() {
        this.ultimo = null;
        this.actual = null;
    }

    public void insertar(PersonalLimpieza p) {
        NodoLimpieza nuevo = new NodoLimpieza(p);
        if (ultimo == null) {
            ultimo = nuevo;
            ultimo.siguiente = nuevo; // Apunta a sí mismo (ciclo)
            actual = nuevo;
        } else {
            nuevo.siguiente = ultimo.siguiente;
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
        }
    }

    public PersonalLimpieza obtenerActual() {
        return (actual != null) ? actual.dato : null;
    }

    // Avanza infinitamente sin llegar nunca a null
    public PersonalLimpieza rotarSiguienteTurno() {
        if (actual != null) {
            actual = actual.siguiente;
        }
        return obtenerActual();
    }

    public NodoLimpieza getInicio() {
        return (ultimo != null) ? ultimo.siguiente : null;
    }
}