package Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureRoom {

    public static BufferedImage mapDefault;
    public static BufferedImage mapBoss;

    static {
        try {
            // Load the game map background image
            mapDefault = read(new File("src/assets/Maps/Isaac_GameBackground1.png"));
            mapBoss = read(new File("src/assets/Maps/Isaac_GameBackground1.png"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}

