package interactables;

import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Chest_Interactable extends SuperInteractable {
    public Chest_Interactable(int worldX, int worldY) {
        super(0,18);
        collision = true;
        canPickUp = false;
        solidArea = new Rectangle(DEFAULT_RECTANGLE_X, DEFAULT_RECTANGLE_Y, 48, 30);

        name = "Chest";
        neededToInteract = "Key";

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = Utility.scale(ImageIO.read(new File("resources/sprites/Interactables/chest_1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
