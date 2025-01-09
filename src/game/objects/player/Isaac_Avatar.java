package game.objects.player;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.map.Isaac_World;
import game.utils.Const;
import game.utils.Isaac_TextureAvatar;

public class Isaac_Avatar extends AbstractGameObject {

    int lives = 100;
    double invincibletime=0;

    public Isaac_Avatar(double x, double y)
    { super(x,y,0,200,15, Isaac_TextureAvatar.avatarDefault);
        this.isMoving = false;
    }

    void debugPrintLive(){
        System.out.println("Current lives: " + lives);
    }

    public void move(double diffSeconds)
    {
        // move Avatar one step forward
        super.move(diffSeconds);

        if(invincibletime > 0)
            invincibletime -= diffSeconds;
        else if(invincibletime < 0)
            invincibletime = 0;

        // calculate all collisions with other Objects
        GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
        for(int i=0; i<collisions.size(); i++)
        {
            AbstractGameObject obj = collisions.get(i);

            // if Object is a tree, move back one step
            if(obj.type()== Const.TYPE_WALL)
            { this.moveBack(); }

            // pick up Grenades
            else if(obj.type()==Const.TYPE_GRENADE)
            { ((Isaac_World)world).addGrenade();
                obj.isLiving=false;
            }
            // pick up Hearts
            else if(obj.type()==Const.TYPE_HEART)
            {
                lives++;
                debugPrintLive();
                obj.isLiving=false;
            }
            // collode with zombies
            else if(obj.type()==Const.TYPE_ZOMBIE && invincibletime == 0)
            {
                lives--;
                invincibletime=Const.INVINCIBILITY_AFTER_HIT;
                debugPrintLive();
                if(lives <= 0){
                    world.gameOver=true;
                }

            }
        }
    }


    public int type() { return Const.TYPE_AVATAR; }
}
