package main;

import entities.Entity;
import entities.Player;
import entities.AssetSetter;
import events.EventHandler;
import interactables.SuperInteractable;
import events.Event;
import weapons.SuperWeapon;

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
    private final int FPS = 60;
    // CURRENT STATE OF GAME
    public static int gameState;
    public static final int STATS_STATE = 7;
    public static final int RESULT_STATE = 6;
    public static final int CHOICE_STATE = 5;
    public static final int EVENT_STATE = 4;
    public static final int TITLE_SCREEN = 3;
    public static final int DIAL0GUE_STATE = 2;
    public static final int PLAY_STATE = 1;
    public static final int PAUSE_STATE = 0;

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
    // GAME EVENTS
    EventHandler eventHandler;

    public GamePanel() {
        // setting up size of the window
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        // so you open on the title screen
        gameState = TITLE_SCREEN;

        // so the game can actually detect key input
        keyH = new KeyHandler(this);
        this.addKeyListener(keyH);
        this.setFocusable(true);


        // handles all the events
        eventHandler = new EventHandler();
        Event.setGP(this);

        // our player
        player = new Player(keyH, eventHandler);

        // needed to render only things in frame
        SuperInteractable.setPlayer(player);


        // the ui, needs player to access its hp
        UI = new UI(this, player);

        // player also needs access to UI
        player.addUI(UI);

        // to deal with npcs & mobs
        AssetSetter.setNPC();
        AssetSetter.createMobs();

        // class that manages all weapons needs a few things to create its static variables
        SuperWeapon.setNeededStaticVariables(player, this);

        // to draw the tiles
        tileManager = new TileManager(this, player);

        // collision hell needs all of these.
        Entity.setNeededVariables(tileManager.getMapTileNum(), tileManager.getTiles(), player, AssetSetter.getNPCs(), this, AssetSetter.getMobs());


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
            case PLAY_STATE -> playState(g2D);
            case DIAL0GUE_STATE -> dialogueState(g2D);
            case PAUSE_STATE -> pauseState(g2D);
            case TITLE_SCREEN -> titleScreenState(g2D);
            case EVENT_STATE, CHOICE_STATE, RESULT_STATE -> eventState(g2D);
            case STATS_STATE -> statsState(g2D);

            default -> throw new UnsupportedOperationException("HEY YOU'RE NOT SUPPOSED TO BE IN THIS STATE.");
        }

        // debugging
//        long drawEnd = System.nanoTime();
//        System.out.println("Draw Time: " + (drawEnd - drawStart));

        // so we dont have a billion graphics 2d i think? (i dont remember the purpose of this i think its efficency or smth tho)
        g2D.dispose();
    }

    public void nextDialogue() {
        eventHandler.getCurrentEvent().incrementDialogue();
    }
    // game state things
    public int getGameState() {
        return gameState;
    }
    public void pause() {
        gameState = PAUSE_STATE;
    }
    public void unpause() {
        gameState = PLAY_STATE;
    }
    public void startDialouge() {
        gameState = DIAL0GUE_STATE;

    }
    public void startEvent() {
        gameState = EVENT_STATE;
    }
    public void choiceTime() {
        gameState = CHOICE_STATE;
    }
    public void endEvent() {
        gameState = PLAY_STATE;
        eventHandler.resetEvent();
    }
    public void lookAtStats() {
        gameState = STATS_STATE;
    }
    // beginning the game after clicking start
    public void startGame() {
        gameState = PLAY_STATE;
        MUSIC.stop();
        MUSIC.setClip(1);
        MUSIC.play();
        MUSIC.loop();
    }

    private void update() {
        // actually keeps the game runnning!
        player.playerUpdate();
        if (player.isSwing() || player.getWeapon().isMidSwing() && gameState == PLAY_STATE) {
            player.getWeapon().update();
        }
        SuperInteractable.interactablesInFrame();
        AssetSetter.update();
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
        AssetSetter.draw(g2D);
        UI.draw(g2D);
        if (player.isSwing() || player.getWeapon().isMidSwing()) {
            player.getWeapon().draw(g2D);
        }
        player.draw(g2D);

    }
    private void statsState(Graphics2D g2D) {
        tileManager.draw(g2D);
        SuperInteractable.draw(g2D);
        AssetSetter.draw(g2D);
        UI.draw(g2D);
        UI.drawInventory(g2D);
        if (player.isSwing() || player.getWeapon().isMidSwing()) {
            player.getWeapon().draw(g2D);
        }
        player.draw(g2D);
    }
    private void pauseState(Graphics2D g2D) {
        UI.drawPauseMenu(g2D);
    }

    private void dialogueState(Graphics2D g2D) {
        tileManager.draw(g2D);
        SuperInteractable.draw(g2D);
        AssetSetter.draw(g2D);
        UI.draw(g2D);
        UI.drawDialouge(g2D);
        player.draw(g2D);
    }

    private void titleScreenState(Graphics2D g2D) {
        UI.drawTitleScreen(g2D);
    }
    private void eventState(Graphics2D g2D) {
        tileManager.draw(g2D);
        SuperInteractable.draw(g2D);
        AssetSetter.draw(g2D);
        UI.draw(g2D);
        UI.drawEvent(g2D, eventHandler.getCurrentEvent());
        player.draw(g2D);
    }

    public void processEvent() {
        eventHandler.processEvent(UI.getCommandNum());
        eventHandler.getCurrentEvent().results(player);
        gameState = RESULT_STATE;
    }
}
