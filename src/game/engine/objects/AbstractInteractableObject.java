package game.engine.objects;

import game.map.TeleporterDestination;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractInteractableObject extends AbstractGameObject{
    public AbstractInteractableObject(double x_, double y_, double a_, double s_, int radius_, Color color_) {
        super(x_, y_, a_, s_, radius_, color_);
    }

    public AbstractInteractableObject(double x_, double y_, double a_, double s_, BufferedImage texture_) {
        super(x_, y_, a_, s_, texture_);
    }
    public abstract void interact(AbstractGameObject avatar);
}
