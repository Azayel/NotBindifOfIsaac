package Game;

import Game.*;

import javax.swing.*;

class Frame extends JFrame
{
    // ...ok...
    private static final long serialVersionUID = 2L;

    private Panel panel = null;

    public Frame()
    { this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Const.WORLDPART_WIDTH+2,Const.WORLDPART_HEIGHT+2);

        this.setAlwaysOnTop(false);
        this.setUndecorated(false);

        this.setResizable(false);

        panel = new Panel();

        // needed for Keyboard input !!!
        panel.setFocusable(true);
        panel.requestFocusInWindow();

        this.setContentPane(panel);
    }

    public void displayOnScreen() { validate(); setVisible(true); }

    public GraphicSystem getGraphicSystem() { return panel; }
    public InputSystem getInputSystem()   { return panel.getInputSystem(); }
}
