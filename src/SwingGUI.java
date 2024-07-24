// Import the Swing Utilities
import javax.swing.SwingUtilities;

// Create the Graphical UserInfo Interface
public class SwingGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PageLayout gui = new PageLayout();
            gui.setVisible(true);
        });
    }
}