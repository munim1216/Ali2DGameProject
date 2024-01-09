import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision;

    public Tile(BufferedImage image, Boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public BufferedImage getImage() {
        return image;
    }
}
