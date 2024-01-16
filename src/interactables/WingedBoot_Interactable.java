package interactables;

import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WingedBoot_Interactable extends SuperInteractable {
    public WingedBoot_Interactable(int worldX, int worldY) {
        super(3, 3);

        solidArea = new Rectangle(DEFAULT_RECTANGLE_X, DEFAULT_RECTANGLE_Y, 39, 39);
        collision = false;
        canPickUp = true;

        name = "WingedBoot";

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = Utility.scale(ImageIO.read(new File("resources/sprites/Interactables/winged_boot_1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
