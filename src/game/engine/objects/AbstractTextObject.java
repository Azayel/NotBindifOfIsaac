package game.engine.objects;


import game.engine.world.AbstractWorld;

import java.awt.Color;


public abstract class AbstractTextObject
{
    protected static AbstractWorld world;

    // yes, public :(
    public int     x;
    public int y;
    public Color color;

    public AbstractTextObject(int x_, int y_, Color color_)
    { x=x_; y=y_; color=color_;
    }

    public abstract String toString();

    protected static void setWorld(AbstractWorld w){world=w;}
}
