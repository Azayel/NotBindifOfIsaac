package Game;

import Game.TextObject;

import java.awt.Color;

class Isaac_Counter extends TextObject
{
    private int number = 1;

    public Isaac_Counter(int x, int y)
    { super(x,y, new Color(255,255,0,210));
    }

    public String toString()
    { String display = "Zombies: ";
        display += number;
        return display;
    }

    public void increment(){ number++; }
}
