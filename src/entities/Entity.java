package entities;

import interactables.SuperInteractable;
import main.GamePanel;
import main.Tile;
import main.Utility;
import weapons.SuperWeapon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    // player, a method for collision requires a reference to the player so here it is
    private static Player player;
    // world map && possible tiles
    private static int[][] mapTileNum;
    private static Tile[] tiles;
    // position in overall world map
    protected int worldX;
    protected int worldY;
    protected int speed;
    // sprites
    protected BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    protected String direction;
    protected int currentSprite;
    protected int spriteCounter;
    protected int actionLockCounter; // to prevent them from having a seizure
    // hitbox (i hate)
    protected Rectangle solidArea; // hit boxes are actually not over ur player until they're checking for collison (every 1/60 of a second) i would've implemented differently, but i had no clue how to do it so i ended up doing it similarly to the guide
    protected int rectangleDefaultX;
    protected int rectangleDefaultY;
    private static Entity[] NPCs;
    private static Entity[] Mobs;
    // combat
    protected int health;
    protected int damage;
    protected int iframes;
    protected boolean invincible;
    // death animation
    protected boolean death;
    protected int deathCounter;
    protected boolean animationOver;
    // dialouge
    protected boolean hasDialogue; // not every entity gonna have dialogue (such as enemies)
    protected String[] dialogue; // their actual words
    protected static Entity lastTouchingPlayer; // to initiate dialogue
    protected int nextDialogue; // the next dialogue that's gonna be written
    protected static GamePanel gp; // needed to check the playstate
    // to determine the type of entity
    protected final static int TYPE_PLAYER = 0; // value needed across all subclasses
    protected final static int TYPE_NPC = 1; // value needed across all subclasses
    protected final static int TYPE_MOB = 2; // value needed across all sub
    protected final int TYPE;
    // to draw hp bar
    protected double hpDrawn;
    protected double sizeOfOneHP;
    public Entity(int rectangleDefaultX, int rectangleDefaultY, int TYPE){
        this.rectangleDefaultX = rectangleDefaultX;
        this.rectangleDefaultY = rectangleDefaultY;
        this.TYPE = TYPE;

        death = false;
        deathCounter = 0;
        animationOver = false;
    }

    public static void setNeededVariables(int[][] mapTileNum, Tile[] tiles, Player player, Entity[] NPCs, GamePanel gp, Entity[] Mobs) {
        // sets static variables needed
        Entity.mapTileNum = mapTileNum;
        Entity.tiles = tiles;
        Entity.player = player;
        Entity.NPCs = NPCs;
        Entity.gp = gp;
        Entity.Mobs = Mobs;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAnimationOver() {
        return animationOver;
    }

    public Rectangle getSolidArea() {
        return solidArea;
    }

    public void loseHP(int amountLost) {
        // method utilized to lower health
        health -= amountLost;
        if (TYPE == TYPE_MOB) {
            hpDrawn = health * sizeOfOneHP;
        }
        invincible = true;
    }
    public static void reset(SuperWeapon weapon, Entity target) {
        // the method utilized to reset the hitboxes when checking if a weapon and an entity's hitboxes intersect
        weapon.decideHitbox();
        target.solidArea.x = target.rectangleDefaultX;
        target.solidArea.y = target.rectangleDefaultY;
    }
    // the method that is called every 1/60 of a second so that all the entities are always updated
    public void update() {
        // if you aren't playing everything else should be paused
        if (gp.getGameState() != GamePanel.PLAY_STATE) {
            return;
        }
        // don't really need to update something outside of frame
        if (!Utility.notOutOfBounds(this, player)) {
            return;
        }

        // the method to decide a npc/mob's action
        setAction();

        // can only move if there isnt a collision
        if (collisionCheck()) {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "right" -> worldX += speed;
                case "left" -> worldX -= speed;
            }
        }

        // so the sprites don't rapidly change
        spriteCounter++;
        if (spriteCounter > 12) {
            alternateSprite();
            spriteCounter = 0;
        }
        if (invincible) {
            iframes++;
            if (iframes >= 60) {
                invincible = false;
            }
        }
    }

    public int getWorldX() {
        return worldX;
    }
    public int getWorldY() {
        return worldY;
    }
    protected int getType() {
        return TYPE;
    }
    protected boolean notInvincible() {
        return !invincible;
    }
    protected int getDamage() {
        return damage;
    }

    protected boolean collisionCheck() {
        return mobCollisionCheck() && tileCollisionCheck() && interactableCollisionCheck() && npcCollisionCheck() && playerCollisionCheck(); // combination of all my collision checks
    }

    protected boolean tileCollisionCheck() {
        // creates variables used to determine which rows and col will be checked
        int leftWorldX = worldX + solidArea.x;
        int rightWorldX = worldX + solidArea.x + solidArea.width;
        int topWorldY = worldY + solidArea.y;
        int bottomWorldY = worldY + solidArea.y + solidArea.height;

        // map organized in rows and
        int leftCol = leftWorldX / GamePanel.TILE_SIZE;
        int rightCol = rightWorldX / GamePanel.TILE_SIZE;
        int topRow = topWorldY / GamePanel.TILE_SIZE;
        int bottomRow = bottomWorldY / GamePanel.TILE_SIZE;

        int tileCheck1, tileCheck2; // when moving, you only gotta check the two posssible tiles you'll be moving into, the switch below decides that
        switch (direction) {
            case "up" -> {
                // going up means top left and top right tiles only gotta check
                topRow = (topWorldY - speed) / GamePanel.TILE_SIZE;
                tileCheck1 = mapTileNum[leftCol][topRow];
                tileCheck2 = mapTileNum[rightCol][topRow];
            }
            case "down" -> {
                // going down means bottom left and bottom right tiles only gotta check
                bottomRow = (bottomWorldY + speed) / GamePanel.TILE_SIZE;
                tileCheck1 = mapTileNum[leftCol][bottomRow];
                tileCheck2 = mapTileNum[rightCol][bottomRow];
            }
            case "right" -> {
                // going right means the right tiles have to be checked
                rightCol = (rightWorldX + speed) / GamePanel.TILE_SIZE;
                tileCheck1 = mapTileNum[rightCol][topRow];
                tileCheck2 = mapTileNum[rightCol][bottomRow];
            }
            case "left" -> {
                // going left means the left tiles have to be checked
                leftCol = (leftWorldX - speed) / GamePanel.TILE_SIZE;
                tileCheck1 = mapTileNum[leftCol][topRow];
                tileCheck2 = mapTileNum[leftCol][bottomRow];
            }
            default -> {
                // should crash the program if one of those above cases arent true.
                throw new RuntimeException("THIS WASN'T SUPOSED TO HAPPEN.");
            }
        }

        // if either of the two tilecheck tiles have the boolean collision to true, it'll return false meaning the entity cannot go in that direction.
        if (tiles[tileCheck1].isCOLLISION() || tiles[tileCheck2].isCOLLISION()) {
            return false;
        } else {
            return true;
        }
    }

    protected boolean interactableCollisionCheck() {
        SuperInteractable[] inScreen = SuperInteractable.getInScreen();
        for (int i = 0; inScreen[i] != null; i++) {
            if (SuperInteractable.getInScreen()[i].isCollision()) {
                // its actual x in the world, not the hitbox
                this.solidArea.x = this.solidArea.x + this.worldX;
                this.solidArea.y = this.solidArea.y + this.worldY;

                // actual x in world, not hitbox
                inScreen[i].getSolidArea().x = inScreen[i].getSolidArea().x + inScreen[i].getWorldX();
                inScreen[i].getSolidArea().y = inScreen[i].getSolidArea().y + inScreen[i].getWorldY();

                // after adjusting where the hit boxes of both the entity and interactable, it tests if the rectangles that represent their hit boxes
                // would intersect after moving in the direction they're trying to move in. (thanks to the handy method intersect from rectangle class)
                switch (direction) {
                    case "up" -> this.solidArea.y -= speed;
                    case "down" -> this.solidArea.y += speed;
                    case "left" -> this.solidArea.x -= speed;
                    case "right" -> this.solidArea.x += speed;
                }
                if (SuperInteractable.getInScreen()[i].getSolidArea().intersects(this.solidArea)) {
                    reset(this, inScreen[i]);
                    return false;
                } else {
                    reset(this, inScreen[i]);
                }
            }
            // it needs to reset it to its default rectangle x and y, or the hitboxes would get infintely massive and mess up a lotta stuff
        }
        return true;
    }

    protected boolean npcCollisionCheck() {
        for (int i = 0; NPCs[i] != null; i++) {
            if (NPCs[i] != this) {

                // its actual x in the world, not the hitbox
                this.solidArea.x = this.solidArea.x + this.worldX;
                this.solidArea.y = this.solidArea.y + this.worldY;

                // actual x in world, not hitbox
                NPCs[i].solidArea.x = NPCs[i].solidArea.x + NPCs[i].worldX;
                NPCs[i].solidArea.y = NPCs[i].solidArea.y + NPCs[i].worldY;

                // after adjusting where the hit boxes of both the entity and other entity, it tests if the rectangles that represent their hit boxes
                // would intersect after moving in the direction they're trying to move in. (thanks to the handy method intersect from rectangle class)
                switch (direction) {
                    case "up" -> this.solidArea.y -= speed;
                    case "down" -> this.solidArea.y += speed;
                    case "left" -> this.solidArea.x -= speed;
                    case "right" -> this.solidArea.x += speed;
                }
                if (NPCs[i].solidArea.intersects(this.solidArea)) {
                    reset(this, NPCs[i]);
                    if (this == player) {
                        lastTouchingPlayer = NPCs[i];
                    }
                    return false;
                }

                reset(this, NPCs[i]);
            }
        }
        return true;
    }

    protected boolean mobCollisionCheck() {
        for (int i = 0; Mobs[i] != null; i++) {
            if (Mobs[i] != this) {

                // its actual x in the world, not the hitbox
                this.solidArea.x = this.solidArea.x + this.worldX;
                this.solidArea.y = this.solidArea.y + this.worldY;

                // actual x in world, not hitbox
                Mobs[i].solidArea.x = Mobs[i].solidArea.x + Mobs[i].worldX;
                Mobs[i].solidArea.y = Mobs[i].solidArea.y + Mobs[i].worldY;

                // after adjusting where the hit boxes of both the entity and other entity, it tests if the rectangles that represent their hit boxes
                // would intersect after moving in the direction they're trying to move in. (thanks to the handy method intersect from rectangle class)
                switch (direction) {
                    case "up" -> this.solidArea.y -= speed;
                    case "down" -> this.solidArea.y += speed;
                    case "left" -> this.solidArea.x -= speed;
                    case "right" -> this.solidArea.x += speed;
                }
                if (Mobs[i].solidArea.intersects(this.solidArea)) {
                    reset(this, Mobs[i]);
                    if (this.getType() != TYPE_MOB && notInvincible()) {
                        this.loseHP(Mobs[i].getDamage());
                    }
                    return false;
                }

                reset(this, Mobs[i]);
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
            case "up" -> this.solidArea.y -= speed;
            case "down" -> this.solidArea.y += speed;
            case "left" -> this.solidArea.x -= speed;
            case "right" -> this.solidArea.x += speed;
        }
        if (player.solidArea.intersects(this.solidArea)) {
            reset(this, player);
            if (player.notInvincible() && getType() == TYPE_MOB) {
                player.loseHP(this.getDamage());
            }
            return false;
        }
        reset(this, player);
        return true;
    }

    protected void death() {
        // method to determine when to start and end death animation
        death = true;
        deathCounter++;
        if (deathCounter > 40) {
            animationOver = true;
        }
    }

    protected void reset(Entity entity, SuperInteractable interactable) {
        // method to reset hitboxes to their default state when checking if an entity and an interactable would collide
        entity.solidArea.x = entity.rectangleDefaultX;
        entity.solidArea.y = entity.rectangleDefaultY;
        interactable.getSolidArea().x = interactable.DEFAULT_RECTANGLE_X;
        interactable.getSolidArea().y = interactable.DEFAULT_RECTANGLE_Y;
    }

    protected void reset(Entity entity, Entity target) {
        // method to reset hitboxes to their default state when checking if an entity and another entity would collide
        entity.solidArea.x = entity.rectangleDefaultX;
        entity.solidArea.y = entity.rectangleDefaultY;
        target.solidArea.x = target.rectangleDefaultX;
        target.solidArea.y = target.rectangleDefaultY;
    }

    protected void draw(Graphics2D g2D) {
        // used to actually make the sprites appear on the screen
        if (!Utility.notOutOfBounds(this, player)) {
            // only rendered if its in frame
            return;
        }

        // math to decide where on the screen entity is being drawn
        int screenX = worldX - player.getWorldX() + Player.PLAYER_SCREEN_X;
        int screenY = worldY - player.getWorldY() + Player.PLAYER_SCREEN_Y;

        BufferedImage image = getCurrentSprite();

        // the code to create the death effect
        if (death) {
            if (deathCounter % 2 == 1) {
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
            } else {
                g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            }
        }

        // code to create the healthbar
        if (TYPE == TYPE_MOB && hpDrawn < GamePanel.TILE_SIZE && !(health <= 0)) {
            g2D.setColor(Color.BLACK);
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2D.fillRect(screenX - 1, screenY + GamePanel.TILE_SIZE, GamePanel.TILE_SIZE + 2, GamePanel.TILE_SIZE / 5);
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

            g2D.setColor(Color.RED);
            g2D.fillRect(screenX, screenY + GamePanel.TILE_SIZE, (int)hpDrawn, GamePanel.TILE_SIZE / 5);

        }

        // the actual draw!
        g2D.drawImage(image, screenX, screenY, null);

        if (death) {
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }


    // made this a method so its easier to read
    protected void alternateSprite() {
        if (currentSprite == 1) {
            currentSprite = 2;
        } else {
            currentSprite = 1;
        }
    }

    // for npcs and mobs, their default behavior, there will be more advanced ones in the future (hopefully)
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

    // method to return the string that is needed to be drawn on the screen when you're in dialogue
    protected String speak() {
        String speechBox;
        if (nextDialogue >= dialogue.length) {
            nextDialogue = 0;
        }
        speechBox = dialogue[nextDialogue];
        nextDialogue++;

        return speechBox;
    }
    private BufferedImage getCurrentSprite() {
        BufferedImage image = null;

        // switch to decide which sprite is being displayed
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
        return image;
    }

}
