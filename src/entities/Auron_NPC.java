package entities;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Auron_NPC extends Entity {
    private BufferedImage right3, right4, left3, left4; // he has extra sprites i needa implement the animation for
    public Auron_NPC(){
        super(9,0 , TYPE_NPC);

        solidArea = new Rectangle(rectangleDefaultX, rectangleDefaultY, 30, 48);

        direction = "down";
        speed = 2;

        worldX = 21 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);
        worldY = 21 * GamePanel.TILE_SIZE - (GamePanel.TILE_SIZE / 2);

        hasDialogue = true;
        dialogue = new String[] {"Hey, is that you Renee?", "I haven't seen you in ages", "So I'm not alone", "Do you know where we are?"};


        try {

            up2 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronUp2.png")));
            up1 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronUp1.png")));
            down1 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronDown1.png")));
            down2 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronDown2.png")));
            right1 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronRight1.png")));
            right2 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronRight2.png")));
            right3 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronRight3.png")));
            right4 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronRight4.png")));
            left1 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronLeft1.png")));
            left2 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronLeft2.png")));
            left3 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronLeft3.png")));
            left4 = Utility.scale(ImageIO.read(new File("resources/sprites/AuronSprite/auronLeft4.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        currentSprite = 1;
    }
}
