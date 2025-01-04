package Game;

import Game.World;

import java.awt.Color;


abstract class TextObject
{
    protected static World world;

    // yes, public :(
    protected int     x,y;
    protected Color color;

    public TextObject(int x_, int y_, Color color_)
    { x=x_; y=y_; color=color_;
    }

    public abstract String toString();

    protected static void setWorld(World w){world=w;}
}
