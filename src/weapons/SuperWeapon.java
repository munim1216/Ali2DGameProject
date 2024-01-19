package weapons;
import entities.AssetSetter;
import entities.Entity;
import entities.Player;

import java.awt.Rectangle;
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
    // weapon damage self explanatory
    protected int damage;
    // needs access to determine collision
    private static Entity[] NPCs;
    private static Entity[] Mobs;
    // for drawin
    private static Player player;

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

    public void update() {
        this.direction = player.getDirection();
        if (hitEntityCollisionCheck()) {

        }
    }

    protected boolean hitEntityCollisionCheck() {
        decideHitbox();
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
                Mobs[i].loseHP(this.damage);
                System.out.println("OUCHIE");// test code
            }

            Entity.reset(this, Mobs[i]);
        }
        return true;
    }

    private void decideHitbox() {
        switch (player.getDirection()) {
            case "up" -> solidArea = new Rectangle(upDefaultX, upDefaultY, width, height);
            case "down" -> solidArea = new Rectangle(downDefaultX,downDefaultY, width, height);
            case "left" -> solidArea = new Rectangle(leftDefaultX, leftDefaultY, height, width);
            case "right" -> solidArea = new Rectangle(rightDefaultX, rightDefaultY, height, width);

        }
    }

}
