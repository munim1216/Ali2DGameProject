import java.awt.image.BufferedImage;

public class Tile {
    public final BufferedImage IMAGE;
    public final boolean COLLISION;

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
