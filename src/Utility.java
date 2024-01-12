import java.awt.*;
import java.awt.image.BufferedImage;

public class Utility {
    public static Graphics2D g2D;
    private Utility(){}

    public static void reorderArr(SuperInteractable[] arr) {
        for(int i=0; i < arr.length - 1; ++i) {
            if(arr[i] == null) {
                arr[i] = arr[i+1];
                arr[i+1] = null;
            }
        } // credit https://stackoverflow.com/questions/25787831/how-to-move-a-null-to-the-end-of-array-in-java-using-for-loop
    }

    public static BufferedImage scale(BufferedImage image) { // still editiing
        BufferedImage scaledImage = new BufferedImage(GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, image.getType());
        Graphics2D g2D = scaledImage.createGraphics();
        g2D.drawImage(image, 0,0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
        return scaledImage;
    }
}
