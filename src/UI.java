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

    public void drawPauseMenu(Graphics2D g2D) {
        g2D.setColor(Color.PINK);
        g2D.fillRect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g2D.drawString("PAUSED", GamePanel.SCREEN_WIDTH / 2 - 20, GamePanel.SCREEN_HEIGHT / 2);
    }
}
