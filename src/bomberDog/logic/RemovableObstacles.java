package bomberDog.logic;

import bomberDog.utils.Resources;

import java.awt.*;
import java.util.Random;

public class RemovableObstacles extends Entity {

    public static int COLS; // era 8
    public static int ROWS; // era 7

    public boolean DESTROYED;

    public RemovableObstacles(int pX, int pY) {
        this.x = pX;
        this.y = pY;
        this.width = Block.DIM_BLOCK;
        this.height = Block.DIM_BLOCK;
        this.sprite = Resources.getImage("/bomberDog/GUI/images/obstacles/barrelObs.png");
    }

    public Rectangle getBorders() {
        return new Rectangle(this.x, this.y, this.height, this.width);
    }

    public void setDESTROYED(boolean DESTROYED) {
        this.DESTROYED = DESTROYED;
        // this.sprite = null;
    }

    public boolean isDESTROYED() {
        return DESTROYED;
    }

    public void drawRemovableBlock(Graphics g) {
        if (!this.isDESTROYED()) { //se non è distrutto -> Disegnalo
            g.drawImage(sprite, x, y, Block.DIM_BLOCK, Block.DIM_BLOCK, null);
        }
    }

    public static void setCOLS(int COLS) {
        RemovableObstacles.COLS = COLS;
    }

    public static void setROWS(int ROWS) {
        RemovableObstacles.ROWS = ROWS;
    }

    public static int randInitializer(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
