package Game;

import Game.*;
import Game.AvatarTexture;

import javax.swing.*;
import java.awt.*;

public class Player_Avatar extends GameObject {


    public Player_Avatar(double x, double y)
    { super(x,y,0,200,15, AvatarTexture.avatarDefault);
        this.isMoving = false;
    }

    public void move(double diffSeconds)
    {
        // move Avatar one step forward
        super.move(diffSeconds);

        // calculate all collisions with other Objects
        GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
        for(int i=0; i<collisions.size(); i++)
        {
            GameObject obj = collisions.get(i);

            // if Object is a tree, move back one step
            if(obj.type()== Const.TYPE_TREE)
            { this.moveBack(); }

            // pick up Grenades
            else if(obj.type()==Const.TYPE_GRENADE)
            { ((Isaac_World)world).addGrenade();
                obj.isLiving=false;
            }
        }
    }


    public int type() { return Const.TYPE_AVATAR; }
}
