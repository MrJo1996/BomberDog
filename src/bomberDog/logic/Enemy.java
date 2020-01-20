package bomberDog.logic;

import bomberDog.utils.Loader;

import java.awt.*;
import java.util.Random;

public class Enemy extends Entity {

    protected static final int DIM_ENEMY = 50;
    protected static final int POINTS = 250;

    private int DIRECTION;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public Enemy(int pX, int pY) {
        this.x = pX;
        this.y = pY;
        this.width = DIM_ENEMY;
        this.height = DIM_ENEMY;
        this.loader = new Loader();
        this.sprite = loader.getEnemyDown(0);
        this.DIRECTION = randInitializer(0, 3);
    }

    public void drawEnemy(Graphics g) {
        g.drawImage(sprite, x, y, DIM_ENEMY, DIM_ENEMY, null);
    }

    public void setImg(Image img) {
        this.sprite = img;
    }

    public Rectangle getBorders() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public int getDIRECTION() {
        return DIRECTION;
    }

    public void setDIRECTION(int DIRECTION) {
        this.DIRECTION = DIRECTION;
    }

    public static int randInitializer(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
