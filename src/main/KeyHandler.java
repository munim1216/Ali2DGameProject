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

        int currentGameState = gp.getGameState();

        // checks which kinda state the game is in
        if (currentGameState == GamePanel.TITLE_SCREEN) {
            switch (code) {
                case KeyEvent.VK_S -> gp.UI.incrementCommandNum(gp.UI.QUIT);
                case KeyEvent.VK_W -> gp.UI.decrementCommandNum(gp.UI.START);
                case KeyEvent.VK_F -> gp.UI.titleMenuOptions();
            }
        } else if (currentGameState == GamePanel.PLAY_STATE) {
            switch (code) {
                case KeyEvent.VK_P -> gp.pause();
                case KeyEvent.VK_W -> wKeyPressed = true;
                case KeyEvent.VK_S -> sKeyPressed = true;
                case KeyEvent.VK_A -> aKeyPressed = true;
                case KeyEvent.VK_D -> dKeyPressed = true;
                case KeyEvent.VK_F -> fKeyPressed = true;
            }
        } else if (currentGameState == GamePanel.PAUSE_STATE) {
            if (code == KeyEvent.VK_P) {
                gp.unpause();
            }
        } else if (currentGameState == GamePanel.DIAL0GUE_STATE) {
            if (code == KeyEvent.VK_F) {
                gp.unpause();
            }
        } else if (currentGameState == GamePanel.EVENT_STATE) {
            if (code == KeyEvent.VK_F) {
                gp.nextDialogue();
            }
        } else if (currentGameState == GamePanel.CHOICE_STATE) {
            System.out.println("ACCESSIBLE");
            switch (code) {
                case KeyEvent.VK_W, KeyEvent.VK_S -> gp.UI.alternateCommandNum();
                case KeyEvent.VK_F -> gp.processEvent();
            }
        } else if (currentGameState == GamePanel.RESULT_STATE) {
            if (code == KeyEvent.VK_F) {
                gp.endEvent();
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
