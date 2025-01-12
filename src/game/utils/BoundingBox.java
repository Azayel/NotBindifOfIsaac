package game.utils;

public class BoundingBox {
    public double x, y, width, height;

    public BoundingBox(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(BoundingBox other) {
        return x < other.x + other.width && x + width > other.x && y < other.y + other.height && y + height > other.y;
    }

    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
}
