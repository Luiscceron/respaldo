/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back;

import java.util.Random;

/**
 *
 * @author LUISC
 */
public class BusquedaRecursiva {
    private int[] vector;
    private int logitud;
    
    public int[] retornarVector() {
        Random rd = new Random();
        for (int i = 0; i < vector.length; i++) {
            vector[i] = rd.nextInt(20);
        }
        return vector;
    }

    public BusquedaRecursiva(int logitud) {
        this.vector = new int[logitud];
    }
    
    public void imprimirVector(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.println("[" + i + "] -> " + vector[i]);
        }
    }
   
 //---Busqueda secuencial   
    
    // CORRECCION: Se agrego 'public' para que la vista pueda acceder al metodo
    public static boolean secuencial(int[] vector, int indice, int valor) {
        if (indice == vector.length) {
            return false;
        }
        if (vector[indice] == valor) {
            return true;
        }
        return secuencial(vector, indice + 1, valor);
    }
   //-------------------------------
    
    //------------------- Busqueda por indice
   public int buscarIndice(int[] vector, int indice, int valor) {
        if (indice >= vector.length) {
            return -1;
        }
        if (vector[indice] == valor) {
            return indice;
        }
        return buscarIndice(vector, indice + 1, valor);
    }
 
    //---------------------------------------
   //---------------------Busqueda de todas las ocurrencias
   
   // CORRECCION: Se agrego 'public' para que la vista pueda acceder al metodo
   public static void buscarOcurrencias(int[] vector, int indice, int valor) {
        if (indice == vector.length) {
            return;
        }
        if (vector[indice] == valor) {
            System.out.println("Encontrado en posicion: " + indice);
        }
        buscarOcurrencias(vector, indice + 1, valor);
    }
   //--------------------------------
   
   //-----------------------Busqueda Binaria Recursiva
   
   public int buscarBinaria(int[] vector, int inicio, int fin, int valor) {
        if (inicio > fin) {
            return -1;
        }
       int medio = (inicio + fin) / 2;
        if (vector[medio] == valor) {
            return medio;
        }
        if (valor < vector[medio]) {
            return buscarBinaria(vector, inicio, medio - 1, valor);
        }
        return buscarBinaria(vector, medio + 1, fin, valor);
    }
//---------------------------------------  
    
 // --- METODOS AGREGADOS (FALTANTES EN EL ORIGINAL) ---

    // 3. Busqueda binaria recursiva
    public int buscarBinariaSimple(int[] vector, int valor) {
        return buscarBinaria(vector, 0, vector.length - 1, valor);
    }

    // 6. Busqueda recursiva del minimo
    public int buscarMinimo(int[] vector, int indice, int minActual) {
        if (indice == vector.length) {
            return minActual;
        }
        if (vector[indice] < minActual) {
            minActual = vector[indice];
        }
        return buscarMinimo(vector, indice + 1, minActual);
    }

    // 7. Busqueda recursiva del maximo
    public int buscarMaximo(int[] vector, int indice, int maxActual) {
        if (indice == vector.length) {
            return maxActual;
        }
        if (vector[indice] > maxActual) {
            maxActual = vector[indice];
        }
        return buscarMaximo(vector, indice + 1, maxActual);
    }

    // 8. Busqueda recursiva de posicion 
    public int buscarUltimaPosicion(int[] vector, int indice, int valor) {
        if (indice < 0) {
            return -1; 
        }
        if (vector[indice] == valor) {
            return indice; 
        }
        return buscarUltimaPosicion(vector, indice - 1, valor);
    }

    // 9. Busqueda recursiva en ambos extremos
    public int buscarAmbosExtremos(int[] vector, int inicio, int fin, int valor) {
        if (inicio > fin) {
            return -1;
        }
        if (vector[inicio] == valor) {
            return inicio;
        }
        if (vector[fin] == valor) {
            return fin;
        }
        return buscarAmbosExtremos(vector, inicio + 1, fin - 1, valor);
    }
// ----------------------------------------------------

 //-------------------------Metodo Main
    
    public static void main(String[] args) {

        BusquedaRecursiva busqueda = new BusquedaRecursiva(10);
        int[] vector = busqueda.retornarVector();
        busqueda.imprimirVector(vector);
        int valor = 12;

        //--Busqueda secuencial
        if (secuencial(vector, 0, valor)) {
            System.out.println("Elemento encontrado");
        } else {
            System.out.println("Elemento no encontrado");
        }
        
        //---Busqueda por indice
        int posicion = busqueda.buscarIndice(vector, 0, 3);

        if (posicion != -1) {
            System.out.println("Elemento encontrado en la posicion: " + posicion);
        } else {
            System.out.println("Elemento no encontrado");
        }
        
        //--Busqueda por ocurrencias
         BusquedaRecursiva.buscarOcurrencias(vector, 0, 14);
        
        //------Busqueda binaria 
         int pBinaria = busqueda.buscarBinaria(vector, 0, vector.length - 1, valor);
                
        if (pBinaria != -1) {
            System.out.println("Elemento encontrado en la posicion: " + pBinaria);
        } else {
            System.out.println("Elemento no encontrado");
        }
    }
}