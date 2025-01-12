package game.objects;

import game.engine.objects.AbstractGameObject;
import game.map.DoorDirection;
import game.utils.Const;
import game.utils.Isaac_TextureEnvironment;

public class Isaac_Door extends AbstractGameObject {



    public Isaac_Door(int x, int y, int radius, DoorDirection direction) {
        super(x,y,0,0,radius, Isaac_TextureEnvironment.door,direction);
    }


    @Override
    public void process(double diffSeconds) {
        // ToDo:
        // IMPLEMENT
    }

    @Override
    public int type() {
        return Const.TYPE_DOOR;
    }
}
