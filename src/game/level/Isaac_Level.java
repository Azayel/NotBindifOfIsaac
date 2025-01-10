package game.level;

import game.map.DoorDirection;
import game.map.Isaac_Room;
import game.map.Isaac_RoomType;
import game.map.Isaac_World;
import game.sound.Isaac_Sounds;
import game.utils.Isaac_TextureRoom;

import java.util.ArrayList;
import java.util.List;

public class Isaac_Level {

    public static Isaac_Level instance;
    private Isaac_Room currentRoom;
    private Isaac_Room startingRoom;
    List<Isaac_Room> rooms = new ArrayList<Isaac_Room>();
    private Isaac_World isaacWorld;

    public Isaac_Level(Isaac_World isaacWorld) {
        if (instance == null) {
            this.isaacWorld = isaacWorld;
            instance = this;
        }
    }


    //Create a Level
    public void CreateLevel(int maxRooms, int level) {
        //Init Starting Room and add to list
        startingRoom = new Isaac_Room(Isaac_TextureRoom.mapDefault, 1920, 1080, Isaac_Sounds.StartingLevelMusic, Isaac_RoomType.START);
        rooms.add(startingRoom);

        while (rooms.size() < maxRooms - 1) {
            //Take Random Room and add new Room to it if not already exists
            int randomRoomIndex = (int) (Math.random() * rooms.size());
            Isaac_Room currentRoom = rooms.get(randomRoomIndex);

            int randomDirection = (int) (Math.random() * 4);

            //Direction
            if (randomDirection == 0 && currentRoom.topRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapNormal, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
                currentRoom.topRoom = newRoom;
                newRoom.bottomRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 1 && currentRoom.rightRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapNormal, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
                currentRoom.rightRoom = newRoom;
                newRoom.leftRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 2 && currentRoom.bottomRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapNormal, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
                currentRoom.bottomRoom = newRoom;
                newRoom.topRoom = currentRoom;
                rooms.add(newRoom);
            } else if (randomDirection == 3 && currentRoom.leftRoom == null) {
                Isaac_Room newRoom = new Isaac_Room(Isaac_TextureRoom.mapNormal, 1920, 1080, Isaac_Sounds.MainMusic, Isaac_RoomType.NORMAL);
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
    }

    public void goThroughRoom(DoorDirection direction) {
        switch (direction) {
            case TOP:
                if (currentRoom.topRoom!=null) {
                    currentRoom = currentRoom.topRoom;

                }
                break;
            case RIGHT:
                if (currentRoom.rightRoom!=null) {
                    currentRoom = currentRoom.rightRoom;

                }
                break;
            case BOTTOM:
                if (currentRoom.bottomRoom!=null) {
                    currentRoom = currentRoom.bottomRoom;

                }
                break;
            case LEFT:
                if (currentRoom.leftRoom!=null) {
                    currentRoom = currentRoom.leftRoom;

                }
                break;
        }
    }

    public void LoadRoom() {
        isaacWorld.CreateRoom(currentRoom);
    }

    public void UpdateDoorRoom(){
        isaacWorld.avatar.setDestination(isaacWorld.avatar.x, isaacWorld.avatar.y);
        isaacWorld.CreateRoom(currentRoom);
    }



    public Isaac_World getIsaacWorld() {
        return isaacWorld;
    }

    public Isaac_Room getCurrentRoom() {
        return currentRoom;
    }
}
