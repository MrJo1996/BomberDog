package bomberDog.logic;

import bomberDog.utils.Loader;

import java.awt.*;

public class Fire extends Entity {

    public Fire(int pX, int pY) {
        this.x = pX;
        this.y = pY;
        this.height = Block.DIM_BLOCK;
        this.width = Block.DIM_BLOCK;
        this.loader = new Loader();
        this.sprite = loader.getFireImg(0);
    }

    public Rectangle getBorders() {
        return new Rectangle(this.x, this.y, this.height, this.width);
    }
}
