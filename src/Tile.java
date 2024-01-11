import java.awt.image.BufferedImage;

public class Tile {
    public final BufferedImage IMAGE;
    public final boolean COLLISION;
// implement boolean that checks for if it has an interactable that has collision enabled on top
    public Tile(BufferedImage image, Boolean collision) {
        IMAGE = image;
        COLLISION = collision;
    }

    public BufferedImage getImage() {
        return IMAGE;
    }

    public boolean isCOLLISION() {
        return COLLISION;
    }
}
