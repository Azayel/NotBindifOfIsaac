package Game;

import Game.*;

class Isaac_World extends World
{
    private double timePassed = 0;
    private double timeSinceLastShot = 0;

    // for grenades
    private int grenades = 500;
    private Isaac_CounterGrenades counterG;
    private Isaac_Counter counterZ;
    private Isaac_HelpText helpText;
    private double spawnGrenade = 0;

    private double lifeHelpText = 10.0;

    protected void init()
    {
        // add the Avatar
        avatar = new Isaac_Avatar(2500,2000);
        gameObjects.add(avatar);

        // set WorldPart position
        worldPartX = 1500;
        worldPartY = 1500;

        // add a little forrest

        for(int x=0; x<5000; x+=1000)
        {

            for(int y=0; y<4000; y+=800)
            {
                gameObjects.add(new Isaac_Tree(x+300,y+200,80));
                gameObjects.add(new Isaac_Tree(x+600,y+370,50));
                gameObjects.add(new Isaac_Tree(x+200,y+600,50));
                gameObjects.add(new Isaac_Tree(x+500,y+800,40));
                gameObjects.add(new Isaac_Tree(x+900,y+500,100));
                gameObjects.add(new Isaac_Tree(x+760,y+160,40));
            }
        }



        // add one zombie
        gameObjects.add(new Isaac_ZombieAI(100,100));


        counterZ = new Isaac_Counter(20,40);
        counterG = new Isaac_CounterGrenades(770,40);
        helpText = new Isaac_HelpText(100,400);

        counterG.setNumber(grenades);
        textObjects.add(counterZ);
        textObjects.add(counterG);
        textObjects.add(helpText);
    }

    protected void processUserInput(UserInput userInput, double diffSeconds)
    {
        // distinguish if Avatar shall move or shoots
        int button = userInput.mouseButton;

        //
        // Mouse events
        //
        if(userInput.isMouseEvent)
        {
            // move
            if(button==1)
            { avatar.setDestination(userInput.mousePressedX+worldPartX,
                    userInput.mousePressedY+worldPartY);
            }
        }

        //
        // Mouse still pressed?
        //
        if(userInput.isMousePressed && button==3)
        {
            // only 1 shot every ... seconds:
            timeSinceLastShot += diffSeconds;
            if(timeSinceLastShot > 0.2)
            {
                timeSinceLastShot = 0;

                Isaac_Shot shot = new Isaac_Shot(
                        avatar.x,avatar.y,userInput.mouseMovedX+worldPartX,userInput.mouseMovedY+worldPartY);
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
            avatar.y -= 5;
        }
        if(userInput.keys.isIn('s')) {
            avatar.y += 5;
        }
        if(userInput.keys.isIn('a')) {
            avatar.x -= 5;
        }
        if(userInput.keys.isIn('d')) {
            avatar.x += 5;
        }
    }


    private void throwGrenade(double x, double y)
    {
        if(grenades<=0) return;

        // throw grenade
        for(int i=0; i<2000; i++)
        {
            double alfa = Math.random()*Math.PI*2;
            double speed = 50+Math.random()*200;
            double time  = 0.2+Math.random()*0.4;
            Isaac_Shot shot = new Isaac_Shot(x,y,alfa,speed,time);
            this.gameObjects.add(shot);
        }

        // inform counter
        grenades--;
        counterG.setNumber(grenades);
    }



    protected void createNewObjects(double diffSeconds)
    {
        createZombie(diffSeconds);
        createGrenade(diffSeconds);

        // delete HelpText after ... seconds
        if(helpText!=null)
        {
            lifeHelpText -= diffSeconds;
            if(lifeHelpText < 0)
            {
                textObjects.remove(helpText);
                helpText = null;
            }
        }
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



    private void createZombie(double diffSeconds)
    {
        final double INTERVAL = Const.SPAWN_INTERVAL;

        timePassed += diffSeconds;
        if(timePassed>INTERVAL)
        {
            timePassed -= INTERVAL;

            // create new Zombie; preference to current screen
            double x,y;
            if(Math.random() < 0.7)
            { x = Math.random()*Const.WORLD_WIDTH;
                y = Math.random()*Const.WORLD_HEIGHT;
            }
            else
            { x = worldPartX+Math.random()*Const.WORLDPART_WIDTH;
                y = worldPartY+Math.random()*Const.WORLDPART_HEIGHT;
            }


            // if too close to Avatar, cancel
            double dx = x-avatar.x;
            double dy = y-avatar.y;
            if(dx*dx+dy*dy < 400*400)
            { timePassed = INTERVAL;
                return;
            }

            // if collisions occur, cancel
            Isaac_ZombieAI zombie = new Isaac_ZombieAI(x,y);
            GameObjectList list = getPhysicsSystem().getCollisions(zombie);
            if(list.size()!=0)
            { timePassed = INTERVAL;
                return;
            }

            // else add zombie to world
            this.gameObjects.add(zombie);
            zombie.setDestination(avatar);
            Isaac_Counter counter = (Isaac_Counter)textObjects.get(0);
            counter.increment();
        }

    }


    public void addGrenade()
    {
        if(grenades<999) { grenades++; }
        counterG.setNumber(grenades);
    }

}
