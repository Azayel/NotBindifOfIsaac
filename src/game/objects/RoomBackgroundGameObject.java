package game.objects;

import game.engine.objects.AbstractGameObject;
import game.utils.Const;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RoomBackgroundGameObject extends AbstractGameObject {


    public RoomBackgroundGameObject(BufferedImage texture_) {
        super(texture_.getWidth()/2,texture_.getHeight()/2,0, 0, texture_);
    }
}
