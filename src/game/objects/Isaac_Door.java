package game.objects;

import game.engine.objects.AbstractGameObject;
import game.utils.Const;
import game.utils.Isaac_TextureEnvironment;

public class Isaac_Door extends AbstractGameObject {

    public boolean isUseable=false;

    public Isaac_Door(int x, int y, int radius) {
        super(x,y,0,0,radius, Isaac_TextureEnvironment.door, false);

    }




    @Override
    public int type() {
        return Const.TYPE_DOOR;
    }
}
