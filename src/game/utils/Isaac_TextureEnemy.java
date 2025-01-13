package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureEnemy {
    public static BufferedImage enemySpider;

    static {
        try {
            // Load the player avatar image
            enemySpider = read(new File("src/assets/Enemy/enemy_spider.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
