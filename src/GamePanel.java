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
    Thread gameThread;

    GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        startGameThread();
        run();
        setUpWindow();
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


    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void run() {
        while (gameThread != null) {

        }
    }

    public
}
