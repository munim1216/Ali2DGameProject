import java.awt.*;

public class AssetSetter {
    Entity[] NPCs;

    public AssetSetter(){}

    public void setNPC() {
        NPCs = new Entity[10];
        NPCs[0] = new Auron_NPC();

    }

    public void draw(Graphics2D g2D) {
        for (int i = 0; NPCs[i] != null; i++) {
            NPCs[i].draw(g2D);
        }
    }

    public void update() {
        for (int i = 0; NPCs[i] != null; i++) {
            NPCs[i].update();
        }
    }

    public Entity[] getNPCs() {
        return NPCs;
    }
}
