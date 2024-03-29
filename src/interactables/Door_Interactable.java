package interactables;

import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Door_Interactable extends SuperInteractable {
    public Door_Interactable(int worldX, int worldY) {
        super(0, 0);
        collision = true;
        canPickUp = false;
        solidArea = new Rectangle(DEFAULT_RECTANGLE_X, DEFAULT_RECTANGLE_Y, 48, 48);

        name = "Door";
        neededToInteract = "Key";

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = Utility.scale(ImageIO.read(new File("resources/sprites/Interactables/door_1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
