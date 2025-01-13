package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureItems {
    public static BufferedImage HEART;

    static {
        try {
            // Load the images
            HEART = read(new File("src/assets/Items/Heart.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
