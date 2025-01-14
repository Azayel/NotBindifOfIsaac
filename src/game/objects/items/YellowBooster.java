package game.objects.items;

import game.engine.objects.AbstractAnimatedGameObject;
import game.engine.objects.AbstractGameObject;
import game.engine.objects.IInteractable;
import game.engine.sound.SoundEngine;
import game.level.Isaac_Level;
import game.map.Isaac_World;
import game.objects.player.Isaac_Avatar;
import game.sound.Isaac_Sounds;
import game.utils.Isaac_TextureItems;

public class YellowBooster extends AbstractAnimatedGameObject implements IInteractable {

    public YellowBooster(double _x, double _y){

        super(_x, _y, 0, 0, Isaac_TextureItems.Y_BOOSTER, true);
    }

    @Override
    public void interact(AbstractGameObject avatar) {
        if(avatar instanceof Isaac_Avatar isaacAvatar){
            ((Isaac_World)world).mod_speed();
            SoundEngine.instance.playSound(Isaac_Sounds.HeartPickup);
            isLiving=false;
        }
    }

}
