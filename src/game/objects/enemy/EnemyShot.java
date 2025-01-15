package game.objects;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.sound.SoundEngine;
import game.objects.enemy.IEnemy;
import game.sound.Isaac_Sounds;
import game.utils.Const;

import java.awt.image.BufferedImage;

public class EnemyShot extends AbstractAnimatedGameObject implements IEnemy
{
    private double lifeTime = 2.4;
    private double damage;

    public EnemyShot(double x, double y, double xDest, double yDest, int speed, BufferedImage[] textures, double damage)
    {
        super(x,y,Math.atan2(yDest-y, xDest-x),speed, textures);
        this.isMoving = true;
        this.damage=damage;
    }

    public void tick(double diffSeconds)
    {
        super.tick(diffSeconds);
        lifeTime -= diffSeconds;
        if(lifeTime<=0)
        { this.isLiving=false;
            return;
        }
        this.processMovement(diffSeconds);
    }

    public void processMovement(double diffSeconds) {
        if(!isMoving) return;

        // move if object has a destination
        if(hasDestination)
        {
            // stop if destination is reached
            double diffX = Math.abs(x-destX);
            double diffY = Math.abs(y-destY);
            if(diffX<3 && diffY<3)
            { isMoving = false;
                return;
            }
        }

        // remember old position
        xOld=x; yOld=y;

        // move one step
        double step_x = Math.cos(alfa)*speed*diffSeconds;
        double step_y = Math.sin(alfa)*speed*diffSeconds;
        if(x + step_x + 10 < Const.WORLD_WIDTH && x + step_x - 10 > 0) {
            x += step_x;
        } else {
            this.isLiving=false;
            return;
        }
        if(y + step_y + 10 < Const.WORLD_HEIGHT && y + step_y - 10 > 0) {
            y += step_y;
        } else {
            this.isLiving=false;
            return;
        }
        this.boundingBox.move(step_x, step_y);
//        double step_x = speed*diffSeconds*inertX;
//
//        double step_y = speed*diffSeconds*inertY;
//
//        inertX /= slipperiness;
//        inertY /= slipperiness;
//
//        System.out.println(x);
//
//        if(x + step_x + 45 < Const.WORLD_WIDTH && x + step_x - 45 > 0)
//            x += step_x;
//        if(y + step_y + 45 < Const.WORLD_HEIGHT && y + step_y - 45 > 0) // calculate world borders
//            y += step_y;
//
//        this.boundingBox.setPosition(x, y);
    }

    @Override
    public int getDamage() {
        return (int) damage;
    }

    @Override
    public void hit(double damageAmount) {
        isLiving = false;
    }
}
