package main;
import entities.Player;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    private final int heartX;
    private final int heartY;
    private Color opaqueBlack;
    private String nextDialogue;
    private Font titleFont;
    private Font npcFont;
    private int commandNum;
    private final int START = 0, CONTINUE = 1, QUIT = 2;
    private BufferedImage full_shield, half_shield, empty_shield;
    private Player player;

    public UI(GamePanel gp, Player player) {
        this.gp = gp;
        this.player = player;

        // custom fonts
        try { // credit to https://github.com/curadProgrammer/shorts-java-programs/blob/main/Adding%20Custom%20Font/src/App.java for helping me kinda understand how i can do it
            File customFontFile = new File("resources/fonts/Cave-Story.ttf");
            titleFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile);
            File customFontFile2 = new File("resources/fonts/PixelifySans-VariableFont_wght.ttf");
            npcFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile2).deriveFont(24f);
        } catch (FontFormatException | IOException e) { // i dont understand why intelliji is allowed to put one | instead of || for an or??? quite odd
            e.printStackTrace();
        }

        // player health ui
        heartX = GamePanel.TILE_SIZE / 2;
        heartY = GamePanel.TILE_SIZE / 2;
        try {

            full_shield = Utility.scale(ImageIO.read(new File("resources/sprites/shields/full_shield.png")));
            half_shield = Utility.scale(ImageIO.read(new File("resources/sprites/shields/half_shield.png")));
            empty_shield = Utility.scale(ImageIO.read(new File("resources/sprites/shields/empty_shield.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // TITLE SCREEN
        commandNum = START;

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
        int hp = player.getCurrentHealth();
        int x = heartX;
        int y = heartY;

        for (int i = 1; i < 4; i++) {
            if (hp - 2 >= 0) {
                g2D.drawImage(full_shield, x, y, null);
                hp -= 2;
            } else if (hp - 1 >= 0) {
                g2D.drawImage(half_shield, x, y, null);
                hp--;
            } else {
                g2D.drawImage(empty_shield, x, y, null);
            }
            x += GamePanel.TILE_SIZE;
        }
    }

    public void drawPauseMenu(Graphics2D g2D) {
        g2D.setColor(Color.PINK);
        g2D.fillRect(0,0,GamePanel.SCREEN_WIDTH,GamePanel.SCREEN_HEIGHT);
        g2D.setColor(Color.BLACK);
        g2D.setFont(new Font("Times New Roman", Font.BOLD, 40));
        g2D.drawString("PAUSED", GamePanel.SCREEN_WIDTH / 2 - 20, GamePanel.SCREEN_HEIGHT / 2);
    }
    public void drawEvent(Graphics2D g2D, Event event) {
        drawBox(g2D);

        //g2D.drawString(event.getDialogue(), textX, textY);
    }


    public void drawDialouge(Graphics2D g2D) {
       drawBox(g2D);

        // words
        g2D.setFont(npcFont.deriveFont(24f));
        g2D.drawString(nextDialogue, textX, textY);
    }

    public void setNextDialogue(String dialogue) {
        nextDialogue = dialogue;
    }

    public void drawTitleScreen(Graphics2D g2D) {
        String text;
        int x;
        int y;

        // TITLE OF GAME
        g2D.setFont(titleFont.deriveFont(125f));
        g2D.setColor(Color.GRAY);
        text = "RENEE'S WORLD";
        x = getXForCenteredText(text, g2D) + 3;
        y = textY + GamePanel.TILE_SIZE + 3;

        g2D.drawString(text, x, y);

        x -= 3;
        y -= 3;
        g2D.setColor(Color.WHITE);
        g2D.drawString(text, x, y);

        // RENEE SPRITE
        BufferedImage renee = null;
        try {
            renee = ImageIO.read(new File("resources/sprites/ReneeSprite/reneeDown1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        x = GamePanel.SCREEN_WIDTH / 2 - GamePanel.TILE_SIZE;
        y = textY + GamePanel.TILE_SIZE * 2;
        g2D.drawImage(renee, x, y, GamePanel.TILE_SIZE * 2, GamePanel.TILE_SIZE * 2, null);

        // MENU
        g2D.setFont(titleFont.deriveFont(45f));
        text = "NEW GAME";
        x = getXForCenteredText(text, g2D);
        y += GamePanel.TILE_SIZE * 4;
        g2D.drawString(text, x, y);
        if (commandNum == 0) {
            g2D.drawString(">", x - GamePanel.TILE_SIZE, y);
        }

        text = "CONTINUE";
        x = getXForCenteredText(text, g2D);
        y += GamePanel.TILE_SIZE;
        g2D.drawString(text, x, y);
        if (commandNum == 1) {
            g2D.drawString(">", x - GamePanel.TILE_SIZE, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text, g2D);
        y += GamePanel.TILE_SIZE;
        g2D.drawString(text, x, y);
        if (commandNum == 2) {
            g2D.drawString(">", x - GamePanel.TILE_SIZE, y);
        }
    }
    public void incrementCommandNum() {
        if (commandNum < QUIT) {
            commandNum++;
        } else {
            commandNum = START;
        }
        GamePanel.SE.setClip(1);
        GamePanel.SE.play();
    }
    public void decrementCommandNum() {
        if (commandNum > START) {
            commandNum--;
        } else {
            commandNum = QUIT;
        }
        GamePanel.SE.setClip(1);
        GamePanel.SE.play();
    }

    public void selectOption() {
        switch (commandNum) {
            case START -> {
                GamePanel.SE.setClip(1);
                GamePanel.SE.play();
                gp.startGame();
            }
            case CONTINUE -> {
                throw new RuntimeException("UNIMPLEMENTED CURRENTLY");
            }
            case QUIT -> {
                GamePanel.SE.setClip(1);
                GamePanel.SE.play();
                System.exit(0);
            }
        }
    }

    private int getXForCenteredText(String text, Graphics2D g2D) {
        int length = (int) g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
        return GamePanel.SCREEN_WIDTH / 2 - length / 2;
    }

    private void drawBox(Graphics2D g2D) {
        // actual rectangle
        g2D.setColor(opaqueBlack);
        g2D.fillRoundRect(boxX, boxY, width, height, 35, 35) ;

        // outline
        g2D.setStroke(new BasicStroke(5));
        g2D.setColor(Color.WHITE);
        g2D.drawRoundRect(boxX + 5, boxY + 5, width - 10, height - 10, 25, 25) ;
    }
}
