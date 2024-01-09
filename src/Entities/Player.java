package Entities;

import Game.GamePanel;
import Game.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player {
    // PLAYER POSITION
    private int playerX;
    private int playerY;
    private int playerSpeed;

    // PLAYER INPUT
    private KeyHandler keyH;
    // PLAYER SPRITE
    private BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    private String direction;

    private int currentSprite;
    private int spriteCounter;
    public Player(KeyHandler keyH) {
        playerX = 100;
        playerY = 100;
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

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public void playerUpdate() {
        if (keyH.isKeyPressed()) {
            spriteCounter++;
        }
        if (keyH.isWKeyPressed()) {
            playerY -= playerSpeed;
            direction = "up";
        }
        if (keyH.isSKeyPressed()) {
            playerY += playerSpeed;
            direction = "down";
        }
        if (keyH.isDKeyPressed()) {
            playerX += playerSpeed;
            direction = "right";
        }
        if (keyH.isAKeyPressed()) {
            playerX -= playerSpeed;
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

        g2D.drawImage(image, playerX, playerY, GamePanel.TILESIZE, GamePanel.TILESIZE, null);
    }

    private void alternateSprite() {
        if (currentSprite == 1) {
            currentSprite = 2;
        } else {
            currentSprite = 1;
        }
    }
}
