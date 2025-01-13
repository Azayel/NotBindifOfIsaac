package game.engine.objects;

import java.awt.image.BufferedImage;

public abstract class AbstractAnimatedGameObject extends AbstractGameObject {

    BufferedImage[] textures;
    int counter = 0;
    double frameTime = 0.25;
    double timer = 0;
    boolean loop = false;

    public AbstractAnimatedGameObject(double x_, double y_, double a_, double s_, BufferedImage[] texture_, boolean loop_) {
        super(x_, y_, a_, s_, texture_[0]);
        loop = loop_;
        textures=texture_;
    }

    public AbstractAnimatedGameObject(double x_, double y_, double a_, double s_, BufferedImage[] texture_) {
        super(x_, y_, a_, s_, texture_[0]);
        loop = false;
        textures=texture_;
    }



    @Override
    public void tick(double diffSeconds) {
        timer+=diffSeconds;
        if(timer > frameTime) {
            timer -= frameTime;
            counter++;
            if(counter >= textures.length) {
                if(loop) {
                    counter = 0;
                }else {
                    counter = textures.length-1;
                }

            }
            this.texture = textures[counter];
        }

    }
}
