package game.engine.physics;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.world.AbstractWorld;

public class PhysicsSystem extends AbstractPhysicsSystem
{

    public PhysicsSystem(AbstractWorld w)
    { super(w);
    }


    //
    // collisions for circle Objects only...
    //
    public GameObjectList getCollisions(AbstractGameObject object)
    {
        GameObjectList result = new GameObjectList();

        int len = world.gameObjects.size();
        for(int i=0; i<len; i++)
        {
            AbstractGameObject obj2 = world.gameObjects.get(i);

            // an object doesn't collide with itself
            if(obj2==object) continue;

            // check if they touch each other
            double dist = object.radius+obj2.radius;
            double dx   = object.x-obj2.x;
            double dy   = object.y-obj2.y;

            if(dx*dx+dy*dy < dist*dist)
            { result.add(obj2);
            }
        }

        return result;
    }
}
