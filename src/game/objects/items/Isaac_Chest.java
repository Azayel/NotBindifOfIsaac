package game.objects.items;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.GameObjectList;
import game.engine.objects.IInteractable;
import game.level.Isaac_Level;
import game.map.Isaac_World;
import game.utils.Isaac_TextureItems;

public class Isaac_Chest extends AbstractAnimatedGameObject implements IInteractable {


    public Isaac_Chest(double x_, double y_) {
        super(x_, y_, 0, 0, Isaac_TextureItems.CHEST, true);
    }

    @Override
    public void interact(AbstractGameObject avatar) {
        //Generate new Level
        if(Isaac_Level.instance.getCurrentRoom().isCleared()) {
            Isaac_Level.instance.CreateLevel();
            Isaac_Level.instance.LoadNewRoom=true;
            ((Isaac_World)world).addScore(1000);
        }
    }
}
