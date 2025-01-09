package game.engine.input;

import game.engine.NKeyList;

public final class UserInput
{
    // everything a user can press on keyboard or mouse
    int mousePressedX;
    int mousePressedY;
    public int mouseMovedX;
    public int mouseMovedY;
    public int mouseButton;

    public NKeyList keys = new NKeyList();

    // private PrintThread thread;

    // char keyPressed;

    // if Mouse was clicked, Key was pressed or Mouse is still hold down
    public boolean isMouseEvent;
    boolean isKeyEvent;
    public boolean isMousePressed;

    // ... is returned as a data set
    UserInput() {

        // thread = new PrintThread(keys);
        // thread.start();
        this.clear();
    }

    public final void clear() {
        isMouseEvent=false;
        isKeyEvent=false;
    }
}
