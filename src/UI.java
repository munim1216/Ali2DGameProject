import java.awt.*;

public class UI {
    GamePanel gp;
    Player player;
    public UI(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;
    }

    public void draw(Graphics2D g2D) {
        g2D.setFont(new Font("Arial", Font.PLAIN, 40));
        g2D.setColor(Color.YELLOW);

        String str = "Keys: " + player.getNumKeys();
        g2D.drawString(str, 20, 60);
    }
}
