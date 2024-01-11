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
    protected boolean collisionOn = false;
    // combat
    protected int health;
    protected int defense;
    protected double multipler = 1.0;
    public Entity(){}

    public static void setMapTileNumAndTiles(int[][] mapTileNum, Tile[] tiles) {
        Entity.mapTileNum = mapTileNum;
        Entity.tiles = tiles;
    }
    protected boolean collisionCheck() {
        return tileCollisionCheck() && interactableCollisionCheck();
    }
    protected boolean tileCollisionCheck() {
        // creates variables used to determine which rows and col will be checked
        int leftWorldX = worldX + solidArea.x;
        int rightWorldX = worldX + solidArea.x + solidArea.width;
        int topWorldY = worldY + solidArea.y;
        int bottomWorldY = worldY + solidArea.y + solidArea.height;

        int leftCol = leftWorldX / GamePanel.TILE_SIZE;
        int rightCol = rightWorldX / GamePanel.TILE_SIZE;
        int topRow = topWorldY / GamePanel.TILE_SIZE;
        int bottomRow = bottomWorldY / GamePanel.TILE_SIZE;

        int tileCheck1, tileCheck2;
        switch (direction) {
            case "up" -> {
                topRow = (topWorldY - speed) / GamePanel.TILE_SIZE;
                tileCheck1 = mapTileNum[leftCol][topRow];
                tileCheck2 = mapTileNum[rightCol][topRow];
            }
            case "down" -> {
                bottomRow = (bottomWorldY + speed) / GamePanel.TILE_SIZE;
                tileCheck1 = mapTileNum[leftCol][bottomRow];
                tileCheck2 = mapTileNum[rightCol][bottomRow];
            }
            case "right" -> {
                rightCol = (rightWorldX + speed) / GamePanel.TILE_SIZE;
                tileCheck1 = mapTileNum[rightCol][topRow];
                tileCheck2 = mapTileNum[rightCol][bottomRow];
            }
            case "left" -> {
                leftCol = (leftWorldX - speed) / GamePanel.TILE_SIZE;
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

    protected boolean interactableCollisionCheck() { // needs urgent fixing
        for (int i = 0; SuperInteractable.inScreen[i] != null; i++) {
            System.out.println(SuperInteractable.inScreen[i].toString());
            if (SuperInteractable.inScreen[i].isCollision()) {
                switch (direction) {
                    case "up" -> {
                        this.solidArea.y -= speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            this.solidArea.y += speed;
                            return false;
                        }
                        this.solidArea.y += speed;
                    }
                    case "down" -> {
                        this.solidArea.y += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            this.solidArea.y -= speed;
                            return false;
                        }
                        this.solidArea.y -= speed;
                    }
                    case "left" -> {
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            this.solidArea.x += speed;
                            return false;
                        }
                        this.solidArea.x += speed;
                    }
                    case "right" -> {
                        this.solidArea.x += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            this.solidArea.x -= speed;                             System.out.println("ODDITY");
                            return false;
                        }
                        this.solidArea.x -= speed;
                    }
                }
            }
        }
        return true;
    }
}
