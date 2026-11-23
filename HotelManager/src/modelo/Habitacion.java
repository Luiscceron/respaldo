/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ferna
 */
public class Habitacion {

    private String numero;      // Ej: "101", "202"
    private String tipo;        // Ej: "Estandar", "Doble", "Suite"
    private double precioNoche; // Ej: 120000.0
    private String estado;      // "Disponible" o "Ocupada"
    private String rutaImagen;  // Ej: "/imagenes/estandar.jpg"

    public Habitacion(String numero, String tipo, double precioNoche, String estado, String rutaImagen) {
        this.numero = numero;
        this.tipo = tipo;
        this.precioNoche = precioNoche;
        this.estado = estado;
        this.rutaImagen = rutaImagen;
    }

    // Getters y Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    // Método auxiliar para saber rápido si se puede reservar
    public boolean estaDisponible() {
        return "Disponible".equalsIgnoreCase(this.estado);
    }

    // Formato listo para guardar en habitaciones.txt con delimitador ::
    public String toFormatoArchivo() {
        return numero + "::" + tipo + "::" + precioNoche + "::" + estado + "::" + rutaImagen;
    }
    public String getImagen() {
    return rutaImagen;
}
}