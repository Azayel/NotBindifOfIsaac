package game.objects.items;

import game.engine.objects.AbstractGameObject;
import game.map.DoorDirection;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Isaac_Chest extends AbstractGameObject {



    public Isaac_Chest(double x_, double y_, double a_, double s_, int radius_, BufferedImage texture_) {
        super(x_, y_, a_, s_, radius_, texture_);
    }

    public Isaac_Chest(double x_, double y_, double a_, double s_, int radius_, Image texture_) {
        super(x_, y_, a_, s_, radius_, texture_);
    }

    public Isaac_Chest(Image texture_) {
        super(texture_);
    }

    @Override
    public void process(double diffSeconds) {

    }

    @Override
    public int type() {
        return 0;
    }
}
