package game.objects;

import game.engine.objects.AbstractGameObject;
import game.utils.Const;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RoomBackgroundGameObject extends AbstractGameObject {


    public RoomBackgroundGameObject(BufferedImage texture_) {
        super(0,0,0, 0, texture_);
    }

    @Override
    public int type() {
        return Const.TYPE_WORLD_BORDER;
    }
}
