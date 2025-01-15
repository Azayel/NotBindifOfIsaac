package game.level;

import game.engine.objects.AbstractGameObject;
import game.engine.sound.SoundEngine;
import game.map.TeleporterDestination;
import game.map.Isaac_Room;
import game.map.Isaac_RoomType;
import game.map.Isaac_World;
import game.objects.RoomBackgroundGameObject;
import game.objects.enemy.IEnemy;
import game.objects.player.Isaac_Avatar;
import game.sound.Isaac_Sounds;
import game.utils.Const;
import game.utils.Isaac_TextureItems;
import game.utils.Isaac_TextureRoom;
import jdk.jshell.execution.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Isaac_Level {

    public static Isaac_Level instance;
    private Isaac_Room currentRoom;
    private Isaac_Room startingRoom;
    List<Isaac_Room> rooms = new ArrayList<Isaac_Room>();
    private Isaac_World isaacWorld;
    private int level=0;

    private boolean roomActive=false;

    public boolean LoadNewRoom=false;


    public Isaac_Level(Isaac_World isaacWorld) {
        if (instance == null) {
            this.isaacWorld = isaacWorld;
            instance = this;
        }
    }

    //Create a Level
    public void CreateLevel() {
        int maxRooms = Const.STARTING_ROOMS + (int)(level*Const.DIFFICULTY_FACTOR);
        rooms.clear();
        var mapBackground = Isaac_TextureRoom.maps[level%Isaac_TextureRoom.maps.length];
        var music = level==0 ? Isaac_Sounds.StartingLevelMusic : Isaac_Sounds.MainMusic[new Random().nextInt(Isaac_Sounds.MainMusic.length)];
        //Init Starting Room and add to list
        startingRoom = new Isaac_Room(mapBackground, 1920, 1080, music, Isaac_RoomType.START);
        rooms.add(startingRoom);

        while (rooms.size() < maxRooms - 1) {
            //Take Random Room and add new Room to it if not already exists
            int randomRoomIndex = (int) (Math.random() * rooms.size());
            Isaac_Room currentRoom = rooms.get(randomRoomIndex);

            int randomDirection = (int) (Math.random() * 4);

            //Direction
            if (randomDirection == 0 && currentRoom.topRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(mapBackground, 1920, 1080, music, Isaac_RoomType.NORMAL);
                currentRoom.topRoom = newRoom;
                newRoom.bottomRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 1 && currentRoom.rightRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(mapBackground, 1920, 1080, music, Isaac_RoomType.NORMAL);
                currentRoom.rightRoom = newRoom;
                newRoom.leftRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 2 && currentRoom.bottomRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(mapBackground, 1920, 1080, music, Isaac_RoomType.NORMAL);
                currentRoom.bottomRoom = newRoom;
                newRoom.topRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 3 && currentRoom.leftRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(mapBackground, 1920, 1080, music, Isaac_RoomType.NORMAL);
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
        currentRoom = startingRoom;
        level++;
    }

    public void goThroughRoom(TeleporterDestination direction) {
        switch (direction) {
            case TOP:
                if (currentRoom.topRoom!=null) {
                    currentRoom = currentRoom.topRoom;

                    currentRoom.playerStartX=currentRoom.maxXRoomSize/2-32;
                    currentRoom.playerStartY=currentRoom.maxYRoomSize-150-32;
                }
                break;
            case RIGHT:
                if (currentRoom.rightRoom!=null) {
                    currentRoom = currentRoom.rightRoom;
                    currentRoom.playerStartX=150+32;
                    currentRoom.playerStartY=currentRoom.maxYRoomSize/2-32;
                }
                break;
            case BOTTOM:
                if (currentRoom.bottomRoom!=null) {
                    currentRoom = currentRoom.bottomRoom;
                    currentRoom.playerStartX=currentRoom.maxXRoomSize/2-32;
                    currentRoom.playerStartY=150+32;

                }
                break;
            case LEFT:
                if (currentRoom.leftRoom!=null) {
                    currentRoom = currentRoom.leftRoom;
                    currentRoom.playerStartX=currentRoom.maxXRoomSize-32-150;
                    currentRoom.playerStartY=currentRoom.maxYRoomSize/2-32;
                }
                break;
        }
    }

    public void LoadRoom(){
        roomActive=false;
        //Clear gameObjects
        isaacWorld.gameObjects.clear();

        //Set Background Image
        isaacWorld.background = new RoomBackgroundGameObject(currentRoom.backgroundRoomImage);
        isaacWorld.gameObjects.add(isaacWorld.background);
        //CreateDoors
        currentRoom.CreateDoors();
        isaacWorld.gameObjects.addAll(currentRoom.teleporterList);
        //Spawn Items
        isaacWorld.gameObjects.addAll(currentRoom.itemList);
        //Set Avatar only if it never existed before
        if(isaacWorld.avatar==null)
            isaacWorld.avatar = new Isaac_Avatar(0,0);
        isaacWorld.avatar.setX(currentRoom.playerStartX);
        isaacWorld.avatar.setY(currentRoom.playerStartY);
        isaacWorld.gameObjects.add(isaacWorld.avatar);
        //add all living Enemys
        isaacWorld.gameObjects.addAll(currentRoom.gameObjectsEnemyList.stream()
                .filter((enemy)->enemy.isLiving)
                .toList());
        //add all Enviorment stuff


        //Sound System
        SoundEngine.instance.playMusic(currentRoom.backgroundMusic,true);
        roomActive=true;
    }

    public void tick(double timediff) {
        if(!roomActive)
            return;

        var currentEnemys = 0;
        for(AbstractGameObject obj : isaacWorld.gameObjects){
            if(obj instanceof IEnemy){
                currentEnemys++;
            }
        }
        if(currentEnemys<=0){
            if (!Isaac_Level.instance.getCurrentRoom().isCleared()) {
                //System.out.println("Changed to Clear: " + currentEnemys);
                Isaac_Level.instance.getCurrentRoom().setCleared();
                //isaacWorld.addScore(500);
            }
        }
        else {
            Isaac_Level.instance.getCurrentRoom().setCleared(false);
        }

        if(LoadNewRoom){
            if(currentEnemys==0) {
                Isaac_Level.instance.LoadRoom();
            }
            LoadNewRoom=false;
        }
    }

    public int getLevel() {
        return level;
    }

    public Isaac_World getIsaacWorld() {
        return isaacWorld;
    }

    public Isaac_Room getCurrentRoom() {
        return currentRoom;
    }
}
