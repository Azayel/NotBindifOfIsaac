package game.engine.physics;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.world.AbstractWorld;

public abstract class AbstractPhysicsSystem
{
    protected AbstractWorld world;

    public AbstractPhysicsSystem(AbstractWorld w)
    { world = w;
    }

    public abstract GameObjectList getCollisions(AbstractGameObject object);


    public double distance(double x1, double y1, double x2, double y2)
    {
        double xd = x1-x2;
        double yd = y1-y2;
        return Math.sqrt(xd*xd+yd*yd);
    }


    //
    // move object "back" reverse alfa until it just does not collide
    //
    public void moveBackToUncollide(AbstractGameObject object)
    {
        double dx = Math.cos(object.alfa);
        double dy = Math.sin(object.alfa);

        while(true)
        {
            object.x -= dx;
            object.y -= dy;

            GameObjectList collisions = getCollisions(object);
            if(collisions.isEmpty()) break;
        }
    }

}
