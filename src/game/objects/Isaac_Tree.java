package game.objects;

import game.engine.objects.AbstractGameObject;
import game.utils.Const;

import java.awt.Color;

class Isaac_Tree extends AbstractGameObject
{
    public Isaac_Tree(double x, double y, int r)
    {
        super(x,y,0,0,r,new Color(64,160,64));
        this.isMoving = false;
    }
}
