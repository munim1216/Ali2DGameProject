import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperInteractable {
    protected BufferedImage image;
    protected String name;
    protected boolean collision;
    protected boolean canPickUp;
    protected int worldX, worldY;
    protected Rectangle solidArea;
    protected static SuperInteractable[] interactables = new SuperInteractable[100];
    protected static int nextSlot = 0;
    protected SuperInteractable() {
        interactables[nextSlot] = this;
        nextSlot++;
    }
    protected boolean inScreen() {
        return false;
    }
    public static void draw(Graphics2D g2D) {
        for (int i = 0; interactables[i] != null; i++) {
            if (interactables[i].inScreen()) {
                g2D.drawImage(interactables[i].image, interactables[i].worldX, interactables[i].worldY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
        }
    }

}
