package game.engine.objects;


import game.engine.world.AbstractWorld;
import game.map.DoorDirection;
import game.utils.BoundingBox;
import game.utils.Const;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class AbstractGameObject
{
    // yes, public  :(
    //
    public double  x,y;
    public double  alfa  = 0;
    public double  speed = 0;
    public int     radius = 7;
    public Color   color;
    public Image texture;
    public boolean hasTexture = false;
    public boolean isbackgroundImage = false;
    public boolean isUseable = false;

    // if the object is existing, moving etc
    public boolean isLiving = true;
    public boolean isMoving = true;

    // destination the object shall move to,
    // old position etc
    protected double  destX, destY;
    protected boolean hasDestination = false;
    protected double  xOld,  yOld;



    protected BoundingBox boundingBox;


    // GameObjects sometimes call physics methods
    public static AbstractWorld world;


    //Doors
    private DoorDirection doorDirection;


    // construct GameObject
    public AbstractGameObject(double x_, double y_,
                              double a_, double s_,
                              int radius_, Color color_)
    {
        x=x_;    y=y_;
        xOld=x;  yOld=y;
        alfa=a_; speed=s_;
        radius=radius_;
        color = color_;
        hasTexture = false;
        boundingBox = new BoundingBox(x, y, radius*2.0, radius*2.0);
    }

    public AbstractGameObject(double x_, double y_,
                              double a_, double s_,
                              int radius_, BufferedImage texture_)
    {
        x=x_;    y=y_;
        xOld=x;  yOld=y;
        alfa=a_; speed=s_;
        radius=radius_;
        texture=texture_;
        hasTexture = true;
        boundingBox = new BoundingBox(x, y, radius*2.0, radius*2.0);
    }

    public AbstractGameObject(double x_, double y_,
                              double a_, double s_,
                              int radius_, Image texture_)
    {
        x=x_;    y=y_;
        xOld=x;  yOld=y;
        alfa=a_; speed=s_;
        radius=radius_;
        texture=texture_;
        hasTexture = true;
        boundingBox = new BoundingBox(x, y, radius*2.0, radius*2.0);
    }

    //TODO this should not exist here, why should this abstract object know what doors are???
    public AbstractGameObject(double x_, double y_,
                              double a_, double s_,
                              int radius_, Image texture_,
                              DoorDirection doorDirection)
    {
        x=x_;    y=y_;
        xOld=x;  yOld=y;
        alfa=a_; speed=s_;
        radius=radius_;
        texture=texture_;
        hasTexture = true;
        this.doorDirection = doorDirection;
        boundingBox = new BoundingBox(x, y, radius, radius);
        System.out.println("Bounding Box Created for door : " + boundingBox.x + " " + boundingBox.y + " " + boundingBox.width + " " + boundingBox.height);
    }

    public AbstractGameObject(Image texture_){
        x=0;    y=0;
        xOld=x;  yOld=y;
        alfa=0; speed=0;
        radius = 0;
        texture=texture_;
        hasTexture = true;
        isbackgroundImage=true;
        boundingBox = new BoundingBox(-99999, -99999, Const.WORLDPART_WIDTH, Const.WORLDPART_HEIGHT);
    }




    // move one step to direction <alfa>
//    public void move(double diffSeconds)
//    {
//        if(!isMoving) return;
//
//        // move if object has a destination
//        if(hasDestination)
//        {
//            // stop if destination is reached
//            double diffX = Math.abs(x-destX);
//            double diffY = Math.abs(y-destY);
//            if(diffX<3 && diffY<3)
//            { isMoving = false;
//                return;
//            }
//        }
//
//        // remember old position
//        xOld=x; yOld=y;
//
//        // move one step
//        double step_x = Math.cos(alfa)*speed*diffSeconds;
//        double step_y = Math.sin(alfa)*speed*diffSeconds;
//        x += step_x;
//        y += step_y;
//        this.boundingBox.move(step_x, step_y);
//    }


    abstract public void process(double diffSeconds);

   // public void move(double dx, double dy);


    // test and reflect on Window Borders
    public void reflectOnBorders()
    {
        double rad = radius;
        double PI  = Math.PI;

        if(x < rad && (alfa > PI/2 && alfa < PI*3/2))
        { alfa = Math.PI-alfa;
        }
        if(y < rad && alfa > PI)
        { alfa = PI*2-alfa;
        }
        if(x > Const.WORLD_WIDTH-rad)
        { alfa = Math.PI-alfa;
        }
        if(y > Const.WORLD_HEIGHT-rad)
        { alfa = PI*2-alfa;
        }


        if(alfa<0)    alfa += PI*2;
        if(alfa>PI*2) alfa -= PI*2;
    }


    // set a point in the world as destination
    public final void setDestination(double dx, double dy)
    {
        isMoving       = true;
        hasDestination = true;
        destX          = dx;
        destY          = dy;

        alfa = Math.atan2(dy-y, dx-x);
    }


    // set the LOCATION of an object as destination
    public void setDestination(AbstractGameObject obj)
    { setDestination(obj.x, obj.y);
    }


    // move back to the position BEFORE the move Method was called
    public void moveBack() { x=xOld; y=yOld; }

    public void setX(double x) {
        this.x = x;
        this.xOld = x;
        this.boundingBox.x = x;
    }

    public void setY(double y) {
        this.y = y;
        this.yOld = y;
        this.boundingBox.y = y;
    }

    public abstract int type();
    public static void setWorld(AbstractWorld w) {world=w;}
    public BoundingBox getBoundingBox() {return boundingBox;}

    public DoorDirection getDoorDirection() {return doorDirection;}

}