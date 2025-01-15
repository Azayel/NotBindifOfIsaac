package game.objects.enemy;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.sound.SoundEngine;
import game.map.Isaac_Room;
import game.map.Isaac_World;
import game.objects.Healthbar.EnemyHealthBar;
import game.objects.items.Heart;
import game.objects.items.RedBooster;
import game.objects.items.YellowBooster;
import game.sound.Isaac_Sounds;
import game.utils.DroppableList;

import java.awt.image.BufferedImage;
import java.util.Random;


public class Isaac_SpiderAI extends AbstractAnimatedGameObject implements IEnemy
{
    private static final int HUNTING  = 1;
    private static final int STUCK    = 2;
    private static final int CLEARING = 3;

    private int    state;
    private double alfaClear;
    private double secondsClear;

    // life of a zombie
    private final int INITIAL_LIFE = 20;
    private double life = INITIAL_LIFE;

    private EnemyHealthBar healthBar;
    private DroppableList droplist;

    public Isaac_SpiderAI(double x, double y,int radius,int speed, BufferedImage[] image)
    {
        super(x,y,0,speed, image,true);
        healthBar = new EnemyHealthBar(x,y);
        this.isMoving = false;

        state = HUNTING;

        // turn left or right to clear
        alfaClear = Math.PI;
        if(Math.random()<0.5) alfaClear = -alfaClear;
        droplist = new DroppableList();
        droplist.addItem(Heart.class);
        droplist.addItem(YellowBooster.class);
        droplist.addItem(RedBooster.class);

    }

    double timer=0;
    double goal = 1.5;
    public void tick(double diffSeconds)
    {
        super.tick(diffSeconds);
        timer+=diffSeconds;

        if(timer>=goal){
            timer=0;
            speed=new Random().nextInt(Isaac_Room.maxEnemySpeed-Isaac_Room.minEnemySpeed)+Isaac_Room.minEnemySpeed;
            goal=new Random().nextDouble(10);
            if(Math.random()<0.1) {
                SoundEngine.instance.playSound(Isaac_Sounds.SpiderAttack);
                speed = 800;
                goal = 0.5;
            }
        }
        if(!world.gameObjects.contains(healthBar))
            world.gameObjects.add(healthBar);

        healthBar.setX(x);
        healthBar.setY(y-32);
        // if avatar is too far away: stop
        double dist = world.getPhysicsSystem()
                .distance(x,y,world.avatar.x,world.avatar.y);

        if(dist > 8000)
        { this.isMoving=false;
            return;
        }
        else
        { this.isMoving = true;
        }


        // state HUNTING
        //

        if(state==HUNTING)
        {
            this.setDestination(world.avatar);

            this.processMovement(diffSeconds);
        }
        // state CLEARING
        //
        else if(state==CLEARING)
        {
            // check, if the clearing time has ended
            secondsClear -= diffSeconds;
            if(secondsClear<0)
            {
                state = HUNTING;
                return;
            }


            // try step in this direction
            this.processMovement(diffSeconds);
        }

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
        x += step_x;
        y += step_y;
        this.boundingBox.move(step_x, step_y);
    }

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public void hit(double damageAmount) {
        // every shot decreases life
        life -= damageAmount;
        healthBar.setHealth(((double) life/INITIAL_LIFE));

        // Slow down
        speed=35;
        goal=1;
        // if Zombie is dead (haha), delete it
        if(life<=0)
        {
            healthBar.remove();
            ((Isaac_World)world).addScore(10);
            this.isLiving=false;
            SoundEngine.instance.playSound(Isaac_Sounds.SpiderDeath);
            if(Math.random()<=0.15){
                AbstractGameObject item = droplist.getItem(x,y);
                if(item!=null)
                    world.gameObjects.add(item);
            }
            return;
        }
    }
}
