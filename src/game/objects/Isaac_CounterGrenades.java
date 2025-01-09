package game.objects;


import game.engine.objects.AbstractTextObject;

import java.awt.Color;

public class Isaac_CounterGrenades extends AbstractTextObject
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
