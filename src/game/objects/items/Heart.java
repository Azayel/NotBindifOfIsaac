package game.objects.items;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.IInteractable;
import game.objects.player.Isaac_Avatar;
import game.utils.Const;
import game.utils.Isaac_TextureItems;

public class Heart extends AbstractGameObject implements IInteractable {
    public Heart(double x_, double y_) {
        super(x_, y_, 100, 0, Isaac_TextureItems.HEART);
    }

    @Override
    public void interact(AbstractGameObject avatar) {
        if(avatar instanceof Isaac_Avatar isaacAvatar){
            isaacAvatar.addHealth(50);
            isLiving=false;
        }
    }

    @Override
    public int type() {
        return Const.TYPE_HEART;
    }
}
