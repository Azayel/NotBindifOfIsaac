package Game;

import Game.GameObject;
import Game.TextObject;

interface GraphicSystem
{
    // prepare to draw a new Screen
    void clear();

    // draw ONE GameObject on the Screen
    void draw(GameObject dot);

    // draw ONE TextObject on the Screen
    void draw(TextObject obj);

    // display the completed Screen
    void redraw();


    // set world
    void setWorld(World world);
}
