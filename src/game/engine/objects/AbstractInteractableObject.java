package game.engine.objects;

import game.map.DoorDirection;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractInteractableObject extends AbstractGameObject{
    public AbstractInteractableObject(double x_, double y_, double a_, double s_, int radius_, Color color_) {
        super(x_, y_, a_, s_, radius_, color_);
    }

    public AbstractInteractableObject(double x_, double y_, double a_, double s_, int radius_, BufferedImage texture_) {
        super(x_, y_, a_, s_, radius_, texture_);
    }

    public AbstractInteractableObject(double x_, double y_, double a_, double s_, int radius_, Image texture_) {
        super(x_, y_, a_, s_, radius_, texture_);
    }

    public AbstractInteractableObject(double x_, double y_, double a_, double s_, int radius_, Image texture_, DoorDirection doorDirection) {
        super(x_, y_, a_, s_, radius_, texture_, doorDirection);
    }

    public AbstractInteractableObject(Image texture_) {
        super(texture_);
    }

    public abstract void interact(AbstractGameObject avatar);
}
