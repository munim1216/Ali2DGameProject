import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    // player
    private static Player player;
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
    protected int actionLockCounter;
    // hitbox (i hate)
    protected Rectangle solidArea;
    protected int rectangleDefaultX;
    protected int rectangleDefaultY;
    protected static Entity[] NPCs;
    // combat
    protected int health;
    protected int defense;
    protected double multipler = 1.0;
    // dialouge
    protected boolean hasDialogue;
    protected String[] dialogue;
    protected Entity lastTouchingPlayer;
    protected int nextDialogue;
    protected static GamePanel gp;
    public Entity(int rectangleDefaultX, int rectangleDefaultY){
        this.rectangleDefaultX = rectangleDefaultX;
        this.rectangleDefaultY = rectangleDefaultY;
    }

    public static void setNeededVariables(int[][] mapTileNum, Tile[] tiles, Player player, Entity[] NPCs, GamePanel gp) {
        Entity.mapTileNum = mapTileNum;
        Entity.tiles = tiles;
        Entity.player = player;
        Entity.NPCs = NPCs;
        Entity.gp = gp;
    }
    protected boolean collisionCheck() {
        return tileCollisionCheck() && interactableCollisionCheck() && entityCollisionCheck() && playerCollisionCheck();
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

    protected boolean interactableCollisionCheck() {
        SuperInteractable[] inScreen = SuperInteractable.getInScreen();
        for (int i = 0; inScreen[i] != null; i++) {
            // its actual x in the world, not the hitbox
            this.solidArea.x = this.solidArea.x + this.worldX;
            this.solidArea.y = this.solidArea.y + this.worldY;

            // actual x in world, not hitbox
            inScreen[i].getSolidArea().x = inScreen[i].getSolidArea().x + inScreen[i].getWorldX();
            inScreen[i].getSolidArea().y = inScreen[i].getSolidArea().y + inScreen[i].getWorldY();

            // switch is only checked if collision is true
            if (SuperInteractable.inScreen[i].isCollision()) {
                switch (direction) {
                    case "up" -> {
                        // moving up is closer to the 0 for y
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
            }
            reset(this, inScreen[i]);
        }
        return true;
    }
    protected boolean entityCollisionCheck() {
        for (int i = 0; NPCs[i] != null; i++) {
            if (NPCs[i] != this) {

                // its actual x in the world, not the hitbox
                this.solidArea.x = this.solidArea.x + this.worldX;
                this.solidArea.y = this.solidArea.y + this.worldY;

                // actual x in world, not hitbox
                NPCs[i].solidArea.x = NPCs[i].solidArea.x + NPCs[i].worldX;
                NPCs[i].solidArea.y = NPCs[i].solidArea.y + NPCs[i].worldY;

                // switch is only checked if collision is true
                switch (direction) {
                    case "up" -> {
                        // moving up is closer to the 0 for y
                        this.solidArea.y -= speed;
                        if (NPCs[i].solidArea.intersects(this.solidArea)) {
                            reset(this, NPCs[i]);
                            lastTouchingPlayer = NPCs[i];
                            System.out.println(lastTouchingPlayer);

                            return false;
                        }
                    }
                    case "down" -> {
                        this.solidArea.y += speed;
                        if (NPCs[i].solidArea.intersects(this.solidArea)) {
                            reset(this, NPCs[i]);
                            lastTouchingPlayer = NPCs[i];
                            System.out.println(lastTouchingPlayer);

                            return false;
                        }
                    }
                    case "left" -> {
                        this.solidArea.x -= speed;
                        if (NPCs[i].solidArea.intersects(this.solidArea)) {
                            reset(this, NPCs[i]);
                            lastTouchingPlayer = NPCs[i];
                            System.out.println(lastTouchingPlayer);

                            return false;
                        }
                    }
                    case "right" -> {
                        this.solidArea.x += speed;
                        if (NPCs[i].solidArea.intersects(this.solidArea)) {
                            reset(this, NPCs[i]);
                            lastTouchingPlayer = NPCs[i];
                            System.out.println(lastTouchingPlayer);

                            return false;
                        }
                    }
                }
                lastTouchingPlayer = null;
                reset(this, NPCs[i]);
            }
        }
        return true;
    }

    protected boolean playerCollisionCheck() {
        if (this == player) {
            return true;
        }
        // its actual x in the world, not the hitbox
        this.solidArea.x = this.solidArea.x + this.worldX;
        this.solidArea.y = this.solidArea.y + this.worldY;

        // actual x in world, not hitbox
        player.solidArea.x = player.solidArea.x + player.worldX;
        player.solidArea.y = player.solidArea.y + player.worldY;

        // switch is only checked if collision is true
        switch (direction) {
            case "up" -> {
                // moving up is closer to the 0 for y
                this.solidArea.y -= speed;
                if (player.solidArea.intersects(this.solidArea)) {
                    reset(this, player);
                    return false;
                }
            }
            case "down" -> {
                this.solidArea.y += speed;
                if (player.solidArea.intersects(this.solidArea)) {
                    reset(this, player);
                    return false;
                }
            }
            case "left" -> {
                this.solidArea.x -= speed;
                if (player.solidArea.intersects(this.solidArea)) {
                    reset(this, player);
                    return false;
                }
            }
            case "right" -> {
                this.solidArea.x += speed;
                if (player.solidArea.intersects(this.solidArea)) {
                    reset(this, player);
                    return false;
                }
            }
        }
        reset(this, player);
        return true;
    }

    protected void reset(Entity entity, SuperInteractable interactable) {
        entity.solidArea.x = entity.rectangleDefaultX;
        entity.solidArea.y = entity.rectangleDefaultY;
        interactable.solidArea.x = interactable.defaultRectangleX;
        interactable.solidArea.y = interactable.defaultRectangleY;
    }

    protected void reset(Entity entity, Entity target) {
        entity.solidArea.x = entity.rectangleDefaultX;
        entity.solidArea.y = entity.rectangleDefaultY;
        target.solidArea.x = target.rectangleDefaultX;
        target.solidArea.y = target.rectangleDefaultY;
    }


    protected void draw(Graphics2D g2D) {
        if (!Utility.notOutOfBounds(this, player)) {
            return;
        }

        int screenX = worldX - player.getworldX() + Player.PLAYER_SCREEN_X;
        int screenY = worldY - player.getworldY() + Player.PLAYER_SCREEN_Y;

        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (currentSprite == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
            }
            case "down" -> {
                if (currentSprite == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
            }
            case "left" -> {
                if (currentSprite == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
            }
            case "right" -> {
                if (currentSprite == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
            }
        }

        g2D.drawImage(image, screenX, screenY, null);
    }

    protected void alternateSprite() {
        if (currentSprite == 1) {
            currentSprite = 2;
        } else {
            currentSprite = 1;
        }
    }

    protected void setAction() {
        actionLockCounter++;
        if (actionLockCounter >= 120) {
            switch ((int) (Math.random() * 4) + 1) {
                case 1 -> direction = "up";
                case 2 -> direction = "down";
                case 3 -> direction = "right";
                case 4 -> direction = "left";
            }
            actionLockCounter = 0;
        }
    }
    protected String speak() {
        String speechBox;
        if (nextDialogue >= dialogue.length) {
            nextDialogue = 0;
        }
        speechBox = dialogue[nextDialogue];
        nextDialogue++;

        return speechBox;
    }
    public void update() {
        if (gp.getGameState() != GamePanel.PLAYSTATE) {
            return;
        }
        if (!Utility.notOutOfBounds(this, player)) {
            return;
        }

        setAction();
        if (collisionCheck()) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "right" -> worldX += speed;
                case "left" -> worldX -= speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            alternateSprite();
            spriteCounter = 0;
        }
    }

}
