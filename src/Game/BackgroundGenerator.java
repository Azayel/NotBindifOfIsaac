package Game;

import Game.Const;

import java.awt.image.BufferedImage;

public class BackgroundGenerator {

    public BufferedImage Background;

    public BackgroundGenerator(BufferedImage Background) {
        this.Background = Background;
    }

    private BufferedImage GenerateBackgroundImage(int PositionX, int PositionY){
        if (Background == null) {
            throw new IllegalArgumentException("Background image is not set.");
        }
        // smaler than 0?
        if (PositionX < 0) {
            PositionX = 0;
        }
        if (PositionY < 0) {
            PositionY = 0;
        }
        //bigger than max size?
        if (PositionX+ Const.WORLDPART_WIDTH > Background.getWidth()) {
            PositionX = Background.getWidth() - Const.WORLDPART_WIDTH;
        }
        if (PositionY+Const.WORLDPART_HEIGHT > Background.getHeight()) {
            PositionY = Background.getHeight() - Const.WORLDPART_HEIGHT;
        }

        return Background.getSubimage(PositionX,PositionY,Const.WORLDPART_WIDTH, Const.WORLDPART_WIDTH);
    }

    public BufferedImage GetBackgroundImage(int PositionX, int PositionY) {
        return GenerateBackgroundImage(PositionX, PositionY);
    }
}
