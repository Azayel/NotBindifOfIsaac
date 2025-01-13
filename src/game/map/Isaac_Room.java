package game.map;

import game.engine.objects.GameObjectList;
import game.objects.Isaac_SpiderAI;
import game.objects.Isaac_Teleporter;
import game.utils.Isaac_TextureEnemy;

import java.awt.image.BufferedImage;

public class Isaac_Room {

    public static int minEnemySpeed = 100;
    public static int maxEnemySpeed = 150;

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
    GameObjectList gameObjectsEnemyList = new GameObjectList();

    //Doors List
    GameObjectList doorList=new GameObjectList();

    private boolean clearedRoom = false;

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
                creategameObjectsEnemyList((int)(Math.random()* ((15 - 10) + 1)) + 10, 150);
                //add Chest

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
                creategameObjectsEnemyList((int)(Math.random()* ((10 - 5) + 1)) + 5, 150);
        }
    }

    private void creategameObjectsEnemyList(int enemysCount, int minDistanceAwayFromPlayer) {

        for (int i = 0; i < enemysCount; i++) {
            boolean validPosition = false;
            int enemyX = 0;
            int enemyY = 0;

            while (!validPosition) {
                enemyX = (int) (Math.random() * maxXRoomSize);
                enemyY = (int) (Math.random() * maxYRoomSize);
                double distance = Math.sqrt(Math.pow(enemyX - playerStartX, 2) + Math.pow(enemyY - playerStartY, 2));

                // Validate position: far enough from player and away from walls
                if (distance >= minDistanceAwayFromPlayer &&
                        enemyX >= xMinSpawnSize && enemyX <= maxXRoomSize - xMinSpawnSize &&
                        enemyY >= yMinSpawnSize && enemyY <= maxYRoomSize - yMinSpawnSize) {
                    validPosition = true;
                }
            }

            // Add the zombie to the game objects list
            gameObjectsEnemyList.add(new Isaac_SpiderAI(enemyX, enemyY,32, (int)(Math.random() * ((maxEnemySpeed - minEnemySpeed) + 1)) + minEnemySpeed, Isaac_TextureEnemy.enemySpider));
        }

    }

    public boolean isCleared(){
        return clearedRoom;
    }

    public void CreateDoors(){
        GameObjectList newDoorList=new GameObjectList();
        if(topRoom != null){
            newDoorList.add(new Isaac_Teleporter( maxXRoomSize/2-32,100,32, TeleporterDestination.TOP));
        }
        if(rightRoom != null){
            newDoorList.add(new Isaac_Teleporter( maxXRoomSize-100,maxYRoomSize/2-32,32, TeleporterDestination.RIGHT));
        }
        if(bottomRoom != null){
            newDoorList.add(new Isaac_Teleporter( maxXRoomSize/2-32,maxYRoomSize-100,32, TeleporterDestination.BOTTOM));
        }
        if(leftRoom != null){
            newDoorList.add(new Isaac_Teleporter( 100,maxYRoomSize/2-32,32, TeleporterDestination.LEFT));
        }
        doorList=newDoorList;
    }

    public void setCleared() {
        clearedRoom = true;
    }

    public void setCleared(boolean isCleared) {
        clearedRoom=isCleared;
    }
}
