import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UI {
    GamePanel gp;
    private final int boxX;
    private final int width;
    private final int boxY;
    private final int height;
    private final int textX;
    private final int textY;
    private Color opaqueBlack;
    private String nextDialogue;
    private Font font;

    public UI(GamePanel gp) {
        this.gp = gp;

        try { // credit to https://github.com/curadProgrammer/shorts-java-programs/blob/main/Adding%20Custom%20Font/src/App.java for helping me kinda understand how i can do it
            File customFontFile = new File("src/Fonts/PixelEmulator-xq08.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(24f);
        } catch (FontFormatException | IOException e) { // i dont understand why intelliji is allowed to put one | instead of || for an or??? quite odd
            e.printStackTrace();
        }

        nextDialogue = "";

        boxX = GamePanel.TILE_SIZE * 2;
        boxY = GamePanel.TILE_SIZE / 2;
        width = GamePanel.TILE_SIZE * 12;
        height = GamePanel.TILE_SIZE * 4;
        opaqueBlack = new Color(0,0,0,200);

        textX = boxX + GamePanel.TILE_SIZE / 2;
        textY = boxY + GamePanel.TILE_SIZE;


    }

    public void draw(Graphics2D g2D) {
        // to be implemented later
    }

    public void drawPauseMenu(Graphics2D g2D) {
        g2D.setColor(Color.PINK);
        g2D.fillRect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g2D.drawString("PAUSED", GamePanel.SCREEN_WIDTH / 2 - 20, GamePanel.SCREEN_HEIGHT / 2);
    }

    public void drawDialougeBox(Graphics2D g2D) {
        // actual rectangle
        g2D.setColor(opaqueBlack);
        g2D.fillRoundRect(boxX, boxY, width, height, 35, 35) ;

        // outline
        g2D.setStroke(new BasicStroke(5));
        g2D.setColor(Color.WHITE);
        g2D.drawRoundRect(boxX + 5, boxY + 5, width - 10, height - 10, 25, 25) ;

        // words
        g2D.setFont(font);
        g2D.drawString(nextDialogue, textX, textY);
    }

    public void setNextDialogue(String dialogue) {
        nextDialogue = dialogue;
    }

    public void drawTitleScreen(Graphics2D g2D) {

    }
}
