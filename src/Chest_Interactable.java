import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Chest_Interactable extends SuperInteractable {
    public Chest_Interactable(int worldX, int worldY) {
        super(0,18);
        collision = true;
        canPickUp = false;
        solidArea = new Rectangle(defaultRectangleX, defaultRectangleY, 48, 30);

        name = "Chest";
        neededToInteract = "Chest";

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = ImageIO.read(new File("src/sprites/Interactables/chest_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
