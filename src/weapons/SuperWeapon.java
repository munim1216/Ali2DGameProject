package weapons;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class SuperWeapon {
    protected Rectangle solidArea;
    protected final int defaultRectangleX;
    protected final int defaultRectangleY;
    protected BufferedImage swingUp, swingDown, swingLeft, swingRight;
    protected int damage;

    SuperWeapon(int defaultRectangleX, int defaultRectangleY) {
        this.defaultRectangleX = defaultRectangleX;
        this.defaultRectangleY = defaultRectangleY;
    }

    public int getDamage() {
        return damage;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }
}
