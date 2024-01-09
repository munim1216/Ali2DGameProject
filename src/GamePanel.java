import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // STATIC SCREEN SETTINGS
    private static final int ORIGINALTILESIZE = 16;
    private static final int SCALE = 3;
    public static final int TILESIZE = ORIGINALTILESIZE * SCALE;

    // SCREEN SETTINGS
    public static final int MAX_SCREEN_COL = 16;
    public static final int MAX_SCREEN_ROW = 12;
    private final int screenHeight = TILESIZE * MAX_SCREEN_ROW;
    private final int screenWidth = TILESIZE * MAX_SCREEN_COL;

    // GAME LOOP
    private Thread gameThread;
    private final int FPS = 60;
    // player's input
    private KeyHandler keyH;
    // players default position;
    private Player player;
    private TileManager tileManager;

    GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setFocusable(true);

        tileManager = new TileManager(this);

        player = new Player(keyH);
        startGameThread();
        setUpWindow();
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

        Graphics2D g2D = (Graphics2D) g;

        tileManager.draw(g2D);
        player.draw(g2D);

        g2D.dispose();
    }

    private void update() {
       player.playerUpdate();
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
