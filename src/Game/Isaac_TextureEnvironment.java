package Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureEnvironment {
    public static BufferedImage door;

    static {
        try {
            // Load the images
            door = read(new File("src/assets/Environment/door.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
