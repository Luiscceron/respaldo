/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class Reserva {

    private String idReserva;
    private String nombreCliente;
    private String numeroHabitacion;
    private double precioTotal;
    private String fecha;

    public Reserva(String idReserva, String nombreCliente, String numeroHabitacion, double precioTotal, String fecha) {
        this.idReserva = idReserva;
        this.nombreCliente = nombreCliente;
        this.numeroHabitacion = numeroHabitacion;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
    }

    public String getIdReserva() { return idReserva; }
    public void setIdReserva(String idReserva) { this.idReserva = idReserva; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNumeroHabitacion() { return numeroHabitacion; }
    public void setNumeroHabitacion(String numeroHabitacion) { this.numeroHabitacion = numeroHabitacion; }

    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    // Formato seguro para reservas.txt
    public String toFormatoArchivo() {
        return idReserva + "::" + nombreCliente + "::" + numeroHabitacion + "::" + precioTotal + "::" + fecha;
    }
}