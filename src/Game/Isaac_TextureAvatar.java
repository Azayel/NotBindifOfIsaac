package Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public final class Isaac_TextureAvatar {
    public static BufferedImage avatarDefault;

    static {
        try {
            // Load the player avatar image
            avatarDefault = read(new File("src/assets/Player/Wizard.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}