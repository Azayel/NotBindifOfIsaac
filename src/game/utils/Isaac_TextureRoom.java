package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureRoom {

    public static BufferedImage[] maps;
    public static BufferedImage mapBoss;

    static {
        try {
            // Load the game map background image
            maps = new BufferedImage[]{
                    read(new File("src/assets/Maps/Isaac_GameBackground1.png")),
                    read(new File("src/assets/Maps/Isaac_GameBackground_NORMAL.png")),
            };
            mapBoss = read(new File("src/assets/Maps/Isaac_GameBackground_BOSS.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}

