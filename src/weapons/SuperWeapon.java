package weapons;
import entities.AssetSetter;
import entities.Entity;
import entities.Player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperWeapon {
    protected Rectangle solidArea;
    protected final int defaultRectangleX;
    protected final int defaultRectangleY;
    protected BufferedImage swingUp1, swingUp2, swingUp3, swingUp4;
    protected BufferedImage swingDown1, swingDown2, swingDown3, swingDown4;
    protected BufferedImage swingLeft1, swingLeft2, swingLeft3, swingLeft4;
    protected BufferedImage swingRight1, swingRight2, swingRight3, swingRight4;
    protected int damage;
    private static Entity[] NPCs;
    private static Entity[] Mobs;
    private static Player player;

    SuperWeapon(int defaultRectangleX, int defaultRectangleY) {
        this.defaultRectangleX = defaultRectangleX;
        this.defaultRectangleY = defaultRectangleY;
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
        return solidArea;
    }

    protected boolean hitEntityCollisionCheck() {
        return true;
    }


}
