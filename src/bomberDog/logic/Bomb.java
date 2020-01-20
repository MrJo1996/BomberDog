package bomberDog.logic;

import bomberDog.utils.Loader;

import java.awt.*;

public class Bomb extends Entity {

    // --- CONSTANTS ---
    private static final int DIM_BOMB = 35;
    public static final int SEC_FOR_THE_EXPLOSION = 1500;   //era 3000

    // --- STATUS ---
    private boolean statusBomb;


    //Costruttore Bomba (passo come parametri, una volta che viene premuto Space, la pos del Player (x,y) )
    public Bomb(int pX, int pY) {

        this.x = pX + 10;
        this.y = pY + 20;   //Addizioni per centrare la bomba ai piedi del player
        this.width = DIM_BOMB;
        this.height = DIM_BOMB;
        this.loader = new Loader();
        this.sprite = loader.getBombImg(0);
        this.setStatusBomb(true);
    }

    public void setStatusBomb(boolean statusBomb) {
        this.statusBomb = statusBomb;
    }

    public boolean getStatusBomb() {
        return statusBomb;
    }
}
