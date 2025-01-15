package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureBoss {
    public static BufferedImage[] agis;
    public static BufferedImage[] fire;
    public static BufferedImage[] laser;

    static {
        try {
            // Load the player avatar image
            agis = new BufferedImage[]{
                    read(new File("src/assets/Boss/tile000.png")),
                    read(new File("src/assets/Boss/tile001.png")),
                    read(new File("src/assets/Boss/tile002.png")),
                    read(new File("src/assets/Boss/tile003.png")),
                    read(new File("src/assets/Boss/tile004.png")),
                    read(new File("src/assets/Boss/tile005.png")),
                    read(new File("src/assets/Boss/tile006.png")),
                    read(new File("src/assets/Boss/tile007.png")),
                    read(new File("src/assets/Boss/tile008.png")),
                    read(new File("src/assets/Boss/tile009.png")),
                    read(new File("src/assets/Boss/tile010.png")),
                    read(new File("src/assets/Boss/tile011.png")),
                    read(new File("src/assets/Boss/tile012.png")),
                    read(new File("src/assets/Boss/tile013.png")),
                    read(new File("src/assets/Boss/tile014.png")),

            };

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }

    static {
        try {
            // Load the player avatar image
            fire = new BufferedImage[]{
                    read(new File("src/assets/Fire/fire_column_medium_5.png")),
                    read(new File("src/assets/Fire/fire_column_medium_6.png")),
                    read(new File("src/assets/Fire/fire_column_medium_7.png")),
                    read(new File("src/assets/Fire/fire_column_medium_8.png"))
            };

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }

    static {
        try {
            // Load the player avatar image
            laser = new BufferedImage[]{
                    read(new File("src/assets/Laser/tile320.png")),
                    // read(new File("src/assets/Laser/tile321.png")),
                    // read(new File("src/assets/Laser/tile322.png")),
                    // read(new File("src/assets/Laser/tile323.png")),
                    // read(new File("src/assets/Laser/tile324.png")),
                    // read(new File("src/assets/Laser/tile326.png")),
                    // read(new File("src/assets/Laser/tile327.png"))
            };

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
