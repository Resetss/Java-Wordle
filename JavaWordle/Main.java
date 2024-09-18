package JavaWordle;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
    	SwingUtilities.invokeLater(() -> new Wordle(5, 6));
    }
}