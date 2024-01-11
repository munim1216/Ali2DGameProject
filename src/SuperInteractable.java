import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperInteractable {
    // IMAGE
    protected BufferedImage image;
    // TYPE
    protected String name;
    // PLAYER INTERACTION
    protected static Player player;
    protected boolean collision;
    protected boolean canPickUp;
    protected Rectangle solidArea;
    // LOCATION
    protected int worldX, worldY;
    // FOR STORING TO DRAW
    protected static SuperInteractable[] interactables = new SuperInteractable[100];
    protected static int nextSlot = 0;
    protected SuperInteractable() {
        interactables[nextSlot] = this;
        System.out.println(interactables[nextSlot].toString());
        nextSlot++;
    }
    protected boolean notOutOfBounds() {
        return (this.worldX + GamePanel.TILE_SIZE > player.getworldX() - Player.PLAYER_SCREEN_X ) && (this.worldX - GamePanel.TILE_SIZE  < player.getworldX() + Player.PLAYER_SCREEN_X) &&
                (this.worldY + GamePanel.TILE_SIZE  > player.getworldY() - Player.PLAYER_SCREEN_Y) && (this.worldY - GamePanel.TILE_SIZE  < player.getworldY() + Player.PLAYER_SCREEN_Y);
    }
    public static void draw(Graphics2D g2D) {
        for (int i = 0; interactables[i] != null; i++) {
            if (interactables[i].notOutOfBounds()) {
                g2D.drawImage(interactables[i].image, interactables[i].worldX, interactables[i].worldY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
            }
        }
    }
    public static void setPlayer(Player player) {
        SuperInteractable.player = player;
    }
}
