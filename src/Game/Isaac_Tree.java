package Game;

import Game.Const;
import Game.GameObject;

import java.awt.Color;

class Isaac_Tree extends GameObject
{
    public Isaac_Tree(double x, double y, int r)
    {
        super(x,y,0,0,r,new Color(64,160,64));
        this.isMoving = false;
    }

    public int type() { return Const.TYPE_TREE; }
}
