
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Back;




public class Coche {

     
    private int velocidad;
    private boolean enMarcha;

     
    public Coche() {
        velocidad = 0;
        enMarcha = false;
    }

     

    public void arrancar() {
        if (!enMarcha) {
            enMarcha = true;
            System.out.println("El coche ha arrancado.");
        } else {
            System.out.println("El coche ya está en marcha.");
        }
    }

    public void parar() {
        if (enMarcha) {
            velocidad = 0;
            enMarcha = false;
            System.out.println("El coche se ha detenido.");
        } else {
            System.out.println("El coche ya está detenido.");
        }
    }

    public void acelerar(int incremento) {
        if (enMarcha && incremento > 0) {
            velocidad += incremento;
            System.out.println("Acelerando...");
        } else if (!enMarcha) {
            System.out.println("Primero debe arrancar el coche.");
        } else {
            System.out.println("El incremento debe ser positivo.");
        }
    }

    public void frenar(int decremento) {
        if (enMarcha && decremento > 0) {
            velocidad -= decremento;

            if (velocidad < 0) {
                velocidad = 0;
            }

            System.out.println("Frenando...");
        } else if (!enMarcha) {
            System.out.println("El coche no está en marcha.");
        } else {
            System.out.println("El decremento debe ser positivo.");
        }
    }

    public int verVelocidad() {
        return velocidad;
    }

    public boolean estaEnMarcha() {
        return enMarcha;
    }
}
