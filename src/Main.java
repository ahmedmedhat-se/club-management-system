import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Entry point - starts with Login screen
 */
public class Main {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}