package Game;

import Game.TextObject;

import java.awt.Color;

class Isaac_HelpText extends TextObject
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
