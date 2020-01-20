package bomberDog.logic;

import bomberDog.utils.Loader;

import java.awt.*;

public class Entity {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Image sprite;
    protected Loader loader;

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Loader getLoader() {
        return loader;
    }

    public Image getImg() {
        return sprite;
    }
}
