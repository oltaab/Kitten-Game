import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The BaseWindow class serves as the base class for all windows in the Break-Through game application.
 * It provides common functionality such as setting the window title, size, and handling window close events.
 */

public class BaseWindow extends JFrame {
    public BaseWindow() {
        setTitle("Break-Through");
        setSize(800, 900);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Change to do nothing on close

        // Add window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmAndClose();
            }
        });
    }

    /**
     * Displays a confirmation dialog when the user attempts to close the window.
     */
    private void confirmAndClose() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the game?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            dispose();
        }
    }
}

