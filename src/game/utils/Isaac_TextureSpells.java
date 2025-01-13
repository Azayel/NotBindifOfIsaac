package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureSpells {
    public static BufferedImage defaultSpell;

    static {
        try {
            // Load the player avatar image
            defaultSpell = read(new File("src/assets/Spells/water_spell.png"));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
