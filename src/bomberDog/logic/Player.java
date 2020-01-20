package bomberDog.logic;

import bomberDog.utils.Loader;
import bomberDog.utils.Resources;

import java.awt.*;

public class Player extends Entity {

    // --- STATUS ---
    public static boolean POSSIBILITY_OF_DEATH; //per controllare se c'è una bomba in fase di rilasciamento FUOCO così da attivare azione

    private int score;
    private boolean superRange;
    private boolean immortality;
    private int life = 3;

    public Player(int pX, int pY, int pWidth, int pHeight) {
        this.loader = new Loader();
        this.sprite = Resources.getImage("/bomberDog/GUI/images/personaggio/diFronte1.png");
        this.x = pX;
        this.y = pY;
        this.height = pHeight;
        this.width = pWidth;
    }

    public void setImgPlayer(Image imgPlayer) {
        this.sprite = imgPlayer;
    }

    public void drawPlayer(Graphics g) {
        g.drawImage(sprite, x, y, width, height, null);
    }

    public Rectangle getBorders() {
        return new Rectangle(this.x, this.y + (this.height - GameManager.SPEED), this.width, GameManager.SPEED);
    }

    public static boolean isPossibilityOfDeath() {
        return POSSIBILITY_OF_DEATH;
    }

    public void setPossibilityOfDeath(boolean isThereABomb) {
        POSSIBILITY_OF_DEATH = isThereABomb;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSuperRange() {
        return superRange;
    }

    public void setSuperRange(boolean superRange) {
        this.superRange = superRange;
    }

    public boolean isImmortality() {
        return immortality;
    }

    public void setImmortality(boolean immortality) {
        this.immortality = immortality;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setStandingImg(Image standingImg) {
        this.sprite = loader.getDogDown(0);
    }

        /*public void drawRectanglePlayer(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(this.x, this.y + (this.height - SPEED), this.width, SPEED);
    }*/
}

