import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

public class GamePanel extends JPanel implements Runnable, WindowListener {
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
    private WindowListener windowListener;
    private boolean isRunning;
    // PLAYER INPUT
    private KeyHandler keyH;
    // PLAYER
    private Player player;
    // MAP SETTINGS
    public static final int MAX_WORLD_COL = 50;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    private TileManager tileManager;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        isRunning = true;

        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setFocusable(true);

        player = new Player(keyH);

        SuperInteractable.setPlayer(player);

        tileManager = new TileManager(this, player);
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
                if (isRunning) {
                    update();
                    repaint();
                    System.out.println("IS RUNNING");
                } else {
                    System.out.println("IS NOT RUNNING");
                }
                delta = 0;
                System.out.println("MouseInfo, x: " + MouseInfo.getPointerInfo().getLocation().x + " y: " + MouseInfo.getPointerInfo().getLocation().y);
                System.out.println();
            }
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        tileManager.draw(g2D);
        SuperInteractable.draw(g2D);
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
        window.addWindowListener(windowListener);
        window.setVisible(true);

    }

    @Override
    public void windowOpened(WindowEvent e) {
        isRunning = true;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        isRunning = false;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        isRunning = false;
    }

    @Override
    public void windowIconified(WindowEvent e) {
        isRunning = false;
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        isRunning = true;
    }

    @Override
    public void windowActivated(WindowEvent e) {
        isRunning = true;
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        isRunning = false;
    }
}
