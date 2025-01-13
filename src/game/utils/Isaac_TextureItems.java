package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureItems {
    public static BufferedImage HEART;
    public static BufferedImage[] CHEST;

    static {
        try {
            // Load the images
            HEART = read(new File("src/assets/Items/Heart.png"));
            CHEST = new BufferedImage[]{
                    read(new File("src/assets/Items/Chest/chest0.png")),
                    read(new File("src/assets/Items/Chest/chest1.png")),
                    read(new File("src/assets/Items/Chest/chest2.png")),
                    read(new File("src/assets/Items/Chest/chest3.png")),
                    read(new File("src/assets/Items/Chest/chest4.png")),
                    read(new File("src/assets/Items/Chest/chest5.png")),
                    read(new File("src/assets/Items/Chest/chest6.png")),
                    read(new File("src/assets/Items/Chest/chest7.png")),
                    read(new File("src/assets/Items/Chest/chest6.png")),
                    read(new File("src/assets/Items/Chest/chest5.png")),
                    read(new File("src/assets/Items/Chest/chest4.png")),
                    read(new File("src/assets/Items/Chest/chest3.png")),
                    read(new File("src/assets/Items/Chest/chest2.png")),
                    read(new File("src/assets/Items/Chest/chest1.png")),
                    read(new File("src/assets/Items/Chest/chest0.png"))};



        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
