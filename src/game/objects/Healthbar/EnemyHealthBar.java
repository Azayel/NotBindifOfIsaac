package game.objects.Healthbar;

import game.engine.objects.AbstractGameObject;
import game.utils.Isaac_TextureHealthBar;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyHealthBar extends AbstractGameObject {
    AbstractGameObject bar = new AbstractGameObject(x, y, alfa, speed, Isaac_TextureHealthBar.HealthBar) {
        @Override
        public void tick(double diffSeconds) {
            super.tick(diffSeconds);
        }
    };
    public EnemyHealthBar(double x_, double y_) {
        super(x_, y_, 0, 0, Isaac_TextureHealthBar.HealthBarBackground);
        world.gameObjects.add(bar);
        //Scale Everythin
        getBoundingBox().width=getBoundingBox().width/2;
        getBoundingBox().height=getBoundingBox().height/2;
        bar.getBoundingBox().width=getBoundingBox().width;
        bar.getBoundingBox().height=getBoundingBox().height;
    }

    @Override
    public void tick(double diffSeconds) {
        super.tick(diffSeconds);
        if(!world.gameObjects.contains(bar))
            world.gameObjects.add(bar);
        bar.setX(x- (getBoundingBox().width - bar.getBoundingBox().width )/2 + (getBoundingBox().width - bar.getBoundingBox().width )/8);
        bar.setY(y);
    }

    public void setHealth(double percent){
        //bar.texture = Isaac_TextureHealthBar.HealthBar.getScaledInstance((int)(texture.getWidth(null)*percent), texture.getHeight(null), Image.SCALE_DEFAULT);
        bar.getBoundingBox().width= (int)(getBoundingBox().width*percent);
        bar.setX(x- (getBoundingBox().width - bar.getBoundingBox().width )/2 + (getBoundingBox().width - bar.getBoundingBox().width )/8);
        bar.setY(y);
    }

    public void remove(){
        bar.isLiving=false;
        isLiving=false;
    }
}
