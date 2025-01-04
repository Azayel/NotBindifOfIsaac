package Game;

final class UserInput
{
    // everything a user can press on keyboard or mouse
    int mousePressedX, mousePressedY,
            mouseMovedX,   mouseMovedY, mouseButton;

    char keyPressed;

    // if Mouse was clicked, Key was pressed or Mouse is still hold down
    boolean isMouseEvent, isKeyEvent, isMousePressed;
    public boolean wIsPressed = false;
    public boolean aIsPressed = false;
    public boolean sIsPressed = false;
    public boolean dIsPressed = false;

    // ... is returned as a data set
    UserInput()
    { this.clear();
    }

    final void clear()
    { isMouseEvent=false; isKeyEvent=false;
    }
}
