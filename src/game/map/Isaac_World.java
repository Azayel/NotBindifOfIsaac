package game.map;


import game.engine.input.UserInput;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.AbstractTextObject;
import game.engine.objects.GameObjectList;
import game.objects.enemy.IEnemy;
import game.engine.world.AbstractWorld;
import game.level.Isaac_Level;
import game.objects.RoomBackgroundGameObject;
import game.engine.sound.SoundEngine;
import game.objects.*;
import game.objects.enemy.Isaac_SpiderAI;
import game.objects.items.Heart;
import game.objects.player.Isaac_Avatar;
import game.utils.Const;
import game.utils.Isaac_TextureSpells;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Isaac_World extends AbstractWorld
{
    private double timePassed = 0;
    private double timeSinceLastShot = 0;
    private double shooting_speed_barrier = 0.7;
    private double baseDmg = 5;

    // for grenades
    private int grenades = 500;
    private Isaac_CounterGrenades counterG;
    private Isaac_Counter counterZ;
    private Isaac_HelpText helpText;
    private double spawnGrenade = 0;

    private double lifeHelpText = 10.0;

    public Isaac_Level level;
    public int score = 0;

    public void init()
    {
        if(Const.CHEAT_DAMAGE){
            shooting_speed_barrier = 0;
            baseDmg = 1000;
        }

        level = new Isaac_Level(this);
        Isaac_Level.instance.CreateLevel();
        level.LoadRoom();
        //CreateRoom(new Isaac_Room(RoomTexture.mapDefault,1920,1080,Sounds.MainMusic,Isacc_RoomType.NORMAL) );
        var liveDisplay = new AbstractTextObject(50, 80, Color.GRAY) {
            @Override
            public String toString() {
                return "Score: " + score;
            }
        };
        this.textObjects.add(liveDisplay);
        var levelDisplay = new AbstractTextObject(50, 110, Color.GRAY) {
            @Override
            public String toString() {
                return "Level: " + level.getLevel();
            }
        };
        this.textObjects.add(levelDisplay);

        var dmgDisplay = new AbstractTextObject(level.getCurrentRoom().maxXRoomSize - 200, 80, Color.GRAY) {
            @Override
            public String toString() {
                return "Damage: " + new DecimalFormat("#00.00").format(baseDmg);
            }
        };
        this.textObjects.add(dmgDisplay);
        var shootingSpeedDisplay = new AbstractTextObject(level.getCurrentRoom().maxXRoomSize - 200, 110, Color.GRAY) {
            @Override
            public String toString() {
                return "Speed:    " + new DecimalFormat("#00.00").format(0.7/shooting_speed_barrier);
            }
        };
        this.textObjects.add(shootingSpeedDisplay);
    }


    @Override
    public void tick(double timediff) {
        level.tick(timediff);

    }

    public void mod_speed(){
        shooting_speed_barrier *= 0.95f; // 1% Speed Increase
    }

    public void mod_damage(){
        baseDmg *= 1.05f; // 1% DMG Increase
    }

    public void processUserInput(UserInput userInput, double diffSeconds)
    {
        // distinguish if Avatar shall move or shoots
        int button = userInput.mouseButton;

        //
        // Mouse events
        //
        if(userInput.isMouseEvent)
        {
            // move
            /*
            if(button==1)
            { avatar.setDestination(userInput.mousePressedX+worldPartX,
                    userInput.mousePressedY+worldPartY);
            }

             */
        }

        //
        // Mouse still pressed?
        //

        // only 1 shot every ... seconds:
        timeSinceLastShot += diffSeconds;
        if(userInput.isMousePressed && button==3)
        {
            if(timeSinceLastShot > shooting_speed_barrier)
            {
                timeSinceLastShot = 0;

                Isaac_Shot shot = new Isaac_Shot(
                        avatar.x,avatar.y,userInput.mouseMovedX+worldPartX,userInput.mouseMovedY+worldPartY,750, Isaac_TextureSpells.waterSpell, this.baseDmg);
                this.gameObjects.add(shot);
            }
        }

        //
        // Keyboard events
        //
//	if(userInput.isKeyEvent)
//	{
//	  if(userInput.keyPressed==' ')
//	  { throwGrenade(userInput.mouseMovedX+worldPartX,userInput.mouseMovedY+worldPartY);
//	  }
//	  else if(userInput.keyPressed==(char)27)
//	  { System.exit(0);
//	  }
//	}
        if(userInput.keys.isIn('w')) {
//            double borderLocation = avatar.y - 45;
//            if(borderLocation > 0)
            ((Isaac_Avatar)avatar).move(0.0, -0.8);
        }
        if(userInput.keys.isIn('s')) {
//            double borderLocation = avatar.y + 45;
//            if(borderLocation < Const.WORLD_HEIGHT)
            ((Isaac_Avatar)avatar).move(0.0, 0.8);
        }
        if(userInput.keys.isIn('a')) {
//            double borderLocation = avatar.x - 45;
//            if(borderLocation > 0)
            ((Isaac_Avatar)avatar).move(-0.8, 0.0);

        }
        if(userInput.keys.isIn('d')) {
//            double borderLocation = avatar.x + 45;
//            if(borderLocation < Const.WORLD_WIDTH)
            ((Isaac_Avatar)avatar).move(0.8, 0.0);
        }

    }

    public void addScore(int addedScore){
        score+=addedScore;
    }


    private void createGrenade(double diffSeconds)
    {
        final double INTERVAL = Const.SPAWN_GRENADE;

        spawnGrenade += diffSeconds;
        if(spawnGrenade>INTERVAL)
        {
            spawnGrenade -= INTERVAL;

            // create new Grenade
            double x = worldPartX+Math.random()*Const.WORLDPART_WIDTH;
            double y = worldPartY+Math.random()*Const.WORLDPART_HEIGHT;

            // if too close to Avatar, cancel
            double dx = x-avatar.x;
            double dy = y-avatar.y;
            if(dx*dx+dy*dy < 200*200)
            { spawnGrenade = INTERVAL;
                return;
            }


            // if collisions occur, cancel
            Isaac_Grenade grenade = new Isaac_Grenade(x,y);
            GameObjectList list = getPhysicsSystem().getCollisions(grenade);
            if(list.size()!=0)
            { spawnGrenade = INTERVAL;
                return;
            }

            // else add zombie to world
            this.gameObjects.add(grenade);
            counterG.setNumber(grenades);
        }

    }

}
