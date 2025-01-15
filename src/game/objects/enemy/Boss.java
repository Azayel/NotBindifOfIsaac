package game.objects.enemy;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.sound.SoundEngine;
import game.level.Isaac_Level;
import game.map.Isaac_Room;
import game.map.Isaac_World;
import game.objects.EnemyShot;
import game.objects.Healthbar.EnemyHealthBar;
import game.objects.Isaac_Shot;
import game.objects.items.Heart;
import game.objects.items.RedBooster;
import game.objects.items.YellowBooster;
import game.sound.Isaac_Sounds;
import game.utils.*;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Boss extends AbstractAnimatedGameObject implements IEnemy {
    enum State {
        SHOOTING,
        FOLLOWING,
        CHARGING,
        RUSHING,
        SLEEPING
    }

    private State current;

    private double inertX;
    private double inertY;

    private double slipperiness;

    private double shootingRad = 350;

    private double timer;
    private EnemyHealthBar healthBar;
    private double initialHealth = (Isaac_Level.instance.getLevel()+1)*100;
    private double health = initialHealth;
    private DroppableList droplist;
    int direction=1;

    public Boss(double x, double y) {
        super(x,y,0, 0, Isaac_TextureBoss.agis, true);
        current = State.FOLLOWING;

        healthBar = new EnemyHealthBar(x,y);

        this.inertX = 0;
        this.inertY = 0;

        this.slipperiness = 1.8;

        this.timer = 0;

        droplist = new DroppableList();
        droplist.addItem(Heart.class);
        droplist.addItem(YellowBooster.class);
        droplist.addItem(RedBooster.class);
    }

    public void tick(double diffSeconds) {
        super.tick(diffSeconds);
        this.makeDesicion();
        this.processState();
        this.processMovement(diffSeconds);
        this.timer += diffSeconds;


        if(!world.gameObjects.contains(healthBar))
            world.gameObjects.add(healthBar);

        healthBar.setX(x);
        healthBar.setY(y-128);
    }


    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public void hit(double damageAmount) {
        // every shot decreases life
        health -= damageAmount;
        healthBar.setHealth(((double) health/initialHealth));

        if(health<=0)
        {
            healthBar.remove();
            ((Isaac_World)world).addScore(1000);
            this.isLiving=false;
            SoundEngine.instance.playSound(Isaac_Sounds.BossDeath);
            for (int xOff = -1; xOff < 2; xOff+=2) {
                for (int yOff = -1; yOff < 2; yOff+=2) {
                    AbstractGameObject item = droplist.getItem(x+(xOff*20),y+(yOff*20));
                    if(item!=null)
                        world.gameObjects.add(item);
                }
            }
        }
    }

    private void processMovement(double diffSeconds) {
            double step_x = speed*diffSeconds*inertX;

            double step_y = speed*diffSeconds*inertY;

            if(this.current != State.RUSHING) {
                inertX /= slipperiness;
                inertY /= slipperiness;
            }
            if(Const.DEBUG_PRINTS)
                System.out.println(x);

            if(x + step_x + 45 < Const.WORLD_WIDTH && x + step_x - 45 > 0)
                x += step_x;
            if(y + step_y + 45 < Const.WORLD_HEIGHT && y + step_y - 45 > 0) // calculate world borders
                y += step_y;

            this.boundingBox.setPosition(x-this.boundingBox.width/2, y-this.boundingBox.height/2);
    }

    private void processState() {
        switch(this.current) {
            case FOLLOWING:
            case CHARGING:
                this.inertX = (world.avatar.x - this.x) / this.distanceToPlayer();
                this.inertY = (world.avatar.y - this.y) / this.distanceToPlayer();
                break;
            case SHOOTING:
                double d = this.distanceToPlayer();
                // System.out.println(d);
                double dirX = 0;
                double dirY = 0;
                if(d > this.shootingRad) {
                    dirX = world.avatar.x - this.x;
                    dirY = world.avatar.y - this.y;
                } else {
                    dirX = world.avatar.y - this.y;
                    dirY = -(world.avatar.x - this.x);

                }
                this.inertX = dirX / this.distanceToPlayer();
                this.inertY = dirY / this.distanceToPlayer();

                this.shootLaser();

                break;
            case RUSHING:
                break;
        }
    }

    private void makeDesicion() {
        switch (this.current) {
            case SHOOTING:
                this.speed = 170;
                if(this.timer > 10) {
                    this.current = State.CHARGING;
                    this.timer = 0;
                }
                break;
            case FOLLOWING:
                this.speed = 90;
                if(this.timer > 10) {
                    direction*=-1;
                    this.current = State.SHOOTING;
                    this.timer = 0;
                }
                break;
            case CHARGING:
                this.speed = 10;
                if(this.timer > 3) {
                    this.current = State.RUSHING;
                    this.inertX = (world.avatar.x - this.x) / this.distanceToPlayer();
                    this.inertY = (world.avatar.y - this.y) / this.distanceToPlayer();
                    this.timer = 0;
                }
                break;
            case RUSHING:
                this.speed = 600;
                if(this.timer > 1.5) {
                    this.current = State.FOLLOWING;
                    this.timer = 0;
                }
        }

    }

    private double distanceToPlayer() {
        return Math.sqrt(Math.pow(this.x - world.avatar.x, 2) + Math.pow(this.y - world.avatar.y, 2));
    }

    private void shootFire() {
        // To Do:
        // - make fly not so far
        world.gameObjects.add(new EnemyShot(this.x, this.y, world.avatar.x,world.avatar.y,400, Isaac_TextureBoss.fire, 10));
    }
    private void shootLaser() {
        double r = 20;
        // To Do:
        // - make fly not so far
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(this.timer)*direction) + this.x, r * Math.sin(this.timer) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(this.timer+Math.PI/2)*direction) + this.x, r * Math.sin(this.timer+Math.PI/2) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(this.timer+Math.PI)*direction) + this.x, r * Math.sin(this.timer+Math.PI) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(this.timer+3*Math.PI/2)*direction) + this.x, r * Math.sin(this.timer+3*Math.PI/2) + this.y,2000, Isaac_TextureBoss.laser, 5));


    }
}
