package entities;

import java.awt.*;

public class AssetSetter {
    // all npcs are extended from its super class entity, and asset manager manages them as a whole
    private Entity[] NPCs;

    public AssetSetter(){}

    // actually creates the npc objects (i should probably combine this with the constructor)
    public void setNPC() {
        NPCs = new Entity[10];
        NPCs[0] = new Auron_NPC();

    }

    // makes sure to call every NPC's draw method
    public void draw(Graphics2D g2D) {
        for (int i = 0; NPCs[i] != null; i++) {
            NPCs[i].draw(g2D);
        }
    }

    // makes sure to call every NPC's update method
    public void update() {
        for (int i = 0; NPCs[i] != null; i++) {
            NPCs[i].update();
        }
    }

    // if a class needs to get the NPCs array list
    public Entity[] getNPCs() {
        return NPCs;
    }
}
