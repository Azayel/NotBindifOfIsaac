package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static javax.imageio.ImageIO.read;

public class Isaac_TextureItems {
    public static BufferedImage HEART;
    public static BufferedImage[] CHEST;
    public static BufferedImage[] Y_BOOSTER;
    public static BufferedImage[] R_BOOSTER;



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
            Y_BOOSTER = new BufferedImage[]{
                    read(new File("src/assets/Items/YellowBooster/Y_Booster1.png")),
                    read(new File("src/assets/Items/YellowBooster/Y_Booster2.png")),
                    read(new File("src/assets/Items/YellowBooster/Y_Booster3.png")),
                    read(new File("src/assets/Items/YellowBooster/Y_Booster4.png")),
                    read(new File("src/assets/Items/YellowBooster/Y_Booster5.png")),
                    read(new File("src/assets/Items/YellowBooster/Y_Booster6.png")),
            };

            R_BOOSTER = new BufferedImage[]{
                    read(new File("src/assets/Items/RedBooster/R_Booster1.png")),
                    read(new File("src/assets/Items/RedBooster/R_Booster2.png")),
                    read(new File("src/assets/Items/RedBooster/R_Booster3.png")),
                    read(new File("src/assets/Items/RedBooster/R_Booster4.png")),
                    read(new File("src/assets/Items/RedBooster/R_Booster5.png")),
                    read(new File("src/assets/Items/RedBooster/R_Booster6.png")),
            };


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading textures!");
        }
    }
}
