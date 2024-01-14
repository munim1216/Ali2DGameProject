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
    private int gameState;
    private final int FPS = 60;
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
    private UI ui;
    // SOUND
    public static Sound MUSIC = new Sound(0);
    public static Sound SE = new Sound(1);
    // PLACING THINGS
    AssetSetter assetSetter;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        gameState = PLAYSTATE;

        keyH = new KeyHandler(this);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        player = new Player(keyH);

        SuperInteractable.setPlayer(player);

        ui = new UI(this, player);

        assetSetter = new AssetSetter();
        assetSetter.setNPC();

        tileManager = new TileManager(this, player);
        startGameThread();
        setUpWindow();
        MUSIC.setClip(0);
        MUSIC.play();
        MUSIC.loop();
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() { // stupid code won't let me override it and reduce visibility
        long currentTime = System.nanoTime();
        long previousTime = currentTime;
        double delta = 0.0;
        double drawInterval = 1000000000.0 / FPS;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (double) (currentTime - previousTime) / drawInterval;

            previousTime = currentTime;
            if (delta >= 1) {
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
        if (gameState == PLAYSTATE) {
            tileManager.draw(g2D);
            SuperInteractable.draw(g2D);
            ui.draw(g2D);
            player.draw(g2D);
            assetSetter.draw(g2D);
        } else {
            ui.drawPauseMenu(g2D);
        }


        // debugging
//        long drawEnd = System.nanoTime();
//        System.out.println("Draw Time: " + (drawEnd - drawStart));

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

    private void update() {
        if (gameState == PLAYSTATE) {
            player.playerUpdate();
            SuperInteractable.interactablesInFrame();
            assetSetter.update();
        }
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

}
