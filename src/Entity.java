import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    // world map && possible tiles
    protected static int[][] mapTileNum;
    protected static Tile[] tiles;
    // position in overall world map
    protected int worldX;
    protected int worldY;
    protected int speed;
    // sprites
    protected BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    protected String direction;
    protected int currentSprite;
    protected int spriteCounter;
    // hitbox (i hate)
    protected Rectangle solidArea;

    public static void setMapTileNumAndTiles(int[][] mapTileNum, Tile[] tiles) {
        Entity.mapTileNum = mapTileNum;
        Entity.tiles = tiles;
    }
    protected boolean collisionCheck() {
        // creates variables used to determine which rows and col will be checked
        int leftWorldX = worldX + solidArea.x - speed;
        int rightWorldX = worldX + solidArea.x + solidArea.width + speed;
        int topWorldY = worldY + solidArea.y - speed;
        int bottomWorldY = worldY + solidArea.y + solidArea.height + speed;

        int leftCol = leftWorldX / GamePanel.TILE_SIZE;
        int rightCol = rightWorldX / GamePanel.TILE_SIZE;
        int topRow = topWorldY / GamePanel.TILE_SIZE;
        int bottomRow = bottomWorldY / GamePanel.TILE_SIZE;

        int tileCheck1, tileCheck2;
        switch (direction) {
            case "up" -> {
                tileCheck1 = mapTileNum[leftCol][topRow];
                tileCheck2 = mapTileNum[rightCol][topRow];
            }
            case "down" -> {
                tileCheck1 = mapTileNum[leftCol][bottomRow];
                tileCheck2 = mapTileNum[rightCol][bottomRow];
            }
            case "right" -> {
                tileCheck1 = mapTileNum[rightCol][topRow];
                tileCheck2 = mapTileNum[rightCol][bottomRow];
            }
            case "left" -> {
                tileCheck1 = mapTileNum[leftCol][topRow];
                tileCheck2 = mapTileNum[leftCol][bottomRow];
            }
            default -> {
                tileCheck1 = -1;
                tileCheck2 = -1;
            }
        }

        if (tiles[tileCheck1].isCOLLISION() || tiles[tileCheck2].isCOLLISION()) {
            return false;
        } else {
            return true;
        }
    }
}
