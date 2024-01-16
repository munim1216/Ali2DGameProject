package interactables;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import main.Utility;

public class Key_Interactable extends SuperInteractable {
    public Key_Interactable(int worldX, int worldY) {
        super(9, 0);
        collision = false;
        canPickUp = true;
        solidArea = new Rectangle(DEFAULT_RECTANGLE_X, DEFAULT_RECTANGLE_Y, 30,45);

        name = "Key";

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = Utility.scale(ImageIO.read(new File("resources/sprites/Interactables/key_1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
