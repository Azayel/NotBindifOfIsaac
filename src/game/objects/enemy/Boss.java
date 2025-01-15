package game.objects.enemy;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.sound.SoundEngine;
import game.map.Isaac_Room;
import game.map.Isaac_World;
import game.objects.EnemyShot;
import game.objects.Healthbar.EnemyHealthBar;
import game.objects.Isaac_Shot;
import game.objects.items.Heart;
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

    private double shootingRad = 700;

    private double timer;
    private double shootingTimer;
    private EnemyHealthBar healthBar;
    private double initialHealth = 500;
    private double health = initialHealth;

    private Random randomizer;

    private double oldAvatarX = 0;
    private double oldAvatarY = 0;

    public Boss(double x, double y) {
        super(x,y,0, 0, Isaac_TextureBoss.agis, true);
        current = State.FOLLOWING;

        healthBar = new EnemyHealthBar(x,y);

        this.inertX = 0;
        this.inertY = 0;

        this.slipperiness = 1.8;

        this.timer = 0;
        this.shootingTimer = 0;

        randomizer = new Random();
    }

    public void tick(double diffSeconds) {
        super.tick(diffSeconds);
        this.makeDesicion();
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
            return;
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
                this.shootLaser();
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
        if (this.shootingTimer > 0.02) {
            double rL = randomizer.nextDouble(0.8, 1.5);
            double rX = randomizer.nextDouble(-280, 280);
            double rY = randomizer.nextDouble(-280, 280);
            world.gameObjects.add(new EnemyShot(this.x, this.y, world.avatar.x + this.inertX + rX,  world.avatar.y + this.inertY + rY, (int) (450 * rL), Isaac_TextureBoss.fire, 0));
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
            for(int i = 0; i < 8; i++) {

            }
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, Const.WORLD_WIDTH, this.y,250, Isaac_TextureBoss.fire, 0));
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, 0, this.y,250, Isaac_TextureBoss.fire, 0));
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, this.x, Const.WORLD_HEIGHT,250, Isaac_TextureBoss.fire, 0));
            world.gameObjects.add(new Isaac_Shot(this.x, this.y, this.x, 0,250, Isaac_TextureBoss.fire, 0));
        }

    }

    private void shootLaser() {
        double r = 20;
        double rotationSpeed = 1.3;
        // To Do:
        // - make fly not so far
        world.gameObjects.add(new EnemyShot(this.x, this.y, - r * Math.cos(rotationSpeed * this.timer) + this.x, - r * Math.sin(rotationSpeed * this.timer) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, r * Math.cos(rotationSpeed * this.timer) + this.x, r * Math.sin(rotationSpeed * this.timer) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, r * Math.sin(rotationSpeed * this.timer) + this.x, - r * Math.cos(rotationSpeed * this.timer) + this.y,2000, Isaac_TextureBoss.laser, 5));
        world.gameObjects.add(new EnemyShot(this.x, this.y, - r * Math.sin(rotationSpeed * this.timer) + this.x, r * Math.cos(rotationSpeed * this.timer) + this.y,2000, Isaac_TextureBoss.laser, 5));

        // Add random bullets in direction of player
    }
}
