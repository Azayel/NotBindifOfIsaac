package game.engine.graphics;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.AbstractTextObject;
import game.engine.world.AbstractWorld;

public interface IGraphicSystem
{
    // prepare to draw a new Screen
    void clear();

    // draw ONE GameObject on the Screen
    void draw(AbstractGameObject dot);

    // draw ONE TextObject on the Screen
    void draw(AbstractTextObject obj);

    // display the completed Screen
    void redraw();


    // set world
    void setWorld(AbstractWorld world);
}
