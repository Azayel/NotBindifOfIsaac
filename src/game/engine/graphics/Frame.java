package game.engine.graphics;


import game.engine.input.InputSystem;
import game.utils.Const;

import javax.swing.*;

public class Frame extends JFrame
{
    // ...ok...
    private static final long serialVersionUID = 2L;

    private Panel panel = null;

    public Frame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Ensure exact size for the content pane (game panel)
        this.setResizable(false);

        // Create the panel
        panel = new Panel();
        panel.setPreferredSize(new java.awt.Dimension(Const.WORLDPART_WIDTH, Const.WORLDPART_HEIGHT));

        // Add the panel and pack the frame to match its size
        this.setContentPane(panel);
        this.pack();

        // Center the frame on the screen
        this.setLocationRelativeTo(null);

        // Needed for keyboard input
        panel.setFocusable(true);
        panel.requestFocusInWindow();
    }

    public void displayOnScreen() { validate(); setVisible(true); }

    public IGraphicSystem getGraphicSystem() { return panel; }
    public InputSystem getInputSystem()   { return panel.getInputSystem(); }

}
