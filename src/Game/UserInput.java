package Game;

final class UserInput
{
    // everything a user can press on keyboard or mouse
    int mousePressedX, mousePressedY,
            mouseMovedX,   mouseMovedY, mouseButton;

    NKeyList keys = new NKeyList();

    // private PrintThread thread;

    // char keyPressed;

    // if Mouse was clicked, Key was pressed or Mouse is still hold down
    boolean isMouseEvent, isKeyEvent, isMousePressed;

    // ... is returned as a data set
    UserInput() {

        // thread = new PrintThread(keys);
        // thread.start();
        this.clear();
    }

    final void clear() {
        isMouseEvent=false;
        isKeyEvent=false;
    }
}
