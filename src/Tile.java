import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collisison;

    public Tile(BufferedImage image, Boolean collisison) {
        this.image = image;
        this.collisison = collisison;
    }

    public BufferedImage getImage() {
        return image;
    }
}
