package Back;

import Vista.Vista;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Vista vista = new Vista();
            vista.setVisible(true);
        });
    }
}