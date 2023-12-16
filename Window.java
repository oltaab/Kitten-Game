import javax.swing.*;
import java.awt.*;

/**
 * The Window class represents the main game window for the BreakThrough game.
 * It extends the BaseWindow class and contains the game logic and user interface elements.
 */
public class Window extends BaseWindow {
    private final int size;
    private final Model model;
    private final JLabel label;
    private final MainWindow mainWindow;

    /**
     * Constructs a new Window object with the specified size and parent MainWindow.
     *
     * @param size       The size of the game board (number of rows and columns).
     * @param mainWindow The parent MainWindow that manages this Window.
     */
    public Window(int size, MainWindow mainWindow) {
        this.size = size;
        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);
        model = new Model(size);

        JPanel top = new JPanel();

        label = new JLabel();
        updateLabelText();

        JButton newGameButton = new JButton();
        newGameButton.setText("New Game");
        newGameButton.addActionListener(e -> newGame());

        top.add(label);
        top.add(newGameButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Adds a button with appropriate icons to the game board panel.
     *
     * @param panel The panel to which the button is added.
     * @param i     The row index of the button.
     * @param j     The column index of the button.
     */
    private void addButton(JPanel panel, final int i, final int j){
        JButton button = new JButton();

        if (i < 2) {
            button.setIcon(new ImageIcon(getScaledImage(
                    "/Users/oltabytyci/Desktop/BThrough/kittens/kitten2.png", 50, 50)));
            panel.add(button);
        } else if (i >= size - 2) {
            button.setIcon(new ImageIcon(getScaledImage(
                    "/Users/oltabytyci/Desktop/BThrough/kittens/kitten1.png", 50, 50)));
            panel.add(button);
        }
        panel.add(button);
        button.addActionListener(e -> {

            int a = model.getPrevRow();
            int b = model.getPrevColumn();
            boolean flag = model.getFlag();
            boolean clickAble = false;
            if (a >= 0 && b >= 0){
                clickAble = model.canIMoveWithMyCurrentPosition(i,j,a,b);

            }

            if (button.getIcon() != null && flag && model.isMoveable(i,j)) {

                button.setIcon(null);
                model.setFlag(false);
                model.setPrevRow(i);
                model.setPrevColumn(j);


            } else if ( (button.getIcon() != null && !flag && model.isDiagonal(a,b,i,j) && model.tryingToStepDown(i,j) )) {
                button.setIcon(null);

                Player newValue = model.step2(a, b);

                ImageIcon icon = new ImageIcon(newValue.getIMAGE_IMAGE_PATH());
                button.setIcon(icon);
                Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));

                model.setActualPlayer(newValue,i,j);

                updateLabelText();
                model.freeThis(a,b);
                model.setFlag(true);
                model.setPrevRow(i);
                model.setPrevColumn(j);

            } else if (button.getIcon() == null && clickAble && !flag && model.tryingToStepDown(i,j)){
                Player newValue = model.step(i, j);

                ImageIcon icon = new ImageIcon(newValue.getIMAGE_IMAGE_PATH());
                button.setIcon(
                        icon);
                Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
                updateLabelText();
                model.freeThis(model.getPrevRow(), model.getPrevColumn());
                model.setFlag(true);
                model.setPrevRow(i);
                model.setPrevColumn(j);

                Player winner = model.stepWin(i,j);

                if(winner == null){
                    showGameOverMessage(newValue);
                    mainWindow.removeGameWindow(this);
                    this.dispose();
                    mainWindow.startNewGame(size);
                }

            }

            Player winner = model.findWinner();
            if (winner != Player.NOBODY) {
                showGameOverMessage(winner);
                mainWindow.removeGameWindow(this);
                this.dispose();
                mainWindow.startNewGame(size);
            } else {
            }
        });
    }

    /**
     * Displays a game over message dialog with the name of the winning player.
     *
     * @param winner The winning player.
     */
    private void showGameOverMessage(Player winner) {
        JOptionPane.showMessageDialog(this, "Game over: Winner: " + winner.getName());
    }

    /**
     * Starts a new game by creating a new Window instance and disposing of the current window.
     */
    private void newGame() {
        mainWindow.removeGameWindow(this);
        this.dispose();
        mainWindow.startNewGame(size);
    }

    /**
     * Updates the label text to display the name of the current player.
     */
    private void updateLabelText() {
        label.setText("Current player: " + model.getActualPlayer().getName());
    }

    /**
     * Scales an image to the specified width and height.
     *
     * @param imagePath The path to the image file.
     * @param width     The desired width of the scaled image.
     * @param height    The desired height of the scaled image.
     * @return The scaled Image object.
     */
    private Image getScaledImage(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
