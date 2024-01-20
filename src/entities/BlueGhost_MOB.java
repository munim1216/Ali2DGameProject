package entities;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BlueGhost_MOB extends Entity {

    public BlueGhost_MOB(int worldX, int worldY) {
        super(6,0, TYPE_MOB);

        solidArea = new Rectangle(rectangleDefaultX, rectangleDefaultY, 36, 48);

        direction = "down";
        speed = 2;
        health = 10;

        hpDrawn = GamePanel.TILE_SIZE;
        sizeOfOneHP = hpDrawn / health;

        this.worldX = worldX;
        this.worldY = worldY;

        hasDialogue = false;

        damage = 1;

        try {
            up1 = ImageIO.read(new File("resources/sprites/enemySprites/blueGhostBeleu.png"));
            up2 = up1;
            down1 = up1;
            down2 = up2;
            left1 = up1;
            left2 = up2;
            right1 = up1;
            right2 = up2;
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentSprite = 1;
    }
}
