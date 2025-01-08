package Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Isaac_Room {

    //to Not spawn in Walls
    public static int xMinSpawnSize = 50;
    public static int yMinSpawnSize = 50;

    public int maxXRoomSize;
    public int maxYRoomSize;
    public BufferedImage backgroundRoomImage;
    public int playerStartX;
    public int playerStartY;

    public String backgroundMusic;

    //all connected Rooms
    public Isaac_Room leftRoom = null;
    public Isaac_Room rightRoom = null;
    public Isaac_Room topRoom = null;
    public Isaac_Room bottomRoom = null;
    public Isaac_RoomType isaacRoomType = null;

    //Enemy List
    List<GameObject> gameObjectsEnemyList = new ArrayList<GameObject>();

    //Doors List
    List<GameObject> doorList=new ArrayList<GameObject>();

    public Isaac_Room(BufferedImage backgroundRoomImage, int maxXRoomSize, int maxYRoomSize,String backgroundMusic, Isaac_RoomType isaccRoomType) {
        this.maxXRoomSize = maxXRoomSize;
        this.maxYRoomSize = maxYRoomSize;
        this.backgroundRoomImage = backgroundRoomImage;
        this.playerStartX = maxXRoomSize/2;
        this.playerStartY = maxYRoomSize/2;
        this.backgroundMusic = backgroundMusic;
        this.isaacRoomType = isaccRoomType;
        setRoomToRoomType(isaccRoomType);

    }

    public Isaac_Room(BufferedImage backgroundRoomImage, int maxXRoomSize, int maxYRoomSize, int playerStartX, int playerStartY, String backgroundMusic, Isaac_RoomType isaccRoomType) {
        this.maxXRoomSize = maxXRoomSize;
        this.maxYRoomSize = maxYRoomSize;
        this.backgroundRoomImage = backgroundRoomImage;
        this.playerStartX = playerStartX;
        this.playerStartY = playerStartY;
        this.backgroundMusic = backgroundMusic;
        this.isaacRoomType = isaccRoomType;
        setRoomToRoomType(isaccRoomType);

    }

    private void setRoomToRoomType(Isaac_RoomType roomType) {
        switch (roomType) {
            case BOSS:
                //ToDo
                creategameObjectsEnemyList(10, 150);
                break;
            case TREASURE:
                //ToDo Add treasure chest or rewards
                break;
            case SHOP:
                //ToDo Add shopkeeper or items
                break;
            case START:

                break;
            default:
                creategameObjectsEnemyList(5, 100);
        }
    }

    private void creategameObjectsEnemyList(int enemysCount, int minDistanceAwayFromPlayer) {

        for (int i = 0; i < enemysCount; i++) {
            boolean validPosition = false;
            int zombieX = 0;
            int zombieY = 0;

            while (!validPosition) {
                zombieX = (int) (Math.random() * maxXRoomSize);
                zombieY = (int) (Math.random() * maxYRoomSize);
                double distance = Math.sqrt(Math.pow(zombieX - playerStartX, 2) + Math.pow(zombieY - playerStartY, 2));

                // Validate position: far enough from player and away from walls
                if (distance >= minDistanceAwayFromPlayer &&
                        zombieX >= xMinSpawnSize && zombieX <= maxXRoomSize - xMinSpawnSize &&
                        zombieY >= yMinSpawnSize && zombieY <= maxYRoomSize - yMinSpawnSize) {
                    validPosition = true;
                }
            }

            // Add the zombie to the game objects list
            gameObjectsEnemyList.add(new Isaac_ZombieAI(zombieX, zombieY));
        }

    }

    public void CreateDoors(){
        List<GameObject> newDoorList=new ArrayList<>();
        if(topRoom != null){
            newDoorList.add(new Isaac_Door( maxXRoomSize/2-32,100,32));
        }
        if(rightRoom != null){
            newDoorList.add(new Isaac_Door( maxXRoomSize-100,maxYRoomSize/2-32,32));
        }
        if(bottomRoom != null){
            newDoorList.add(new Isaac_Door( maxXRoomSize/2-32,maxYRoomSize-100,32));
        }
        if(leftRoom != null){
            newDoorList.add(new Isaac_Door( 100,maxYRoomSize/2-32,32));
        }
        doorList=newDoorList;
    }
}