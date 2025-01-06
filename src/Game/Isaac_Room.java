package Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Isaac_Room {

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

                // Check if the zombie's position is at least minDistanceAwayFromPlayer away from the player
                if (distance >= minDistanceAwayFromPlayer) {
                    validPosition = true;
                }
            }

            // Add the zombie to the game objects list
            gameObjectsEnemyList.add(new Isaac_ZombieAI(zombieX, zombieY));
        }

    }
}
