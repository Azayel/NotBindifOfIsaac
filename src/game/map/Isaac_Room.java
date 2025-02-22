package game.map;

import game.engine.objects.GameObjectList;
import game.level.Isaac_Level;
import game.objects.enemy.Boss;
import game.objects.enemy.Isaac_DragonAi;
import game.objects.enemy.Isaac_SpiderAI;
import game.objects.Isaac_Teleporter;
import game.objects.items.Isaac_Chest;
import game.utils.Const;
import game.utils.Isaac_TextureEnemy;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Isaac_Room {

    public static int minEnemySpeed = 20;
    public static int maxEnemySpeed = 250;

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
    public GameObjectList gameObjectsEnemyList = new GameObjectList();

    //Doors List
    public GameObjectList teleporterList =new GameObjectList();

    //Item List
    public GameObjectList itemList =new GameObjectList();

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
        int enemySpawnNormalMin=5+ (int) (Isaac_Level.instance.getLevel()* Const.DIFFICULTY_FACTOR);
        int enemySpawnNormalMax=10+(int) (Isaac_Level.instance.getLevel()* Const.DIFFICULTY_FACTOR);
        int enemySpawnBossMin=10+(int) (Isaac_Level.instance.getLevel()* Const.DIFFICULTY_FACTOR);
        int enemySpawnBossMax=15+(int) (Isaac_Level.instance.getLevel()* Const.DIFFICULTY_FACTOR);

        int enemySpawnNormal = (int)(Math.random() * ((enemySpawnNormalMax - enemySpawnNormalMin) + 1)) + enemySpawnNormalMin;
        int enemySpawnBoss = (int)(Math.random()* ((enemySpawnBossMax - enemySpawnBossMin) + 1)) + enemySpawnBossMin;

        switch (roomType) {
            case BOSS:
                //ToDo
                creategameObjectsEnemyList(enemySpawnBoss, 150);
                setSpawnDragon(150);
                gameObjectsEnemyList.add(new Boss(maxXRoomSize/2, maxYRoomSize/2));
                //add Chest
                itemList.add(new Isaac_Chest(maxXRoomSize/2,maxYRoomSize/4));
                break;
            case TREASURE:
                //ToDo Add treasure chest or rewards
                break;
            case SHOP:
                //ToDo Add shopkeeper or items
                break;
            case START:
                //gameObjectsEnemyList.add(new Isaac_DragonAi(maxXRoomSize/2, 100));



                break;
            default:
                creategameObjectsEnemyList(enemySpawnNormal, 150);
                setSpawnDragon(150);
                break;
        }
    }

    private void setSpawnDragon(int minDistanceAwayFromPlayer){
        //Spawn Dragons at Level 3 or above 25%
        if(Isaac_Level.instance.getLevel()>2){
            for (int i = 0; i < Isaac_Level.instance.getLevel(); i++) {
                if (Math.random()<0.25){
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
                    gameObjectsEnemyList.add(new Isaac_DragonAi(enemyX,enemyY));
                }
            }
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
        teleporterList =newDoorList;
    }

    public void setCleared() {
        clearedRoom = true;
    }

    public void setCleared(boolean isCleared) {
        clearedRoom=isCleared;
    }
}
