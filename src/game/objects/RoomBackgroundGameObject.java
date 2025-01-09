package game.objects;

import game.engine.objects.AbstractGameObject;

import java.awt.*;

public class RoomBackgroundGameObject extends AbstractGameObject {


    public RoomBackgroundGameObject(Image texture_) {
        super(texture_);
    }

    @Override
    public int type() {
        return 0;
    }
}
