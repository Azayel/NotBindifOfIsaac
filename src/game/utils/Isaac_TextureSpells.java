package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureSpells {
    public static BufferedImage[] waterSpell;

    static {
        try {
            // Load the player avatar image
            waterSpell = new BufferedImage[]{
                    read(new File("src/assets/Spells/WaterSpell/WaterSpell0.png")),
                    read(new File("src/assets/Spells/WaterSpell/WaterSpell1.png")),
                    read(new File("src/assets/Spells/WaterSpell/WaterSpell2.png")),
                    read(new File("src/assets/Spells/WaterSpell/WaterSpell3.png")),
                    read(new File("src/assets/Spells/WaterSpell/WaterSpell4.png"))
            };

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
