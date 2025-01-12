package game.objects.items;

import game.engine.objects.AbstractGameObject;
import game.utils.Const;

import java.awt.*;

public class Heart extends AbstractGameObject {
    public Heart(double x_, double y_) {
        super(x_, y_, 100, 0, 5, Color.RED);
    }

    @Override
    public void process(double diffSeconds) {
        // ToDo:
        // IMPLEMENT
    }

    @Override
    public int type() {
        return Const.TYPE_HEART;
    }
}
