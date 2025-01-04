package Game;

import Game.TextObject;

import java.awt.Color;

class Isaac_CounterGrenades extends TextObject
{
    private int number = 1;

    public Isaac_CounterGrenades(int x, int y)
    { super(x,y, new Color(255,255,0,210));
    }

    public String toString()
    { String display = "Grenades: ";
        display += number;
        return display;
    }

    public void setNumber(int n){number=n;}
}
