package Game;


import Game.*;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

class Panel extends JPanel implements GraphicSystem
{
    // constants
    private static final long serialVersionUID = 1L;
    private static final Font font = new Font("Arial",Font.PLAIN,24);


    // InputSystem is an external instance
    private InputSystem inputSystem = new InputSystem();
    private World world       = null;


    // GraphicsSystem variables
    //
    private GraphicsConfiguration graphicsConf =
            GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration();
    private BufferedImage imageBuffer;
    private Graphics      graphics;



    public Panel()
    {
        this.setSize(Const.WORLDPART_WIDTH,Const.WORLDPART_HEIGHT);
        imageBuffer = graphicsConf.createCompatibleImage(
                this.getWidth(), this.getHeight());
        graphics = imageBuffer.getGraphics();

        // initialize Listeners
        this.addMouseListener(inputSystem);
        this.addMouseMotionListener(inputSystem);
        this.addKeyListener(inputSystem);
    }

    public void clear()
    { graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(
                0, 0,Const.WORLDPART_WIDTH,Const.WORLDPART_HEIGHT);
    }

    public final void draw(BufferedImage image){

    }

    public final void draw(GameObject dot)
    {
        int x = (int)(dot.x-dot.radius-world.worldPartX);
        int y = (int)(dot.y-dot.radius-world.worldPartY);
        int d = (int)(dot.radius*2);

        if( dot.hasTexture){
            graphics.drawImage(dot.texture, x,y,d,d,null);
        }else {
            graphics.setColor(dot.color);
            graphics.fillOval(x, y, d, d);
            graphics.setColor(Color.DARK_GRAY);
            graphics.drawOval(x, y, d, d);
        }
    }

    public final void draw(TextObject text)
    {
        graphics.setFont(font);
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawString(text.toString(), (int)text.x+1, (int)text.y+1);
        graphics.setColor(text.color);
        graphics.drawString(text.toString(), (int)text.x, (int)text.y);
    }


    public void redraw()
    { this.getGraphics().drawImage(imageBuffer, 0, 0, this);
    }

    public final InputSystem getInputSystem() { return inputSystem; }
    public final void setWorld(World world_)  {this.world = world_;}
}
