package entities;

import events.EventHandler;
import interactables.SuperInteractable;
import main.GamePanel;
import main.KeyHandler;
import main.UI;
import main.Utility;
import weapons.RedSword;
import weapons.SuperWeapon;

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
    // UI
    private UI UI;
    // EVENT HANDLING (so it can effect the player)
    private EventHandler eventHandler;
    // player iframes
    private boolean flashing;
    // player combat
    private int cooldown;
    private SuperWeapon weapon;
    private boolean swing;

    public Player(KeyHandler keyH, EventHandler eventHandler) {
        super(8,16, TYPE_PLAYER);

        this.eventHandler = eventHandler;

        swing = false;

        cooldown = 0;

        weapon = new RedSword();

        FULL_SHIELD = 2;
        HALF_SHIELD = 1;
        EMPTY_SHIELD = 0;

        flashing = false;

        health = 6;

        iframes = 0;
        invincible = false;

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
            up1 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeUp1.png")); // credit: poke
            up2 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeUp2.png")); // credit: poke
            down1 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeDown1.png"));
            down2 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeDown2.png"));
            left1 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeLeft1.png"));
            left2 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeLeft2.png"));
            right1 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeRight1.png"));
            right2 = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeRight2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUI(UI ui) {
        this.UI = ui;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public SuperWeapon getWeapon() {
        return weapon;
    }
    public SuperInteractable[] getItemList() {
        return itemList;
    }

    public void playerUpdate() {
        if (keyH.isFKeyPressed() && !npcCollisionCheck()) {
            if (gp.getGameState() == GamePanel.PLAY_STATE) {
                gp.startDialouge();
                UI.setNextDialogue(lastTouchingPlayer.speak());
                keyH.setFKeyPressed(false);
            } else if (gp.getGameState() == GamePanel.DIAL0GUE_STATE) {
                gp.unpause();
            }
        }
        if (GamePanel.gameState == GamePanel.PLAY_STATE) {
            if (keyH.isAMovementKeyPressed()) {
                spriteCounter++;
                interactCheck();
                eventHandlerCollisionCheck();
            }
            if (keyH.isWKeyPressed()) {
                direction = "up";
                if (collisionCheck()) {
                    worldY -= speed;
                }
            }
            if (keyH.isSKeyPressed()) {
                direction = "down";
                if (collisionCheck()) {
                    worldY += speed;
                }
            }
            if (keyH.isDKeyPressed()) {
                direction = "right";
                if (collisionCheck()) {
                    worldX += speed;
                }
            }
            if (keyH.isAKeyPressed()) {
                direction = "left";
                if (collisionCheck()) {
                    worldX -= speed;
                }
            }
            if (spriteCounter >= 10) {
                alternateSprite();
                spriteCounter = 0;
            }

            if (keyH.isACombatKeyPressed()) {
                cooldown--;
                if (cooldown <= 0) {
                    swing = true;
                    cooldown = 30;
                } else {
                    swing = false;
                }
            }

            if (keyH.isUpKeyPressed()) {
                direction = "up";
            }
            if (keyH.isDownKeyPressed()) {
                direction = "down";
            }
            if (keyH.isLeftKeyPressed()) {
                direction = "left";
            }
            if (keyH.isRightKeyPressed()) {
                direction = "right";
            }
        }


        if (invincible) {
            iframes++;
            if (iframes >= 60) {
                invincible = false;
                iframes = 0;
            }
        }
    }

    public boolean isSwing() {
        return swing;
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

        if (invincible && flashing) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            flashing = false;
        } else if (invincible) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            flashing = true;
        }

        g2D.drawImage(image, PLAYER_SCREEN_X, PLAYER_SCREEN_Y, null);
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
    public String getDirection() {
        return direction;
    }
    private void interactCheck() { // rework into interact check?
        for (int i = 0; SuperInteractable.getInScreen()[i] != null; i++) {
            if (SuperInteractable.getInScreen()[i].isCanInteract()) {
                this.solidArea.x = this.solidArea.x + this.worldX;
                this.solidArea.y = this.solidArea.y + this.worldY;

                SuperInteractable.getInScreen()[i].getSolidArea().x = SuperInteractable.getInScreen()[i].getSolidArea().x + SuperInteractable.getInScreen()[i].getWorldX();
                SuperInteractable.getInScreen()[i].getSolidArea().y = SuperInteractable.getInScreen()[i].getSolidArea().y + SuperInteractable.getInScreen()[i].getWorldY();

                switch (direction) {
                    case "up" -> this.solidArea.y -= speed;
                    case "down" -> this.solidArea.y += speed;
                    case "left" -> this.solidArea.x -= speed;
                    case "right" -> this.solidArea.x += speed;
                }
                if (SuperInteractable.getInScreen()[i].getSolidArea().intersects(this.solidArea)) {
                    reset(this, SuperInteractable.getInScreen()[i]);
                    interact(SuperInteractable.getInScreen()[i]);
                }
                reset(this, SuperInteractable.getInScreen()[i]);
            }
        }
    }
    protected void eventHandlerCollisionCheck() {
        // its actual x in the world, not the hitbox
        this.solidArea.x = this.solidArea.x + this.worldX;
        this.solidArea.y = this.solidArea.y + this.worldY;

        // actual x in world, not hitbox
        eventHandler.getSolidArea().x = eventHandler.getSolidArea().x + eventHandler.getWorldX();
        eventHandler.getSolidArea().y = eventHandler.getSolidArea().y + eventHandler.getWorldY();

        switch (direction) {
            case "up" -> this.solidArea.y -= speed;
            case "down" -> this.solidArea.y += speed;
            case "left" -> this.solidArea.x -= speed;
            case "right" -> this.solidArea.x += speed;
        }
        if (this.solidArea.intersects(eventHandler.getSolidArea())) {
            reset(this, eventHandler);
            eventHandler.tryForEvent();
        }
        reset(this, eventHandler);
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

    protected void reset(Entity entity, EventHandler eventHandler) {
        entity.solidArea.x = entity.rectangleDefaultX;
        entity.solidArea.y = entity.rectangleDefaultY;
        eventHandler.getSolidArea().x = eventHandler.rectangleDefaultX;
        eventHandler.getSolidArea().y = eventHandler.rectangleDefaultY;
    }
}
