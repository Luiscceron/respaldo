/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class PersonalLimpieza {
    private String nombre;
    private String areaAsignada;

    public PersonalLimpieza(String nombre, String areaAsignada) {
        this.nombre = nombre;
        this.areaAsignada = areaAsignada;
    }

    public String getNombre() { return nombre; }
    public String getAreaAsignada() { return areaAsignada; }
}