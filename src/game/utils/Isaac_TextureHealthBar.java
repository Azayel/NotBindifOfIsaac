package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureHealthBar {
    public static BufferedImage HealthBarBackground;
    public static BufferedImage HealthBar;

    static {
        try {
            // Load the images
            HealthBarBackground = read(new File("src/assets/Healthbar/enemy_health_bar_background_001.png"));
            HealthBar = read(new File("src/assets/Healthbar/enemy_health_bar_001.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
