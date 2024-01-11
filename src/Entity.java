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
    protected int rectangleDefaultX;
    protected int rectangleDefaultY;
    protected boolean collisionOn = false;
    // combat
    protected int health;
    protected int defense;
    protected double multipler = 1.0;
    public Entity(int rectangleDefaultX, int rectangleDefaultY){
        this.rectangleDefaultX = rectangleDefaultX;
        this.rectangleDefaultY = rectangleDefaultY;
    }

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
        SuperInteractable[] inScreen = SuperInteractable.getInScreen();
        for (int i = 0; inScreen[i] != null; i++) {
            this.solidArea.x = this.solidArea.x + this.worldX;
            this.solidArea.y = this.solidArea.y + this.worldY;

            inScreen[i].getSolidArea().x = inScreen[i].getSolidArea().x + inScreen[i].getWorldX();
            inScreen[i].getSolidArea().y = inScreen[i].getSolidArea().y + inScreen[i].getWorldY();


            System.out.println(SuperInteractable.inScreen[i].toString());
            if (SuperInteractable.inScreen[i].isCollision()) {
                switch (direction) {
                    case "up" -> {
                        this.solidArea.y -= speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, inScreen[i]);
                            return false;
                        }
                    }
                    case "down" -> {
                        this.solidArea.y += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, inScreen[i]);
                            return false;
                        }
                    }
                    case "left" -> {
                        this.solidArea.x -= speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, inScreen[i]);
                            return false;
                        }
                    }
                    case "right" -> {
                        this.solidArea.x += speed;
                        if (SuperInteractable.inScreen[i].getSolidArea().intersects(this.solidArea)) {
                            reset(this, inScreen[i]);
                            return false;
                        }
                    }
                }
                reset(this, inScreen[i]);
            }
        }
        return true;
    }

    protected void reset(Entity entity, SuperInteractable interactable) {
        entity.solidArea.x = entity.rectangleDefaultX;
        entity.solidArea.y = entity.rectangleDefaultY;
        interactable.solidArea.x = interactable.defaultRectangleX;
        interactable.solidArea.y = interactable.defaultRectangleY;
    }
}
