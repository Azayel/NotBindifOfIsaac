package game.objects;

import game.engine.objects.AbstractGameObject;
import game.utils.Const;

import java.awt.Color;

public class Isaac_Grenade extends AbstractGameObject
{
    double life = Const.LIFE_GRENADE;

    public Isaac_Grenade(double x, double y)
    {
        super(x,y,0,0,15,Color.ORANGE);
    }

    @Override
    public void tick(double diffSeconds) {
        // ToDo:
        // IMPLEMENT
        life -= diffSeconds;
        if(life<0)
        { this.isLiving=false;
            return;
        }
    }

    public int type() { return Const.TYPE_GRENADE; }
}
