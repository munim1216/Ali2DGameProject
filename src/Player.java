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
    public Player(KeyHandler keyH) {
        super(8,16);

        worldX = 17 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);
        worldY = 17 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);
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

    public int getworldX() {
        return worldX;
    }

    public int getworldY() {
        return worldY;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public SuperInteractable[] getItemList() {
        return itemList;
    }

    public void playerUpdate() {
        SuperInteractable interactable;

        if (keyH.isKeyPressed()) {
            spriteCounter++;
        }
        if (keyH.isWKeyPressed()) {
            direction = "up";
            interactCheck();
            if (collisionCheck()){
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
    private void alternateSprite() {
        if (currentSprite == 1) {
            currentSprite = 2;
        } else {
            currentSprite = 1;
        }
    }

    private void interactCheck() { // rework into interact check?
        boolean stop = false;
        for (int i = 0; SuperInteractable.inScreen[i] != null; i++) {
            if (SuperInteractable.inScreen[i].canInteract) {
                this.solidArea.x = this.solidArea.x + this.worldX;
                this.solidArea.y = this.solidArea.y + this.worldY;

                SuperInteractable.inScreen[i].getSolidArea().x = SuperInteractable.inScreen[i].getSolidArea().x + SuperInteractable.inScreen[i].getWorldX();
                SuperInteractable.inScreen[i].getSolidArea().y = SuperInteractable.inScreen[i].getSolidArea().y + SuperInteractable.inScreen[i].getWorldY();

                switch (direction) {
                    case "up" -> {
                        // moving up is closer to the 0 for y
                        this.solidArea.y -= speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            interact(SuperInteractable.inScreen[i]);
                            stop = true;
                        }
                    }
                    case "down" -> {
                        this.solidArea.y += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            interact(SuperInteractable.inScreen[i]);
                            stop = true;
                        }
                    }
                    case "left" -> {
                        this.solidArea.x -= speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            interact(SuperInteractable.inScreen[i]);
                            stop = true;
                        }
                    }
                    case "right" -> {
                        this.solidArea.x += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            interact(SuperInteractable.inScreen[i]);
                            stop = true;
                        }

                    }
                }
                reset(this, SuperInteractable.inScreen[i]);
                if (stop) {
                    break;
                }
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

                    for (SuperInteractable item : itemList) {
                        if (item != null) {
                            System.out.println("I STILL HAVE: " + item);
                        }
                    }
                } else {
                    System.out.println("false");
                }
            }
        }
    }



    private void pickUp(SuperInteractable item) {
        if (item == null || !item.canPickUp) {
            return;
        }
        itemList[nextItem] = item;
        if (item.getName().equals("Key")) {
            numKeys++;
        }
        for (int i = 0; itemList[i] != null; i++) {
            System.out.println("This is currently in my inv: " + itemList[i]);
        }

        System.out.println();
        nextItem++;
        SuperInteractable.pickUp(item);
    }
}
