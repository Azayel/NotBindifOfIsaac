package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureEnemy {
    public static BufferedImage[] enemySpider;
    public static BufferedImage[] enemyDragon;

    static {
        try {
            // Load the player avatar image
            enemySpider = new BufferedImage[] {
                    read(new File("src/assets/Enemy/Spider/spider0.png")),
                    read(new File("src/assets/Enemy/Spider/spider1.png")),
                    read(new File("src/assets/Enemy/Spider/spider2.png")),
                    read(new File("src/assets/Enemy/Spider/spider3.png")),
                    read(new File("src/assets/Enemy/Spider/spider4.png")),
                    read(new File("src/assets/Enemy/Spider/spider5.png")),
                    read(new File("src/assets/Enemy/Spider/spider6.png")),
                    read(new File("src/assets/Enemy/Spider/spider7.png")),
                    read(new File("src/assets/Enemy/Spider/spider8.png")),
                    read(new File("src/assets/Enemy/Spider/spider9.png")),
                    read(new File("src/assets/Enemy/Spider/spider8.png")),
                    read(new File("src/assets/Enemy/Spider/spider7.png")),
                    read(new File("src/assets/Enemy/Spider/spider6.png")),
                    read(new File("src/assets/Enemy/Spider/spider5.png")),
                    read(new File("src/assets/Enemy/Spider/spider4.png")),
                    read(new File("src/assets/Enemy/Spider/spider3.png")),
                    read(new File("src/assets/Enemy/Spider/spider2.png")),
                    read(new File("src/assets/Enemy/Spider/spider1.png")),
                    read(new File("src/assets/Enemy/Spider/spider0.png")),
            };
            enemyDragon = new BufferedImage[] {
                    read(new File("src/assets/Enemy/Dragon/dragon0.png")),
                    read(new File("src/assets/Enemy/Dragon/dragon1.png")),
                    read(new File("src/assets/Enemy/Dragon/dragon2.png")),
                    read(new File("src/assets/Enemy/Dragon/dragon1.png")),
                    read(new File("src/assets/Enemy/Dragon/dragon0.png")),
            };
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
