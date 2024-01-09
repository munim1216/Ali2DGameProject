import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player {
    // PLAYER POSITION
    private int worldX;
    private int worldY;
    private int playerSpeed;

    // PLAYER INPUT
    private KeyHandler keyH;
    // PLAYER SPRITE
    private BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    private String direction;

    private int currentSprite;
    private int spriteCounter;
    // CAMERA SETTINGS
    public static final int PLAYER_SCREEN_X = GamePanel.SCREEN_WIDTH / 2;;
    public static final int PLAYER_SCREEN_Y = GamePanel.SCREEN_HEIGHT / 2;
    public Player(KeyHandler keyH) {
        worldX = 25 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);
        worldY = 25 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);
        playerSpeed = 3;

        direction = "down";
        currentSprite = 1;
        spriteCounter = 0;

        this.keyH = keyH;

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeUp1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeUp2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeDown1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeDown2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeLeft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeLeft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeRight1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("ReneeSprite/reneeRight2.png"));

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
        if (keyH.isKeyPressed()) {
            spriteCounter++;
        }
        if (keyH.isWKeyPressed()) {
            worldY -= playerSpeed;
            direction = "up";
        }
        if (keyH.isSKeyPressed()) {
            worldY += playerSpeed;
            direction = "down";
        }
        if (keyH.isDKeyPressed()) {
            worldX += playerSpeed;
            direction = "right";
        }
        if (keyH.isAKeyPressed()) {
            worldX -= playerSpeed;
            direction = "left";
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
    }

    private void alternateSprite() {
        if (currentSprite == 1) {
            currentSprite = 2;
        } else {
            currentSprite = 1;
        }
    }
}
