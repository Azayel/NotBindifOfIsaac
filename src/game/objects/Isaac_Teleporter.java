package game.objects;

import game.engine.objects.AbstractGameObject;
import game.engine.objects.IInteractable;
import game.engine.sound.SoundEngine;
import game.level.Isaac_Level;
import game.map.TeleporterDestination;
import game.sound.Isaac_Sounds;
import game.utils.Const;
import game.utils.Isaac_TextureEnvironment;

public class Isaac_Teleporter extends AbstractGameObject  implements IInteractable {


    TeleporterDestination dest;
    public Isaac_Teleporter(int x, int y, int radius, TeleporterDestination dest) {
        super(x,y,0,0, Isaac_TextureEnvironment.door);
        this.dest=dest;
    }

    @Override
    public void interact(AbstractGameObject avatar) {
        if(!Isaac_Level.instance.getCurrentRoom().isCleared())
            return;
        if(Const.DEBUG_PRINTS)
            System.out.println(dest);
        SoundEngine.instance.playSound(Isaac_Sounds.Teleport);
        Isaac_Level.instance.goThroughRoom(dest);
        Isaac_Level.instance.LoadNewRoom=true;
    }
}
