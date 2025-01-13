package game.objects.items;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.AbstractInteractableObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Isaac_Chest extends AbstractInteractableObject {


    public Isaac_Chest(double x_, double y_, double a_, double s_, int radius_, Color color_) {
        super(x_, y_, a_, s_, radius_, color_);
    }

    @Override
    public void interact(AbstractGameObject avatar) {

    }

    @Override
    public int type() {
        return 0;
    }
}
