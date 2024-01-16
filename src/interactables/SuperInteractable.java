package interactables;

import main.GamePanel;
import entities.Player;
import main.Utility;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperInteractable {
    // IMAGE
    protected BufferedImage image;
    // TYPE
    protected String name;
    protected String neededToInteract;
    // PLAYER INTERACTION
    protected static Player player;
    protected boolean collision;
    protected boolean canPickUp;
    protected Rectangle solidArea;
    protected static SuperInteractable[] inScreen = new SuperInteractable[100];
    // LOCATION
    protected int worldX, worldY;
    public final int DEFAULT_RECTANGLE_X;
    public final int DEFAULT_RECTANGLE_Y;
    // FOR STORING TO DRAW
    protected static SuperInteractable[] interactables = new SuperInteractable[100];
    protected static int nextSlot = 0;
    protected int idx;
    protected boolean canInteract = true;

    protected SuperInteractable(int defaultRectangleX, int defaultRectangleY) {
        this.DEFAULT_RECTANGLE_X = defaultRectangleX;
        this.DEFAULT_RECTANGLE_Y = defaultRectangleY;
        this.idx = nextSlot;
        interactables[nextSlot] = this;
        nextSlot++;
    }
    // make a new overloaded constructor so u can actually remove things from the interactable array, and keep them on the player inv array (for stuff other than key)

    public Rectangle getSolidArea() {
        return solidArea;
    }
    public int getWorldX() {
        return worldX;
    }

    public boolean isCanInteract() {
        return canInteract;
    }

    public int getWorldY() {
        return worldY;
    }
    public String getName() {
        return name;
    }
    protected String getNeededItem() {
        return neededToInteract;
    }
    public static void pickUp(SuperInteractable interactable) {
        interactable.canPickUp = false;

        interactables[interactable.idx] = null;
        Utility.reorderArr(interactables);
        for (int i = 0; interactables[i] != null; i++) {
            interactables[i].idx = i;
        }

        GamePanel.SE.setClip(0);
        GamePanel.SE.play();
    }
    public boolean isCollision() {
        return collision;
    }
    public boolean isCanPickUp() {
        return canPickUp;
    }

    public static void draw(Graphics2D g2D) {
        for (int i = 0; interactables[i] != null; i++) {
            if (Utility.notOutOfBounds(interactables[i], player)) {
                int screenX = interactables[i].worldX - player.getworldX() + Player.PLAYER_SCREEN_X;
                int screenY = interactables[i].worldY - player.getworldY() + Player.PLAYER_SCREEN_Y;
                g2D.drawImage(interactables[i].image, screenX, screenY, null);
            }
        }
    }

    public static void interactablesInFrame() {
        int nextAdded = 0;
        for (int i = 0; interactables[i] != null; i++) {
            if (Utility.notOutOfBounds(interactables[i], player)) {
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

    public static boolean useItem(Player player, SuperInteractable interactable) {
        if (!interactable.collision) {
            return false;
        }
        boolean hasNeededItem = false;
        SuperInteractable[] itemList = player.getItemList();
        for (int i = 0; itemList[i] != null && !hasNeededItem; i++) {
            if (itemList[i].getName().equals(interactable.getNeededItem())) {
                hasNeededItem = true;
                itemList[i] = null;
            }
        }
        // DEBUGGING CODE
        System.out.println(itemList[0]);
        System.out.println(itemList[1]);
        System.out.println(itemList[2]);

        if (hasNeededItem) { // turn into a switch when more interactables are implemented
            interactable.collision = false;
            interactable.interacted();
        }
        return hasNeededItem;
    }
    public void interacted() {
        canInteract = false;
    }
}
