package game.objects;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.utils.Const;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Isaac_Shot extends AbstractGameObject
{
    private double lifeTime = 1.2;

    public Isaac_Shot(double x, double y, double xDest, double yDest, int speed ,BufferedImage image)
    {
        super(x,y,Math.atan2(yDest-y, xDest-x),speed,16, image);
        this.isMoving = true;
    }

    public Isaac_Shot(double x, double y, double xDest, double yDest)
    {
        super(x,y,Math.atan2(yDest-y, xDest-x),500,4,Color.YELLOW);
        this.isMoving = true;
    }

    public Isaac_Shot(double x, double y, double a, double s, double time, BufferedImage image)
    { super(x,y,a,s,4,image);
        lifeTime = time;
        this.isMoving = true;
    }

    public Isaac_Shot(double x, double y, double a, double s, double time)
    { super(x,y,a,s,4,Color.YELLOW);
        lifeTime = time;
        this.isMoving = true;
    }


    public void tick(double diffSeconds)
    {
        lifeTime -= diffSeconds;
        if(lifeTime<=0)
        { this.isLiving=false;
            return;
        }


        // handle collisions of the zombie
        GameObjectList collisions = world.getPhysicsSystem().getCollisions(this);
        for(int i=0; i<collisions.size(); i++)
        {
            AbstractGameObject obj = collisions.get(i);

            int type = obj.type();

            // tree: shot is deleted
            if(type== Const.TYPE_TREE)
            { this.isLiving=false;
            }
            // Zombie: inform Zombie it is hit
            else if(type==Const.TYPE_ZOMBIE && obj.isLiving)
            {
                Isaac_ZombieAI zombie = (Isaac_ZombieAI)obj;
                zombie.hasBeenShot();
                this.isLiving=false;
            }
        }

        this.processMovement(diffSeconds);
    }

    public void processMovement(double diffSeconds) {
        if(!isMoving) return;

        // move if object has a destination
        if(hasDestination)
        {
            // stop if destination is reached
            double diffX = Math.abs(x-destX);
            double diffY = Math.abs(y-destY);
            if(diffX<3 && diffY<3)
            { isMoving = false;
                return;
            }
        }

        // remember old position
        xOld=x; yOld=y;

        // move one step
        double step_x = Math.cos(alfa)*speed*diffSeconds;
        double step_y = Math.sin(alfa)*speed*diffSeconds;
        x += step_x;
        y += step_y;
        this.boundingBox.move(step_x, step_y);
//        double step_x = speed*diffSeconds*inertX;
//
//        double step_y = speed*diffSeconds*inertY;
//
//        inertX /= slipperiness;
//        inertY /= slipperiness;
//
//        System.out.println(x);
//
//        if(x + step_x + 45 < Const.WORLD_WIDTH && x + step_x - 45 > 0)
//            x += step_x;
//        if(y + step_y + 45 < Const.WORLD_HEIGHT && y + step_y - 45 > 0) // calculate world borders
//            y += step_y;
//
//        this.boundingBox.setPosition(x, y);
    }

    public final int type() { return Const.TYPE_SHOT;}
}
