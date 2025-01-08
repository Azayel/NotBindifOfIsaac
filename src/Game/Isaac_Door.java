package Game;


import java.awt.*;

public class Isaac_Door extends GameObject{

    public boolean isUseable=false;

    public Isaac_Door(int x, int y, int radius) {
        super(x,y,0,0,radius,Isaac_TextureEnvironment.door, false);

    }




    @Override
    int type() {
        return Const.TYPE_DOOR;
    }
}
