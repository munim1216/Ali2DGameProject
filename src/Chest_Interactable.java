import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Chest_Interactable extends SuperInteractable {
    public Chest_Interactable(int worldX, int worldY) {
        super();
        collision = true;
        canPickUp = false;
        solidArea = new Rectangle(0, 18, 48, 30);

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = ImageIO.read(new File("src/sprites/Interactables/chest_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
