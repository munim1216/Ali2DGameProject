import java.awt.*;

public class Chest_Interactable extends SuperInteractable {
    public Chest_Interactable(int worldX, int worldY) {
        super();
        collision = true;
        canPickUp = false;
        solidArea = new Rectangle(0, 18, 48, 30);
    }
}
