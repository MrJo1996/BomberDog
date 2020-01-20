package bomberDog.logic;

import bomberDog.utils.Resources;

import java.awt.*;

public class Block extends Entity {

    public static final int COLS = 7; //era 15
    public static final int ROWS = 6; //era 13

    public static final int DIM_BLOCK = 50;


    public Block(int pX, int pY, int pW, int pH) {
        this.sprite = Resources.getImage("/bomberDog/GUI/images/obstacles/woodBlock.png");
        this.x = pX;
        this.y = pY;
        this.width = pW;
        this.height = pH;
        //this.setBounds(pX, pY, pW, pH);  utile quando estendo a Rectangle la classe per controllare coordinate di ogni blocco tramite Listener nel gamePanel
    }

    //Per controllo collisioni
    public Rectangle getBorders() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    //Disegna nel gamePanel l'hotArea dei blocchi in giallo, utile per vedere se è creata bene l'hot
    public void drawRectangleBlock(Graphics g) {
        g.drawImage(sprite, x, y, Block.DIM_BLOCK, Block.DIM_BLOCK, null);
    }
}



