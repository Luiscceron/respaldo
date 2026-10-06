/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import back.BusquedaRecursiva;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 *
 * @author LUISC
 */
public class VentanaPrincipal extends JFrame {
    
    private BusquedaRecursiva backend;
    private int[] vectorActual;

    public VentanaPrincipal() {
        backend = new BusquedaRecursiva(15);
        vectorActual = backend.retornarVector(); 

        setTitle("Proyecto: Busqueda Recursiva");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar barraMenu = new JMenuBar();
        JMenu menuMetodos = new JMenu("Seleccionar Metodo de Busqueda");

        // Sin tildes para evitar errores de NetBeans
        String[] metodos = {
            "1. Secuencial", "2. Secuencial por indice", "3. Binaria", 
            "4. Binaria con limites", "5. Todas las ocurrencias", 
            "6. Minimo", "7. Maximo", "8. Posicion", "9. Ambos extremos"
        };

        for (String nombre : metodos) {
            JMenuItem item = new JMenuItem(nombre);
            item.addActionListener(e -> abrirVentanaHija(nombre));
            menuMetodos.add(item);
        }

        barraMenu.add(menuMetodos);
        setJMenuBar(barraMenu);
        
        add(new JLabel("Seleccione un metodo en el menu superior para comenzar", SwingConstants.CENTER));
    }

    private void abrirVentanaHija(String metodo) {
        JDialog dialog = new JDialog(this, "Metodo: " + metodo, true);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(4, 1, 10, 10));

        int[] vectorTrabajo = vectorActual.clone();
        if (metodo.contains("Binaria")) {
            Arrays.sort(vectorTrabajo);
        }

        JLabel lblVector = new JLabel("Vector: " + Arrays.toString(vectorTrabajo), SwingConstants.CENTER);
        dialog.add(lblVector);

        JPanel panelInput = new JPanel();
        panelInput.add(new JLabel("Ingrese numero a buscar: "));
        JTextField txtValor = new JTextField(10);
        panelInput.add(txtValor);
        
        if (metodo.contains("Minimo") || metodo.contains("Maximo")) {
            txtValor.setVisible(false);
            panelInput.getComponent(0).setVisible(false);
        }
        dialog.add(panelInput);

        JButton btnBuscar = new JButton("Buscar");
        dialog.add(btnBuscar);

        JLabel lblResultado = new JLabel("Resultado: Esperando...", SwingConstants.CENTER);
        lblResultado.setFont(new Font("Arial", Font.BOLD, 14));
        dialog.add(lblResultado);

        btnBuscar.addActionListener(e -> {
            try {
                int valor = 0;
                if (!metodo.contains("Minimo") && !metodo.contains("Maximo")) {
                    valor = Integer.parseInt(txtValor.getText());
                }
                
                int n = vectorTrabajo.length;
                String res = "";

                switch (metodo) {
                    case "1. Secuencial":
                        boolean hallado = BusquedaRecursiva.secuencial(vectorTrabajo, 0, valor);
                        res = hallado ? "Numero encontrado!" : "Numero no encontrado.";
                        break;
                    case "2. Secuencial por indice":
                        int pos = backend.buscarIndice(vectorTrabajo, 0, valor);
                        res = (pos != -1) ? "Encontrado en el indice: " + pos : "No encontrado.";
                        break;
                    case "3. Binaria":
                        int posBS = backend.buscarBinariaSimple(vectorTrabajo, valor);
                        res = (posBS != -1) ? "Encontrado en el indice: " + posBS : "No encontrado.";
                        break;
                    case "4. Binaria con limites":
                        int posB = backend.buscarBinaria(vectorTrabajo, 0, n - 1, valor);
                        res = (posB != -1) ? "Encontrado en el indice: " + posB : "No encontrado.";
                        break;
                    case "5. Todas las ocurrencias":
                        BusquedaRecursiva.buscarOcurrencias(vectorTrabajo, 0, valor);
                        res = "Ejecutado. Revisa la consola para ver posiciones.";
                        break;
                    case "6. Minimo":
                        res = "El minimo es: " + backend.buscarMinimo(vectorTrabajo, 0, vectorTrabajo[0]);
                        break;
                    case "7. Maximo":
                        res = "El maximo es: " + backend.buscarMaximo(vectorTrabajo, 0, vectorTrabajo[0]);
                        break;
                    case "8. Posicion":
                        int ultPos = backend.buscarUltimaPosicion(vectorTrabajo, n - 1, valor);
                        res = (ultPos != -1) ? "Ultima aparicion en indice: " + ultPos : "No encontrado.";
                        break;
                    case "9. Ambos extremos":
                        int posExt = backend.buscarAmbosExtremos(vectorTrabajo, 0, n - 1, valor);
                        res = (posExt != -1) ? "Encontrado en el indice: " + posExt : "No encontrado.";
                        break;
                }
                lblResultado.setText("Resultado: " + res);
            } catch (NumberFormatException ex) {
                lblResultado.setText("Error: Ingrese un numero valido.");
            }
        });

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}