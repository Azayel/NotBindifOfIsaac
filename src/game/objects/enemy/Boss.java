package game.objects.enemy;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.sound.SoundEngine;
import game.map.Isaac_Room;
import game.map.Isaac_World;
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

    private double shootingRad = 350;

    private double timer;

    public Boss(double x, double y, int radius) {
        super(x,y,0, 0, Isaac_TextureBoss.agis, true);
        current = State.FOLLOWING;

        this.inertX = 0;
        this.inertY = 0;

        this.slipperiness = 1.8;

        this.timer = 0;
    }

    public void tick(double diffSeconds) {
        super.tick(diffSeconds);
        this.makeDesicion();
        this.processState();
        this.processMovement(diffSeconds);
        this.timer += diffSeconds;
    }


    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public void hit(double damageAmount) {

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

            // this.boundingBox.setPosition(x, y);
    }

    private void processState() {
        switch(this.current) {
            case State.FOLLOWING:
            case State.CHARGING:
                this.inertX = (world.avatar.x - this.x) / this.distanceToPlayer();
                this.inertY = (world.avatar.y - this.y) / this.distanceToPlayer();
                break;
            case State.SHOOTING:
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
            case State.RUSHING:
                break;
        }
    }

    private void makeDesicion() {
        switch (this.current) {
            case State.SHOOTING:
                this.speed = 170;
                if(this.timer > 10) {
                    this.current = State.CHARGING;
                    this.timer = 0;
                }
                break;
            case State.FOLLOWING:
                this.speed = 90;
                if(this.timer > 10) {
                    this.current = State.SHOOTING;
                    this.timer = 0;
                }
                break;
            case State.CHARGING:
                this.speed = 10;
                if(this.timer > 3) {
                    this.current = State.RUSHING;
                    this.inertX = (world.avatar.x - this.x) / this.distanceToPlayer();
                    this.inertY = (world.avatar.y - this.y) / this.distanceToPlayer();
                    this.timer = 0;
                }
                break;
            case State.RUSHING:
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
        // - implement collision logic
        world.gameObjects.add(new Isaac_Shot(this.x, this.y, world.avatar.x,world.avatar.y,400, Isaac_TextureBoss.fire, 0));
    }
    private void shootLaser() {
        double r = 20;
        // To Do:
        // - make fly not so far
        // - implement collision logic
        world.gameObjects.add(new Isaac_Shot(this.x, this.y, r * Math.cos(this.timer) + this.x, r * Math.sin(this.timer) + this.y,2000, Isaac_TextureBoss.laser, 0));
    }
}
