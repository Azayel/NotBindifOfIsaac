package game.engine.world;


import game.engine.input.*;
import game.engine.graphics.IGraphicSystem;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.AbstractTextObject;
import game.engine.objects.GameObjectList;
import game.engine.physics.AbstractPhysicsSystem;
import game.engine.physics.PhysicsSystem;
import game.engine.sound.SoundEngine;
import game.level.Isaac_Level;
import game.utils.Const;

import java.util.ArrayList;

public abstract class AbstractWorld
{
    private IGraphicSystem graphicSystem;
    private AbstractPhysicsSystem physicsSystem;
    private InputSystem inputSystem;
    private UserInput userInput;
    private SoundEngine soundEngine;

    // top left corner of the displayed pane of the world
    public double worldPartX = 0;
    public double worldPartY = 0;

    // defines maximum frame rate
    private static final int FRAME_MINIMUM_MILLIS = 10;
    public boolean LoadNewRoom=false;

    // if game is over
    public boolean gameOver = false;

    // all objects in the game, including the Avatar
    public GameObjectList gameObjects = new GameObjectList();
    public AbstractGameObject avatar;
    public AbstractGameObject background;
    public ArrayList<AbstractTextObject> textObjects = new ArrayList<AbstractTextObject>();

    //Counter of Enemys
    int currentEnemys=0;

    public AbstractWorld() {
        physicsSystem = new PhysicsSystem(this);
    }


    //
    // the main GAME LOOP
    //
    public final void run()
    {
        long lastTick =  System.currentTimeMillis();

        while(true)
        {
            // calculate elapsed time
            //
            long currentTick = System.currentTimeMillis();
            long millisDiff  = currentTick-lastTick;

            // don�t run faster then MINIMUM_DIFF_SECONDS per frame
            //
            if(millisDiff<FRAME_MINIMUM_MILLIS)
            {
                try{ Thread.sleep(FRAME_MINIMUM_MILLIS-millisDiff);} catch(Exception ex){}
                currentTick = System.currentTimeMillis();
                millisDiff  = currentTick-lastTick;
            }

            lastTick = currentTick;


            // process User Input
            userInput = inputSystem.getUserInput();
            processUserInput(userInput,millisDiff/1000.0);
            userInput.clear();

            // no actions if game is over
            if(gameOver) { continue;}

            // move all Objects, maybe collide them etc...
            int gameSize = gameObjects.size();
//            for(int i=0; i<gameSize; i++)
//            {
//                AbstractGameObject obj = gameObjects.get(i);
//                if(obj.isLiving)  obj.move(millisDiff/1000.0);
//            }

            for(int i=0; i<gameSize; i++)
            {
                AbstractGameObject obj = gameObjects.get(i);
                if(obj.isLiving)  obj.processMovement(millisDiff/1000.0);
            }


            // delete all Objects which are not living anymore
            int num=0;
            while(num<gameSize)
            {
                if(gameObjects.get(num).isLiving==false)
                { gameObjects.remove(num);
                    gameSize--;
                }
                else
                { num++;
                }
            }


            // adjust displayed pane of the world
            //this.adjustWorldPart();






            // draw all Objects
            graphicSystem.clear();
            for(int i=0; i<gameSize; i++)
            { graphicSystem.draw(gameObjects.get(i));
            }


            // draw all TextObjects
            for (AbstractTextObject textObject : textObjects) {
                graphicSystem.draw(textObject);
            }

            // redraw everything
            graphicSystem.redraw();

            // create new objects if needed
            //createNewObjects(millisDiff/1000.0);

            //ToDo Check if Doors Needs tho be added?
            //TODO this should go in the Isaac world, here is the wrong place for that
            //One could add an abstract tick(double diffSeconds) method to this class implement that in the Isaac world and call it here with:
            //tick(millisDiff / 1000.0);
            currentEnemys = 0;
            for(AbstractGameObject obj : gameObjects){
                if(obj.type()==Const.TYPE_ZOMBIE){
                    currentEnemys++;
                }
            }
            //System.out.println("Enemys: "+currentEnemys);
            if(currentEnemys<=0){
                if (!Isaac_Level.instance.getCurrentRoom().isCleared()) {
                    System.out.println("Changed to Clear: " + currentEnemys);
                    Isaac_Level.instance.getCurrentRoom().setCleared();
                }
            }
            else {
                Isaac_Level.instance.getCurrentRoom().setCleared(false);
            }

            if(LoadNewRoom){
                Isaac_Level.instance.LoadRoom();
                LoadNewRoom=false;
            }

            //End of wrong code
        }
    }


    // adjust the displayed pane of the world according to Avatar and Bounds
    //
    private final void adjustWorldPart()
    {
        final int RIGHT_END  = Const.WORLD_WIDTH-Const.WORLDPART_WIDTH;
        final int BOTTOM_END = Const.WORLD_HEIGHT-Const.WORLDPART_HEIGHT;

        // if avatar is too much right in display ...
        if(avatar.x > worldPartX+Const.WORLDPART_WIDTH-Const.SCROLL_BOUNDS)
        {
            // ... adjust display
            worldPartX = avatar.x+Const.SCROLL_BOUNDS-Const.WORLDPART_WIDTH;
            if(worldPartX >= RIGHT_END)
            { worldPartX = RIGHT_END;
            }
        }

        // same left
        else if(avatar.x < worldPartX+Const.SCROLL_BOUNDS)
        {
            worldPartX = avatar.x-Const.SCROLL_BOUNDS;
            if(worldPartX <=0)
            { worldPartX = 0;
            }
        }

        // same bottom
        if(avatar.y > worldPartY+Const.WORLDPART_HEIGHT-Const.SCROLL_BOUNDS)
        {
            worldPartY = avatar.y+Const.SCROLL_BOUNDS-Const.WORLDPART_HEIGHT;
            if(worldPartY >= BOTTOM_END)
            { worldPartY = BOTTOM_END;
            }
        }

        // same top
        else if(avatar.y < worldPartY+Const.SCROLL_BOUNDS)
        {
            worldPartY = avatar.y-Const.SCROLL_BOUNDS;
            if(worldPartY <=0)
            { worldPartY = 0;
            }
        }

    }


    public void setSoundEngine(SoundEngine soundEngine) {this.soundEngine = soundEngine;}
    public void setGraphicSystem(IGraphicSystem p) { graphicSystem = p; }
    public void setInputSystem(InputSystem p)     { inputSystem   = p; }

    public AbstractPhysicsSystem getPhysicsSystem()       { return physicsSystem; }


    public abstract void init();
    public abstract void processUserInput(UserInput input, double diffSec);
    public abstract void createNewObjects(double diffSeconds);

}
