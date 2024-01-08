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
    // player's input
    private KeyHandler keyH;
    // players default position;
    private int playerX = 100;
    private int playerY = 100;


    GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        keyH = new KeyHandler();
        this.addKeyListener(keyH);
        this.setFocusable(true);

        startGameThread();
        run();
        setUpWindow();
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() { // stupid code won't let me override it and reduce visibility
        int delta = 0;
        while (gameThread != null) {
            //long sysTime = System.nanoTime();
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        g.setColor(Color.white);

        g2D.fillRect(playerX, playerY, tileSize, tileSize);

        g2D.dispose();
    }

    private void
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
