import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Key_Interactable extends SuperInteractable {
    public Key_Interactable(int worldX, int worldY) {
        super();
        collision = false;
        canPickUp = true;
        solidArea = new Rectangle(9, 0, 30,45);

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = ImageIO.read(new File("src/sprites/Interactables/key_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
