import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean wKeyPressed, sKeyPressed, aKeyPressed, dKeyPressed;

    public boolean iswKeyPressed() {
        return wKeyPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wKeyPressed = true;
            case KeyEvent.VK_S -> sKeyPressed = true;
            case KeyEvent.VK_A -> aKeyPressed = true;
            case KeyEvent.VK_D -> dKeyPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wKeyPressed = false;
            case KeyEvent.VK_S -> sKeyPressed = false;
            case KeyEvent.VK_A -> aKeyPressed = false;
            case KeyEvent.VK_D -> dKeyPressed = false;
        }
    }
}
