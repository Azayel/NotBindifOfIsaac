package Game;

import java.awt.*;

public class RoomBackgroundGameObject extends GameObject {


    public RoomBackgroundGameObject(Image texture_) {
        super(texture_);
    }

    @Override
    int type() {
        return 0;
    }
}
