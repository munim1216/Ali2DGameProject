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
    protected static SuperInteractable[] inScreen = new SuperInteractable[100];
    // LOCATION
    protected int worldX, worldY;
    protected final int defaultRectangleX, defaultRectangleY;
    // FOR STORING TO DRAW
    protected static SuperInteractable[] interactables = new SuperInteractable[100];
    protected static int nextSlot = 0;
    protected int idx;
    protected boolean draw = true;

    protected SuperInteractable(int defaultRectangleX, int defaultRectangleY) {
        this.defaultRectangleX = defaultRectangleX;
        this.defaultRectangleY = defaultRectangleY;
        this.idx = nextSlot;
        interactables[nextSlot] = this;
        nextSlot++;
    }
    protected boolean notOutOfBounds() {
        return (this.worldX + GamePanel.TILE_SIZE > player.getworldX() - Player.PLAYER_SCREEN_X ) && (this.worldX - GamePanel.TILE_SIZE  < player.getworldX() + Player.PLAYER_SCREEN_X) &&
                (this.worldY + GamePanel.TILE_SIZE  > player.getworldY() - Player.PLAYER_SCREEN_Y) && (this.worldY - GamePanel.TILE_SIZE  < player.getworldY() + Player.PLAYER_SCREEN_Y);
    }
    protected Rectangle getSolidArea() {
        return solidArea;
    }
    protected int getWorldX() {
        return worldX;
    }

    protected int getWorldY() {
        return worldY;
    }

    public void pickUp() { // somethhing horribly wrong
        interactables[idx] = null;
        interactables = ArrayUtil.reorderArr(interactables);
        System.out.println();
        System.out.println();
        int i = 0;
        for (SuperInteractable element : interactables) {
            if (element != null) {
                System.out.println("ARRAY NUM :" + i + ", whats remains added: " + element);
                i++;
            }
        }
    }
    public boolean isCollision() {
        return collision;
    }
    public boolean isCanPickUp() {
        return canPickUp;
    }

    public static void draw(Graphics2D g2D) {
        for (int i = 0; inScreen[i] != null; i++) {
                int screenX = inScreen[i].worldX - player.getworldX() + Player.PLAYER_SCREEN_X;
                int screenY = inScreen[i].worldY - player.getworldY() + Player.PLAYER_SCREEN_Y;
                g2D.drawImage(inScreen[i].image, screenX, screenY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
                //System.out.println(interactables[i]);

        }
    }

    public static void interactablesInFrame() {
        int nextAdded = 0;
        for (int i = 0; interactables[i] != null; i++) {
            if (interactables[i].notOutOfBounds()) {
                inScreen[nextAdded] = interactables[i];
                nextAdded++;
            }
        }
    }
    public static void setPlayer(Player player) {
        SuperInteractable.player = player;
    }
    public static SuperInteractable[] getInScreen() {
        return inScreen;
    }

}
