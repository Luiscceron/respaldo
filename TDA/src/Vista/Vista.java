/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author LUISC
 */

import Back.Coche;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Vista extends JFrame {
    private ArrayList<Coche> listaCoches;
    private JComboBox<String> comboCoches;
    private JLabel lblEstado;
    private JLabel lblVelocidad;

    public Vista() {
        listaCoches = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestion de Coches - Laboratorio TAD");
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelCreacion = new JPanel(new FlowLayout());
        panelCreacion.setBorder(BorderFactory.createTitledBorder("Crear Coches"));

        JLabel lblCantidad = new JLabel("Numero de coches a crear:");
        JTextField txtCantidad = new JTextField(5);
        JButton btnCrear = new JButton("Crear Coches");

        panelCreacion.add(lblCantidad);
        panelCreacion.add(txtCantidad);
        panelCreacion.add(btnCrear);

        JPanel panelControl = new JPanel(new GridLayout(6, 1, 5, 5));
        panelControl.setBorder(BorderFactory.createTitledBorder("Control del Coche Seleccionado"));

        JPanel panelCombo = new JPanel(new FlowLayout());
        panelCombo.add(new JLabel("Seleccionar Coche:"));
        comboCoches = new JComboBox<>();
        panelCombo.add(comboCoches);

        lblEstado = new JLabel("Estado: Detenido", SwingConstants.CENTER);
        lblVelocidad = new JLabel("Velocidad actual: 0 km/h", SwingConstants.CENTER);
        lblVelocidad.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panelBotonesAccion = new JPanel(new FlowLayout());
        JButton btnArrancar = new JButton("Arrancar");
        JButton btnParar = new JButton("Parar");
        panelBotonesAccion.add(btnArrancar);
        panelBotonesAccion.add(btnParar);

        JPanel panelAcelerarFrenar = new JPanel(new FlowLayout());
        JButton btnAcelerar = new JButton("Acelerar");
        JButton btnFrenar = new JButton("Frenar");
        panelAcelerarFrenar.add(btnAcelerar);
        panelAcelerarFrenar.add(btnFrenar);

        panelControl.add(panelCombo);
        panelControl.add(lblEstado);
        panelControl.add(lblVelocidad);
        panelControl.add(panelBotonesAccion);
        panelControl.add(panelAcelerarFrenar);

        add(panelCreacion, BorderLayout.NORTH);
        add(panelControl, BorderLayout.CENTER);

        btnCrear.addActionListener(e -> {
            try {
                int n = Integer.parseInt(txtCantidad.getText().trim());
                if (n <= 0) {
                    JOptionPane.showMessageDialog(this, "Por favor ingrese un numero positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                listaCoches.clear();
                comboCoches.removeAllItems();

                for (int i = 1; i <= n; i++) {
                    listaCoches.add(new Coche());
                    comboCoches.addItem("Coche #" + i);
                }

                JOptionPane.showMessageDialog(this, "Se han creado " + n + " coches con exito.");
                actualizarVista();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un numero entero valido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        comboCoches.addActionListener(e -> actualizarVista());

        btnArrancar.addActionListener(e -> {
            Coche c = getCocheSeleccionado();
            if (c != null) {
                c.arrancar();
                actualizarVista();
            }
        });

        btnParar.addActionListener(e -> {
            Coche c = getCocheSeleccionado();
            if (c != null) {
                c.parar();
                actualizarVista();
            }
        });

        btnAcelerar.addActionListener(e -> {
            Coche c = getCocheSeleccionado();
            if (c == null) return;

            if (!c.estaEnMarcha()) {
                JOptionPane.showMessageDialog(this, "Primero debe arrancar el coche.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String input = JOptionPane.showInputDialog(this, "Ingrese la velocidad a acelerar (km/h):");
            if (input != null && !input.isEmpty()) {
                try {
                    int inc = Integer.parseInt(input);
                    c.acelerar(inc);
                    actualizarVista();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un numero valido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnFrenar.addActionListener(e -> {
            Coche c = getCocheSeleccionado();
            if (c == null) return;

            if (!c.estaEnMarcha()) {
                JOptionPane.showMessageDialog(this, "El coche no esta en marcha.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String input = JOptionPane.showInputDialog(this, "Ingrese la velocidad a frenar (km/h):");
            if (input != null && !input.isEmpty()) {
                try {
                    int dec = Integer.parseInt(input);
                    c.frenar(dec);
                    actualizarVista();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un numero valido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private Coche getCocheSeleccionado() {
        int idx = comboCoches.getSelectedIndex();
        if (idx >= 0 && idx < listaCoches.size()) {
            return listaCoches.get(idx);
        }
        return null;
    }

    private void actualizarVista() {
        Coche c = getCocheSeleccionado();
        if (c != null) {
            lblEstado.setText("Estado: " + (c.estaEnMarcha() ? "En marcha" : "Detenido"));
            lblVelocidad.setText("Velocidad actual: " + c.verVelocidad() + " km/h");
        } else {
            lblEstado.setText("Estado: No hay coches creados");
            lblVelocidad.setText("Velocidad actual: 0 km/h");
        }
    }
}