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

    List<GameObject> gameObjectsEnemyList = new ArrayList<GameObject>();

    public Isaac_Room(BufferedImage backgroundRoomImage, int maxXRoomSize, int maxYRoomSize) {
        this.maxXRoomSize = maxXRoomSize;
        this.maxYRoomSize = maxYRoomSize;
        this.backgroundRoomImage = backgroundRoomImage;
        this.playerStartX = maxXRoomSize/2;
        this.playerStartY = maxYRoomSize/2;

        creategameObjectsEnemyList(5,100);
    }

    public Isaac_Room(BufferedImage backgroundRoomImage, int maxXRoomSize, int maxYRoomSize, int playerStartX, int playerStartY) {
        this.maxXRoomSize = maxXRoomSize;
        this.maxYRoomSize = maxYRoomSize;
        this.backgroundRoomImage = backgroundRoomImage;
        this.playerStartX = playerStartX;
        this.playerStartY = playerStartY;

        creategameObjectsEnemyList(5,100);
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
}
