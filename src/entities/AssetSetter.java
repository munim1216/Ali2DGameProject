package entities;

import main.GamePanel;

import java.awt.*;

public class AssetSetter {
    // all npcs & mobs are extended from its super class entity, and asset manager manages them as a whole
    private static Entity[] NPCs;
    private static Entity[] Mobs;
    private AssetSetter(){}

    // actually creates the npc objects (i should probably combine this with the constructor)
    public static void setNPC() {
        NPCs = new Entity[10];
        NPCs[0] = new Auron_NPC();
    }

    public static void createMobs() {
        Mobs =  new Entity[10];
        Mobs[0] = new BlueGhost_MOB(GamePanel.TILE_SIZE * 10, GamePanel.TILE_SIZE * 10);
    }

    // makes sure to call every NPC's draw method
    public static void draw(Graphics2D g2D) {
        for (int i = 0; NPCs[i] != null; i++) {
            NPCs[i].draw(g2D);
        }
        for (int i = 0; Mobs[i] != null; i++) {
            Mobs[i].draw(g2D);
        }
    }

    // makes sure to call every NPC's update method
    public static void update() {
        for (int i = 0; NPCs[i] != null; i++) {
            NPCs[i].update();
        }
        for (int i = 0; Mobs[i] != null; i++) {
            Mobs[i].update();
        }
    }

    // if a class needs to get the NPCs array list
    public static Entity[] getNPCs() {
        return NPCs;
    }

    public static Entity[] getMobs() {
        return Mobs;
    }
}
