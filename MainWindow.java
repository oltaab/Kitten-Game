import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 * The MainWindow class represents the main window of the BreakThrough game application.
 * It provides options to start new games with different board sizes and manages the list
 * of active game windows.
 */
public class MainWindow extends BaseWindow {
    private List<Window> gameWindows = new ArrayList<>();
    public List<Window> getWindowList(){
        return gameWindows;
    }
    public MainWindow(){
        JButton small = new JButton();
        small.setText("6 x 6 ");
        small.addActionListener(getActionListener(6));

        JButton medium = new JButton();
        medium.setText("8 x 8");
        medium.addActionListener(getActionListener(8));

        JButton large = new JButton();
        large.setText(" 10 x 10");
        large.addActionListener(getActionListener(10));

        JPanel buttons = new JPanel();
        buttons.add(small);
        buttons.add(medium);
        buttons.add(large);

        getContentPane().setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(small);
        buttonPanel.add(medium);
        buttonPanel.add(large);

        ImageIcon imageIcon = new ImageIcon("kittens/Main3.png");
        JLabel pictureLabel = new JLabel(imageIcon);

        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(pictureLabel, BorderLayout.CENTER);
    }

    /**
     * Returns an ActionListener that starts a new game with the specified board size when a button is clicked.
     *
     * @param size The size of the game board (number of rows and columns).
     * @return An ActionListener for starting a new game with the given board size.
     */
    private ActionListener getActionListener(final int size){
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Window window = new Window(size,MainWindow.this);
                window.setVisible(true);
                gameWindows.add(window);
            }
        };
    }

    /**
     * Starts a new game by creating a new Window instance.
     *
     * @param size The size of the game board.
     */
    public void startNewGame(int size) {
        Window window = new Window(size, this);
        window.setVisible(true);
        gameWindows.add(window);
    }

    /**
     * Removes a game window from the list of active windows.
     *
     * @param window The window to remove.
     */
    public void removeGameWindow(Window window) {
        gameWindows.remove(window);
    }
}
