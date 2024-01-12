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
    public Player(KeyHandler keyH) {
        super(8,16);

        worldX = 25 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);
        worldY = 25 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);
        speed = 3;

        direction = "down";
        currentSprite = 1;
        spriteCounter = 0;

        solidArea = new Rectangle(rectangleDefaultX, rectangleDefaultY, 32, 32);

        this.keyH = keyH;

        itemList = new SuperInteractable[100];

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

    public void playerUpdate() {
        SuperInteractable interactable;

        if (keyH.isKeyPressed()) {
            spriteCounter++;
        }
        if (keyH.isWKeyPressed()) {
            direction = "up";
            if (collisionCheck()){
                pickupCheck();
                worldY -= speed;
            }
        }
        if (keyH.isSKeyPressed()) {
            direction = "down";
            if (collisionCheck()) {
                pickupCheck();
                worldY += speed;
            }
        }
        if (keyH.isDKeyPressed()) {
            direction = "right";
            if (collisionCheck()) {
                pickupCheck();
                worldX += speed;
            }
        }
        if (keyH.isAKeyPressed()) {
            direction = "left";
            if (collisionCheck()) {
                pickupCheck();
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

        g2D.drawImage(image, PLAYER_SCREEN_X, PLAYER_SCREEN_Y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        g2D.draw(solidArea);
    }
    private void alternateSprite() {
        if (currentSprite == 1) {
            currentSprite = 2;
        } else {
            currentSprite = 1;
        }
    }

    private void pickupCheck() {
        for (int i = 0; SuperInteractable.inScreen[i] != null; i++) {
            this.solidArea.x = this.solidArea.x + this.worldX;
            this.solidArea.y = this.solidArea.y + this.worldY;

            SuperInteractable.inScreen[i].getSolidArea().x = SuperInteractable.inScreen[i].getSolidArea().x + SuperInteractable.inScreen[i].getWorldX();
            SuperInteractable.inScreen[i].getSolidArea().y = SuperInteractable.inScreen[i].getSolidArea().y + SuperInteractable.inScreen[i].getWorldY();

            // switch is only checked if can pickup is true
            if (SuperInteractable.inScreen[i].isCanPickUp()) {
                switch (direction) {
                    case "up" -> {
                        // moving up is closer to the 0 for y
                        this.solidArea.y -= speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            pickUp(SuperInteractable.inScreen[i]);
                        }
                    }
                    case "down" -> {
                        this.solidArea.y += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            pickUp(SuperInteractable.inScreen[i]);
                        }
                    }
                    case "left" -> {
                        this.solidArea.x -= speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            pickUp(SuperInteractable.inScreen[i]);
                        }
                    }
                    case "right" -> {
                        this.solidArea.x += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, SuperInteractable.inScreen[i]);
                            pickUp(SuperInteractable.inScreen[i]);
                        }
                    }
                }
            }
            reset(this, SuperInteractable.inScreen[i]);
        }
    }
    private void pickUp(SuperInteractable item) { // sosmethign wrong
        if (item == null) {
            return;
        }
        itemList[nextItem] = item;
        System.out.println("This is now in my inv: " + itemList[nextItem]);
        System.out.println("This is not supposed to be in my inv: " + itemList[nextItem + 1]);

        nextItem++;
        item.canPickUp = false; // im even doing forbidden things to try to fix it.
        item.pickUp();
    }
}
