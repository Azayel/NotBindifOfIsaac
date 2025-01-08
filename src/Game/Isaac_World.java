package Game;

import java.util.ArrayList;
import java.util.List;

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

    public Isaac_Room startingRoom;
    public List<Isaac_Room> rooms = new ArrayList<Isaac_Room>();
    public Isaac_Room currentRoom;

    protected void init()
    {
        CreateLevel(5,1);
        //CreateRoom(new Isaac_Room(RoomTexture.mapDefault,1920,1080,Sounds.MainMusic,Isacc_RoomType.NORMAL) );
    }


    //Create a Level
    public void CreateLevel(int maxRooms,int level){
        //Init Starting Room and add to list
        startingRoom = new Isaac_Room(Isaac_TextureRoom.mapDefault,1920,1080, Isaac_Sounds.StartingLevelMusic, Isaac_RoomType.START);
        rooms.add(startingRoom);

        while (rooms.size() < maxRooms-1)
        {
            //Take Random Room and add new Room to it if not already exists
            int randomRoomIndex = (int) (Math.random() * rooms.size());
            Isaac_Room currentRoom = rooms.get(randomRoomIndex);

            int randomDirection = (int) (Math.random() * 4);

            //Direction
            if (randomDirection == 0 && currentRoom.topRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapDefault, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
                currentRoom.topRoom = newRoom;
                newRoom.bottomRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 1 && currentRoom.rightRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapDefault, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
                currentRoom.rightRoom = newRoom;
                newRoom.leftRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 2 && currentRoom.bottomRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapDefault, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
                currentRoom.bottomRoom = newRoom;
                newRoom.topRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 3 && currentRoom.leftRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapDefault, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
                currentRoom.leftRoom = newRoom;
                newRoom.rightRoom = currentRoom;
                rooms.add(newRoom);
            }
        }

        // Place the boss room
        Isaac_Room lastRoom = rooms.get(rooms.size() - 1);
        Isaac_Room bossRoom = new Isaac_Room(Isaac_TextureRoom.mapBoss, 1920, 1080, Isaac_Sounds.BossMusic, Isaac_RoomType.BOSS);
        if (lastRoom.topRoom == null) {
            lastRoom.topRoom = bossRoom;
            bossRoom.bottomRoom = lastRoom;
        } else if (lastRoom.rightRoom == null) {
            lastRoom.rightRoom = bossRoom;
            bossRoom.leftRoom = lastRoom;
        } else if (lastRoom.bottomRoom == null) {
            lastRoom.bottomRoom = bossRoom;
            bossRoom.topRoom = lastRoom;
        } else if (lastRoom.leftRoom == null) {
            lastRoom.leftRoom = bossRoom;
            bossRoom.rightRoom = lastRoom;
        }
        rooms.add(bossRoom);

        // Start with the starting room
        CreateRoom(startingRoom);
        currentRoom=startingRoom;
    }



    //Create a Room
    public void CreateRoom(Isaac_Room room){
        //Clear gameObjects
        gameObjects.clear();

        //Set Background Image
        background = new RoomBackgroundGameObject(room.backgroundRoomImage);
        gameObjects.add(background);
        //Set Avatar
        avatar = new Isaac_Avatar(room.playerStartX,room.playerStartY);
        gameObjects.add(avatar);
        //add all Enemys
        gameObjects.addAll(room.gameObjectsEnemyList);
        //add all Enviorment stuff
        //ToDo
        room.CreateDoors();
        gameObjects.addAll(room.doorList);

        //Sound System
        SoundEngine.instance.playMusic(room.backgroundMusic,true);
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
           double y =avatar.y-5;
            avatar.y = Math.clamp(y,50, currentRoom.maxYRoomSize - 50);
        }
        if(userInput.keys.isIn('s')) {
            double y =avatar.y+5;
            avatar.y = Math.clamp(y,50, currentRoom.maxYRoomSize - 50);
        }
        if(userInput.keys.isIn('a')) {
            double x = avatar.x -5;
            avatar.x = Math.clamp(x, 50 , currentRoom.maxXRoomSize - 50);
        }
        if(userInput.keys.isIn('d')) {
            double x =avatar.x+5;
            avatar.x = Math.clamp(x,50, currentRoom.maxXRoomSize - 50);
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

    //ToDo Old stuff
    /*
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
    */


}
