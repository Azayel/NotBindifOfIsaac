package game.objects;

import game.engine.objects.AbstractGameObject;
import game.utils.Const;

import java.awt.*;

public class RoomBackgroundGameObject extends AbstractGameObject {


    public RoomBackgroundGameObject(Image texture_) {
        super(texture_);
    }

    @Override
    public void process(double diffSeconds) {
        // ToDo:
        // IMPLEMENT
    }

    @Override
    public int type() {
        return Const.TYPE_WORLD_BORDER;
    }
}
