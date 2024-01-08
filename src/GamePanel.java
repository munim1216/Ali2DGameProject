import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    private final int originalTileSize = 16;
    private final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int screenWidth = tileSize * maxScreenCol;

    // GAME LOOP
    private Thread gameThread;
    private final int FPS = 60;
    // player's input
    private KeyHandler keyH;
    // players default position;
    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 3;


    GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setFocusable(true);

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

        g.setColor(Color.white);

        g2D.fillRect(playerX, playerY, tileSize, tileSize);

        g2D.dispose();
    }

    private void update() {
        if (keyH.isWKeyPressed()) {
            playerY -= playerSpeed;
        }
        if (keyH.isSKeyPressed()) {
            playerY += playerSpeed;
        }
        if (keyH.isDKeyPressed()) {
            playerX += playerSpeed;
        }
        if (keyH.isAKeyPressed()) {
            playerX -= playerSpeed;
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
