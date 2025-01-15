package game.objects.enemy;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.sound.SoundEngine;
import game.level.Isaac_Level;
import game.map.Isaac_World;
import game.objects.EnemyShot;
import game.objects.Healthbar.EnemyHealthBar;
import game.objects.Isaac_Shot;
import game.objects.items.Heart;
import game.objects.items.RedBooster;
import game.objects.items.YellowBooster;
import game.sound.Isaac_Sounds;
import game.utils.*;

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

    private double shootingRad = 700;

    private double timer;
    private double shootingTimer;
    private EnemyHealthBar healthBar;
    private double initialHealth = (Isaac_Level.instance.getLevel()+1)*100;
    private double health = initialHealth;
    private DroppableList droplist;
    int direction=1;

    private Random randomizer;

    private double oldAvatarX = 0;
    private double oldAvatarY = 0;

    public Boss(double x, double y) {
        super(x,y,0, 0, Isaac_TextureBoss.agis, true);
        current = State.SLEEPING;

        healthBar = new EnemyHealthBar(x,y);

        this.inertX = 0;
        this.inertY = 0;

        this.slipperiness = 1.8;

        this.timer = 0;
        droplist = new DroppableList();
        droplist.addItem(Heart.class);
        droplist.addItem(YellowBooster.class);
        droplist.addItem(RedBooster.class);
        this.shootingTimer = 0;

        randomizer = new Random();
    }

    public void tick(double diffSeconds) {
        super.tick(diffSeconds);
        this.makeDecision();
        this.processState();
        this.processMovement(diffSeconds);
        this.timer += diffSeconds;
        this.shootingTimer += diffSeconds;


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

            if((this.x +  this.boundingBox.width / 2) + step_x  < Const.WORLD_WIDTH && (this.x -  this.boundingBox.width / 2) + step_x > 0)
                x += step_x;
            if((this.y +  this.boundingBox.height / 2) + step_y < Const.WORLD_HEIGHT && (this.y -  this.boundingBox.height / 2) + step_y > 0) // calculate world borders
                y += step_y;

            this.boundingBox.setPosition(x-this.boundingBox.width/2, y-this.boundingBox.height/2);
    }

    private void processState() {
        switch(this.current) {
            case FOLLOWING:

                this.inertX=(Isaac_Level.instance.getCurrentRoom().maxXRoomSize/2- this.x);
                this.inertY=(Isaac_Level.instance.getCurrentRoom().maxYRoomSize/2- this.y);
                this.shootLaser();
                break;
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
                this.shootFire();
                break;
            case RUSHING:
                break;
        }
    }

    private void makeDecision() {
        switch (this.current) {
            case SHOOTING:
                this.speed = 170;
                if(this.timer > 10) {
                    this.current = State.CHARGING;
                    this.timer = 0;
                }
                break;
            case FOLLOWING:
                this.setDestination(Isaac_Level.instance.getCurrentRoom().maxXRoomSize/2,Isaac_Level.instance.getCurrentRoom().maxYRoomSize/2);
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
                this.speed = 800;
                if(this.timer > 1.5) {
                    this.current = State.FOLLOWING;
                    this.timer = 0;
                }
                break;
            case SLEEPING:
                this.speed = 0;
                if(this.timer > 3) {
                    this.current = State.FOLLOWING;
                    this.timer = 0;
                }
                break;
        }

    }

    private double distanceToPlayer() {
        return Math.sqrt(Math.pow(this.x - world.avatar.x, 2) + Math.pow(this.y - world.avatar.y, 2));
    }

    private void shootFire() {

        // To Do:
        // - make fly not so far
        // - implement collision logic
        // - implement timings
        if (this.shootingTimer > 0.4/ Math.pow((Isaac_Level.instance.getLevel()+1), 2)) {
            double rL = randomizer.nextDouble(0.8, 1.5);
            double rX = randomizer.nextDouble(-280, 280);
            double rY = randomizer.nextDouble(-280, 280);
            world.gameObjects.add(new EnemyShot(this.x, this.y, world.avatar.x + this.inertX + rX,  world.avatar.y + this.inertY + rY, (int) (450 * rL), Isaac_TextureBoss.fire, 1));
            this.shootingTimer = 0;
        }
    }
    private void shootFireCircle() {
        double r = 2000;
        double t = 5;
        // To Do:
        // - make fly not so far
        // - implement collision logic
        // - implement timings
        System.out.println((this.timer * 10) % 7);
        if((this.timer * 10) % 7 > 6.6) {
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, Const.WORLD_WIDTH, this.y,250, Isaac_TextureBoss.fire, 1));
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, 0, this.y,250, Isaac_TextureBoss.fire, 1));
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, this.x, Const.WORLD_HEIGHT,250, Isaac_TextureBoss.fire, 1));
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, this.x, 0,250, Isaac_TextureBoss.fire, 1));
        }

    }

    private void shootLaser() {
        double r = 20;
        double rotationSpeed = 1;
        // To Do:
        // - make fly not so far
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(rotationSpeed*this.timer)*direction) + this.x, r * Math.sin(rotationSpeed*this.timer) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(rotationSpeed*this.timer+Math.PI/2)*direction) + this.x, r * Math.sin(rotationSpeed*this.timer+Math.PI/2) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(rotationSpeed*this.timer+Math.PI)*direction) + this.x, r * Math.sin(rotationSpeed*this.timer+Math.PI) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, (r * Math.cos(rotationSpeed*this.timer+3*Math.PI/2)*direction) + this.x, r * Math.sin(rotationSpeed*this.timer+3*Math.PI/2) + this.y,2000, Isaac_TextureBoss.laser, 5));

    }

}
