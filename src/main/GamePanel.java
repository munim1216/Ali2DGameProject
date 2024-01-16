package main;

import entities.Entity;
import entities.Player;
import entities.AssetSetter;
import interactables.SuperInteractable;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // STATIC SCREEN SETTINGS
    private static final int ORIGINALTILE_SIZE = 16;
    private static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINALTILE_SIZE * SCALE;

    // SCREEN SETTINGS
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;

    // GAME LOOP
    private Thread gameThread;
    public static int gameState;
    private final int FPS = 60;
    public static final int TITLESCREEN = 3;
    public static final int DIAL0GUESTATE = 2;
    public static final int PLAYSTATE = 1;
    public static final int PAUSESTATE = 0;

    // PLAYER INPUT
    private KeyHandler keyH;
    // PLAYER
    private Player player;
    // MAP SETTINGS
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    private TileManager tileManager;
    public final UI UI;
    // SOUND
    public static Sound MUSIC = new Sound(0);
    public static Sound SE = new Sound(1);
    // PLACING THINGS
    AssetSetter assetSetter;

    public GamePanel() {
        // setting up size of the window
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        // so you open on the title screen
        gameState = TITLESCREEN;

        // so the game can actually detect key input
        keyH = new KeyHandler(this);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // our player
        player = new Player(keyH);

        // needed to render only things in frame
        SuperInteractable.setPlayer(player);

        // the ui, needs player to access its hp
        UI = new UI(this, player);

        // to deal with npcs (and maybe enemies in the future too)
        assetSetter = new AssetSetter();
        assetSetter.setNPC();

        // to draw the tiles
        tileManager = new TileManager(this, player);

        // collision hell needs all of these.
        Entity.setNeededVariables(tileManager.getMapTileNum(), tileManager.getTiles(), player, assetSetter.getNPCs(), this);

        // the game can actually start if the thread is active
        startGameThread();

        // the jframe
        setUpWindow();

        // MUSIC THAT KINDA SUCKS
        MUSIC.setClip(0);
        MUSIC.play();
        MUSIC.loop();
    }

    private void startGameThread() {
        // makes sure the game only updates and redraws every 1/60 of a second
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() { // stupid code won't let me override it and reduce visibility
        // variables needed to ensure its locked at 60 fps and below
        long currentTime = System.nanoTime();
        long previousTime = currentTime;
        double delta = 0.0;
        double drawInterval = 1000000000.0 / FPS;

        while (gameThread != null) {
            // system.nanotime is java's very accurate clock or something (i dont 100% remember)
            currentTime = System.nanoTime();

            // the time between now and the least time this looped
            delta += (double) (currentTime - previousTime) / drawInterval;

            previousTime = currentTime;
            if (delta >= 1) {
                // delta being 1 or greater means 1/60 of a second;
                update();
                repaint();
                delta = 0;
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // debugging
      //  long drawStart = System.nanoTime();

        Graphics2D g2D = (Graphics2D) g;
        switch (gameState) {
            case PLAYSTATE -> playState(g2D);
            case DIAL0GUESTATE -> dialogueState(g2D);
            case PAUSESTATE -> pauseState(g2D);
            case TITLESCREEN -> titleScreenState(g2D);
            default -> throw new UnsupportedOperationException("HEY YOU'RE NOT SUPPOSED TO BE IN THIS STATE.");
        }

        // debugging
//        long drawEnd = System.nanoTime();
//        System.out.println("Draw Time: " + (drawEnd - drawStart));

        // so we dont have a billion graphics 2d i think? (i dont remember the purpose of this i think its efficency or smth tho)
        g2D.dispose();
    }

    public int getGameState() {
        return gameState;
    }
    public void pause() {
        gameState = PAUSESTATE;
    }
    public void unpause() {
        gameState = PLAYSTATE;
    }
    public void startDialouge() {
        gameState = DIAL0GUESTATE;

    }
    public void startGame() {
        gameState = PLAYSTATE;
        MUSIC.stop();
        MUSIC.setClip(1);
        MUSIC.play();
        MUSIC.loop();
    }

    private void update() {
        // actually keeps the game runnning!
        player.playerUpdate();
        SuperInteractable.interactablesInFrame();
        assetSetter.update();
    }

    private void setUpWindow() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D GAME");
        window.add(this);
        window.pack();
        window.setVisible(true);
    }

    private void playState(Graphics2D g2D) {
        tileManager.draw(g2D);
        SuperInteractable.draw(g2D);
        assetSetter.draw(g2D);
        UI.draw(g2D);
        player.draw(g2D);
    }

    private void pauseState(Graphics2D g2D) {
        UI.drawPauseMenu(g2D);
    }

    private void dialogueState(Graphics2D g2D) {
        tileManager.draw(g2D);
        SuperInteractable.draw(g2D);
        assetSetter.draw(g2D);
        UI.draw(g2D);
        UI.drawDialouge(g2D);
        player.draw(g2D);
    }

    private void titleScreenState(Graphics2D g2D) {
        UI.drawTitleScreen(g2D);
    }
}
