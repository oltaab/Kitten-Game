import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private String IMAGE_IMAGE_PATH;
    public static final Player PLAYER1 = new Player("Kitten 1", "kittens/kitten2.png");
    public static final Player PLAYER2 = new Player("Kitten 2", "kittens/kitten1.png");
    public static final Player NOBODY = new Player("NOBODY","kittens/blankButton.jpg");

    private Player(String name, String IMAGE_IMAGE_PATH) {
        this.name = name;
        this.IMAGE_IMAGE_PATH = IMAGE_IMAGE_PATH;;
    }

    public String getName() {
        return name;
    }

    public String getIMAGE_IMAGE_PATH() {
        return IMAGE_IMAGE_PATH;
    }

    @Override
    public String toString() {
        return name;
    }
}
