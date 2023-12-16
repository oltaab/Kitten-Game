/**
 * The BreakThrough class is the entry point for the BreakThrough game application.
 * It creates an instance of the MainWindow class and makes it visible to start the game.
 */
public class BreakThrough {

    /**
     * The main method of the BreakThrough class, which serves as the entry point of the application.
     */

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
}
