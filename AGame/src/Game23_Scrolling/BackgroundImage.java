package Game23_Scrolling;

public class BackgroundImage extends A_GameObject {

    public BackgroundImage(double x, double y)
    { super(x,y, MapBackground.mainBackground);
        this.isMoving = false;
    }


    public int type() { return A_Const.TYPE_BACKGROUNDIMAGE; }

}
