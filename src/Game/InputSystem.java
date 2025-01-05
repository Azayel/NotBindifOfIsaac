package Game;

import Game.UserInput;

import java.awt.event.*;

class InputSystem implements KeyListener, MouseListener, MouseMotionListener {

    // UserInput variables
    private Game.UserInput userInput = new Game.UserInput();

    public void mousePressed(MouseEvent evt) {
        // an input Event occurs
        userInput.isMouseEvent      = true;
        userInput.mousePressedX     = evt.getX();
        userInput.mousePressedY     = evt.getY();
        userInput.mouseButton       = evt.getButton();
        userInput.isMousePressed    = true;
    }

    public void mouseReleased(MouseEvent evt) {
        userInput.isMousePressed = false;
    }


    public void mouseMoved(MouseEvent evt) {
        userInput.mouseMovedX=evt.getX();
        userInput.mouseMovedY=evt.getY();
    }


    public void mouseDragged(MouseEvent evt) {
        userInput.mouseMovedX=evt.getX();
        userInput.mouseMovedY=evt.getY();
    }


    public void keyPressed(KeyEvent evt) {
        userInput.isKeyEvent = true;
        char c = evt.getKeyChar();
        userInput.keys.add(c);
    }


    public void mouseEntered(MouseEvent evt){}
    public void mouseExited(MouseEvent evt){}
    public void mouseClicked(MouseEvent evt){}
    public void keyReleased(KeyEvent evt){
        char c = evt.getKeyChar();
        userInput.keys.remove(c);
    }

    public void keyTyped(KeyEvent evt){}


    public Game.UserInput getUserInput() {
        return userInput;
    }
}
