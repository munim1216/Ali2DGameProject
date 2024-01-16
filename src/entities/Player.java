package entities;

import interactables.SuperInteractable;
import main.GamePanel;
import main.KeyHandler;
import main.UI;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player extends Entity {
    // PLAYER INPUT
    private KeyHandler keyH;
    // CAMERA SETTINGS
    public static final int PLAYER_SCREEN_X = GamePanel.SCREEN_WIDTH / 2 - (GamePanel.TILE_SIZE / 2);
    public static final int PLAYER_SCREEN_Y = GamePanel.SCREEN_HEIGHT / 2 - (GamePanel.TILE_SIZE / 2);
    // PLAYER INVENTORY
    private SuperInteractable[] itemList;
    private int nextItem;
    private SuperInteractable addToItemList;
    private int numKeys;
    // PLAYER HEALTH
    public final int FULL_SHIELD, HALF_SHIELD, EMPTY_SHIELD;
    private int currentHealth;
    // UI
    private UI UI;
    public Player(KeyHandler keyH) {
        super(8,16);

        FULL_SHIELD = 2;
        HALF_SHIELD = 1;
        EMPTY_SHIELD = 0;

        currentHealth = 6;

        worldX = 10 * GamePanel.TILE_SIZE;
        worldY = 4 * GamePanel.TILE_SIZE;
        speed = 3;

        direction = "down";
        currentSprite = 1;
        spriteCounter = 0;

        solidArea = new Rectangle(rectangleDefaultX, rectangleDefaultY, 32, 32);

        this.keyH = keyH;

        itemList = new SuperInteractable[100];
        numKeys = 0;

        try {
            up1 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeUp1.png")); // credit: poke
            up2 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeUp2.png")); // credit: poke
            down1 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeDown1.png"));
            down2 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeDown2.png"));
            left1 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeLeft1.png"));
            left2 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeLeft2.png"));
            right1 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeRight1.png"));
            right2 = ImageIO.read(new File("src/sprites/ReneeSprite/reneeRight2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUI(UI ui) {
        this.UI = ui;
    }
    public int getworldX() {
        return worldX;
    }

    public int getworldY() {
        return worldY;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public SuperInteractable[] getItemList() {
        return itemList;
    }

    public void playerUpdate() {
        if (keyH.isFKeyPressed() && !entityCollisionCheck()) {
            if (gp.getGameState() == GamePanel.PLAYSTATE) {
                gp.startDialouge();
                UI.setNextDialogue(lastTouchingPlayer.speak());
                keyH.setFKeyPressed(false);
            } else if (gp.getGameState() == GamePanel.DIAL0GUESTATE) {
                gp.unpause();
            }
        }
        if (GamePanel.gameState == GamePanel.PLAYSTATE) {
            if (keyH.isKeyPressed()) {
                spriteCounter++;
            }
            if (keyH.isWKeyPressed()) {
                direction = "up";
                interactCheck();
                if (collisionCheck()) {
                    worldY -= speed;
                }
            }
            if (keyH.isSKeyPressed()) {
                direction = "down";
                interactCheck();
                if (collisionCheck()) {
                    worldY += speed;
                }
            }
            if (keyH.isDKeyPressed()) {
                direction = "right";
                interactCheck();
                if (collisionCheck()) {
                    worldX += speed;
                }
            }
            if (keyH.isAKeyPressed()) {
                direction = "left";
                interactCheck();
                if (collisionCheck()) {
                    worldX -= speed;
                }
            }
            if (spriteCounter >= 10) {
                alternateSprite();
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2D) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (currentSprite == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
            }
            case "down" -> {
                if (currentSprite == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
            }
            case "left" -> {
                if (currentSprite == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
            }
            case "right" -> {
                if (currentSprite == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
            }
        }

        g2D.drawImage(image, PLAYER_SCREEN_X, PLAYER_SCREEN_Y, null);
    }

    private void interactCheck() { // rework into interact check?
        boolean stop = false;
        for (int i = 0; SuperInteractable.getInScreen()[i] != null; i++) {
            if (SuperInteractable.getInScreen()[i].isCanInteract()) {
                this.solidArea.x = this.solidArea.x + this.worldX;
                this.solidArea.y = this.solidArea.y + this.worldY;

                SuperInteractable.getInScreen()[i].getSolidArea().x = SuperInteractable.getInScreen()[i].getSolidArea().x + SuperInteractable.getInScreen()[i].getWorldX();
                SuperInteractable.getInScreen()[i].getSolidArea().y = SuperInteractable.getInScreen()[i].getSolidArea().y + SuperInteractable.getInScreen()[i].getWorldY();

                switch (direction) {
                    case "up" -> {
                        // moving up is closer to the 0 for y
                        this.solidArea.y -= speed;
                        if (SuperInteractable.getInScreen()[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.getInScreen()[i]);
                            interact(SuperInteractable.getInScreen()[i]);
                            stop = true;
                        }
                    }
                    case "down" -> {
                        this.solidArea.y += speed;
                        if (SuperInteractable.getInScreen()[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.getInScreen()[i]);
                            interact(SuperInteractable.getInScreen()[i]);
                            stop = true;
                        }
                    }
                    case "left" -> {
                        this.solidArea.x -= speed;
                        if (SuperInteractable.getInScreen()[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.getInScreen()[i]);
                            interact(SuperInteractable.getInScreen()[i]);
                            stop = true;
                        }
                    }
                    case "right" -> {
                        this.solidArea.x += speed;
                        if (SuperInteractable.getInScreen()[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.getInScreen()[i]);
                            interact(SuperInteractable.getInScreen()[i]);
                            stop = true;
                        }

                    }
                }
                reset(this, SuperInteractable.getInScreen()[i]);
            }
        }
    }

    private void interact(SuperInteractable interactable) {
        switch (interactable.getName()) {
            case "Key", "WingedBoot" -> pickUp(interactable);
            case "Chest", "Door" -> {
                if (SuperInteractable.useItem(this, interactable)) {
                    numKeys--;
                    Utility.reorderArr(itemList);
                    int newNextNum = 0;
                    while(itemList[newNextNum] != null) {
                        newNextNum++;
                    }
                    nextItem = newNextNum;
                }
            }
        }
    }



    private void pickUp(SuperInteractable item) {
        if (item == null || !item.isCanPickUp()) {
            return;
        }
        itemList[nextItem] = item;
        if (item.getName().equals("Key")) {
            numKeys++;
        }

        nextItem++;
        SuperInteractable.pickUp(item);
    }
}