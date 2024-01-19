package weapons;
import entities.AssetSetter;
import entities.Entity;
import entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperWeapon {
    // the sword's hit box
    protected Rectangle solidArea;
    // sword's hit box is different depending on the direction which is stupid!!!!!
    protected final int downDefaultX;
    protected final int downDefaultY;
    protected final int upDefaultX;
    protected final int upDefaultY;
    protected final int leftDefaultX;
    protected final int leftDefaultY;
    protected final int rightDefaultX;
    protected final int rightDefaultY;
    protected final int width;
    protected final int height;

    // the actual sprites!!!!
    protected String direction;
    protected BufferedImage swingUp1, swingUp2, swingUp3, swingUp4;
    protected BufferedImage swingDown1, swingDown2, swingDown3, swingDown4;
    protected BufferedImage swingLeft1, swingLeft2, swingLeft3, swingLeft4;
    protected BufferedImage swingRight1, swingRight2, swingRight3, swingRight4;
    protected int spriteNum;
    // weapon damage self-explanatory
    protected int damage;
    // needs access to determine collision
    private static Entity[] NPCs;
    private static Entity[] Mobs;
    // for drawin
    private static Player player;
    private boolean midSwing;
    private int spriteCounter;
    // to not repeatedly hit the same mob in the same swing animation
    private Entity[] mobsHitInSwing;
    private int nextMob;

    SuperWeapon(int downDefaultX, int downDefaultY, int upDefaultX, int upDefaultY, int leftDefaultX, int leftDefaultY, int rightDefaultX, int rightDefaultY, int width, int height) {
        this.downDefaultX = downDefaultX;
        this.downDefaultY = downDefaultY;
        this.upDefaultX = upDefaultX;
        this.upDefaultY = upDefaultY;
        this.leftDefaultX = leftDefaultX;
        this.leftDefaultY = leftDefaultY;
        this.rightDefaultX = rightDefaultX;
        this.rightDefaultY = rightDefaultY;
        this.width = width;
        this.height = height;
        spriteNum = 0;
        nextMob = 0;
        mobsHitInSwing = new Entity[3];
    }
    public static void setPlayerAndArrays(Player player) {
        SuperWeapon.player = player;
        SuperWeapon.NPCs = AssetSetter.getNPCs();
        SuperWeapon.Mobs = AssetSetter.getMobs();
    }
    public int getDamage() {
        return damage;
    }

    public Rectangle getSolidArea() {
        return null;
    }

    public boolean isMidSwing() {
        return midSwing;
    }

    public void update() {
        this.direction = player.getDirection();
        hitEntityCollisionCheck();
    }

    protected void hitEntityCollisionCheck() {
        decideHitbox();
        boolean hitInSwing = false;
        for (int i = 0; Mobs[i] != null; i++) {

            // its actual x in the world, not the hitbox
            this.solidArea.x = this.solidArea.x + player.getWorldX();
            this.solidArea.y = this.solidArea.y + player.getWorldY();

            switch (player.getDirection()) {
                case "up" -> this.solidArea.y -= height;
                case "down" -> this.solidArea.y += height;
                case "left" -> this.solidArea.x -= width;
                case "right" -> this.solidArea.x += width;
            }

            // actual x in world, not hitbox
            Mobs[i].getSolidArea().x = Mobs[i].getSolidArea().x + Mobs[i].getWorldX();
            Mobs[i].getSolidArea().y = Mobs[i].getSolidArea().y + Mobs[i].getWorldY();

            // after adjusting where the hit boxes of both the entity and other entity, it tests if the rectangles that represent their hit boxes
            // would intersect after moving in the direction they're trying to move in. (thanks to the handy method intersect from rectangle class)
            if (Mobs[i].getSolidArea().intersects(this.solidArea)) {
                Entity.reset(this, Mobs[i]);
                for (Entity entity : mobsHitInSwing) {
                    if (entity == Mobs[i]) {
                        hitInSwing = true;
                        break;
                    }
                }
                if (!hitInSwing) {
                    Mobs[i].loseHP(this.damage);
                    mobsHitInSwing[nextMob] = Mobs[i];
                    System.out.println("OUCHIE");// test code
                }
            }

            hitInSwing = false;
            Entity.reset(this, Mobs[i]);
        }
    }

    public void decideHitbox() {
        switch (player.getDirection()) {
            case "up" -> solidArea = new Rectangle(upDefaultX, upDefaultY, width, height);
            case "down" -> solidArea = new Rectangle(downDefaultX,downDefaultY, width, height);
            case "left" -> solidArea = new Rectangle(leftDefaultX, leftDefaultY, height, width);
            case "right" -> solidArea = new Rectangle(rightDefaultX, rightDefaultY, height, width);

        }
    }

    public void draw(Graphics2D g2D) {
        spriteCounter++;
        if (spriteCounter > 2) {
            spriteNum++;
            spriteCounter = 0;
        }
        switch (spriteNum) {
            case 0 -> drawSpriteOne(g2D);
            case 1 -> drawSpriteTwo(g2D);
            case 2 -> drawSpriteThree(g2D);
            case 3 -> drawSpriteFour(g2D);
        }
    }

    private void drawSpriteOne(Graphics2D g2D) {
        BufferedImage image = null;
        int x = Player.PLAYER_SCREEN_X;
        int y = Player.PLAYER_SCREEN_Y;
        midSwing = true;

        switch (player.getDirection()) {
            case "up" -> {
                y -= height;
                image = swingUp1;
            }
            case "down" -> {
                y += height;
                image = swingDown1;
            }
            case "left" -> {
                x -= width;
                image = swingLeft1;
            }
            case "right" -> {
                x += width;
                image = swingRight1;
            }
        }

        g2D.drawImage(image, x, y, null);
    }

    private void drawSpriteTwo(Graphics2D g2D) {
        BufferedImage image = null;
        int x = Player.PLAYER_SCREEN_X;
        int y = Player.PLAYER_SCREEN_Y;

        switch (player.getDirection()) {
            case "up" -> {
                y -= height;
                image = swingUp2;
            }
            case "down" -> {
                y += height;
                image = swingDown2;
            }
            case "left" -> {
                x -= width;
                image = swingLeft2;
            }
            case "right" -> {
                x += width;
                image = swingRight2;
            }
        }

        g2D.drawImage(image, x, y, null);
    }

    private void drawSpriteThree(Graphics2D g2D) {
        BufferedImage image = null;
        int x = Player.PLAYER_SCREEN_X;
        int y = Player.PLAYER_SCREEN_Y;

        switch (player.getDirection()) {
            case "up" -> {
                y -= height;
                image = swingUp3;
            }
            case "down" -> {
                y += height;
                image = swingDown3;
            }
            case "left" -> {
                x -= width;
                image = swingLeft3;
            }
            case "right" -> {
                x += width;
                image = swingRight3;
            }
        }

        g2D.drawImage(image, x, y, null);
    }

    private void drawSpriteFour(Graphics2D g2D) {
        BufferedImage image = null;
        int x = Player.PLAYER_SCREEN_X;
        int y = Player.PLAYER_SCREEN_Y;

        switch (player.getDirection()) {
            case "up" -> {
                y -= height;
                image = swingUp4;
            }
            case "down" -> {
                y += height;
                image = swingDown4;
            }
            case "left" -> {
                x -= width;
                image = swingLeft4;
            }
            case "right" -> {
                x += width;
                image = swingRight4;
            }
        }

        g2D.drawImage(image, x, y, null);
        resetSwing();
    }

    private void resetSwing() {
        spriteNum = 0;
        mobsHitInSwing = new Entity[3];
        midSwing = false;
        spriteCounter = 0;
        nextMob = 0;
    }
}
