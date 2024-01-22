package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean wKeyPressed, sKeyPressed, aKeyPressed, dKeyPressed; // movement keys
    private boolean fKeyPressed; // interact key
    private boolean upKeyPressed, downKeyPressed, leftKeyPressed, rightKeyPressed; // attack keys

    public boolean isUpKeyPressed() {
        return upKeyPressed;
    }

    public boolean isDownKeyPressed() {
        return downKeyPressed;
    }

    public boolean isLeftKeyPressed() {
        return leftKeyPressed;
    }

    public boolean isRightKeyPressed() {
        return rightKeyPressed;
    }

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
            // decides what to do when the keys s w and f are clicked in the title screen
            switch (code) {
                case KeyEvent.VK_S -> gp.UI.incrementCommandNum(gp.UI.QUIT);
                case KeyEvent.VK_W -> gp.UI.decrementCommandNum(gp.UI.START);
                case KeyEvent.VK_F -> gp.UI.titleMenuOptions();
            }
        } else if (currentGameState == GamePanel.PLAY_STATE) {
            // decides what to do when any of the below keys are pressed in the play state
            switch (code) {
                case KeyEvent.VK_P -> gp.pause();
                case KeyEvent.VK_W -> wKeyPressed = true;
                case KeyEvent.VK_S -> sKeyPressed = true;
                case KeyEvent.VK_A -> aKeyPressed = true;
                case KeyEvent.VK_D -> dKeyPressed = true;
                case KeyEvent.VK_F -> fKeyPressed = true;
                case KeyEvent.VK_UP -> upKeyPressed = true;
                case KeyEvent.VK_DOWN -> downKeyPressed = true;
                case KeyEvent.VK_LEFT -> leftKeyPressed = true;
                case KeyEvent.VK_RIGHT -> rightKeyPressed = true;
                case KeyEvent.VK_E -> gp.lookAtStats();
            }
        } else if (currentGameState == GamePanel.PAUSE_STATE) {
            // unpauses the program
            if (code == KeyEvent.VK_P) {
                gp.unpause();
            }
        } else if (currentGameState == GamePanel.DIAL0GUE_STATE) {
            // unpauses the program after dialogue
            if (code == KeyEvent.VK_F) {
                gp.unpause();
            }
        } else if (currentGameState == GamePanel.EVENT_STATE) {
            // an event will keep going till its over, so it'll only bring u to the next dialogue
            if (code == KeyEvent.VK_F) {
                gp.nextDialogue();
            }
        } else if (currentGameState == GamePanel.CHOICE_STATE) {
            // f is the key to choose ur choice, and because there will only be 2 w and s will do the same thing pretty much
            switch (code) {
                case KeyEvent.VK_W, KeyEvent.VK_S -> gp.UI.alternateCommandNum();
                case KeyEvent.VK_F -> gp.processEvent();
            }
        } else if (currentGameState == GamePanel.RESULT_STATE) {
            // to let the player click f to unpause the game
            if (code == KeyEvent.VK_F) {
                gp.endEvent();
            }
        } else if (currentGameState == GamePanel.STATS_STATE) {
            // to let the player out of their stats
            if (code == KeyEvent.VK_E) {
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
            case KeyEvent.VK_UP -> upKeyPressed = false;
            case KeyEvent.VK_DOWN -> downKeyPressed = false;
            case KeyEvent.VK_LEFT -> leftKeyPressed = false;
            case KeyEvent.VK_RIGHT -> rightKeyPressed = false;
            case KeyEvent.VK_F -> fKeyPressed = false;
        }
    }

    public boolean isAMovementKeyPressed() {
        return wKeyPressed || aKeyPressed || sKeyPressed || dKeyPressed;
    }
    public boolean isACombatKeyPressed() {
        return upKeyPressed || downKeyPressed || leftKeyPressed || rightKeyPressed;
    }
}
