package game.objects;

import game.engine.objects.AbstractTextObject;

import java.awt.Color;

public class Isaac_HelpText extends AbstractTextObject
{
    public Isaac_HelpText(int x, int y)
    { super(x,y, new Color(0,120,255,60));
    }

    public String toString()
    { String display = "MOVE:Mouse left      SHOOT:Mouse right      "+
            "Grenade:Space bar     END: Escape";
        return display;
    }

}
