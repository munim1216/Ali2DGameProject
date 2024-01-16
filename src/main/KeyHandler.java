package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean wKeyPressed, sKeyPressed, aKeyPressed, dKeyPressed, fKeyPressed;

    public boolean isWKeyPressed() {
        return wKeyPressed;
    }
    public boolean isAKeyPressed() {
        return aKeyPressed;
    }

    public boolean isDKeyPressed() {
        return dKeyPressed;
    }

    public boolean isSKeyPressed() {
        return sKeyPressed;
    }

    public boolean isFKeyPressed() {
        return fKeyPressed;
    }

    public void setFKeyPressed(boolean fKeyPressed) {
        this.fKeyPressed = fKeyPressed;
    }

    private GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // the code is an int that represents which key has been pressed
        int code = e.getKeyCode();

        // checks which kinda state the game is in
        if (gp.getGameState() == GamePanel.TITLE_SCREEN) {
            switch (code) {
                case KeyEvent.VK_S -> gp.UI.incrementCommandNum();
                case KeyEvent.VK_W -> gp.UI.decrementCommandNum();
                case KeyEvent.VK_F -> gp.UI.selectOption();
            }
        } else if (gp.getGameState() == GamePanel.PLAY_STATE) {
            switch (code) {
                case KeyEvent.VK_P -> gp.pause();
                case KeyEvent.VK_W -> wKeyPressed = true;
                case KeyEvent.VK_S -> sKeyPressed = true;
                case KeyEvent.VK_A -> aKeyPressed = true;
                case KeyEvent.VK_D -> dKeyPressed = true;
                case KeyEvent.VK_F -> fKeyPressed = true;
            }
        } else if (gp.getGameState() == GamePanel.PAUSE_STATE) {
            if (code == KeyEvent.VK_P) {
                gp.unpause();
            }
        } else if (gp.getGameState() == GamePanel.DIAL0GUE_STATE) {
            if (code == KeyEvent.VK_F) {
                gp.unpause();
            }
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

    public boolean isKeyPressed() {
        return wKeyPressed || aKeyPressed || sKeyPressed || dKeyPressed;
    }
}
