import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WingedBoot_Interactable extends SuperInteractable {
    public WingedBoot_Interactable(int worldX, int worldY) {
        super(3, 3);

        solidArea = new Rectangle(defaultRectangleX, defaultRectangleY, 39, 39);
        collision = false;
        canPickUp = true;

        name = "WingedBoot";

        this.worldX = worldX;
        this.worldY = worldY;

        try {
            image = ImageIO.read(new File("src/sprites/Interactables/winged_boot_1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
