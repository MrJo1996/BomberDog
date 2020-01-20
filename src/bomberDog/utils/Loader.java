package bomberDog.utils;

import java.awt.*;

public class Loader {

    private Image[] dogUp;
    private Image[] dogDown;
    private Image[] dogRight;
    private Image[] dogLeft;

    private Image[] bomb;

    private Image[] fire;

    private Image[] bonus;

    private Image[] enemyUp;
    private Image[] enemyDown;
    private Image[] enemyLeft;
    private Image[] enemyRight;

    public Loader() {

        //Img Movimento Dog
        this.dogUp = new Image[4];
        dogUp[0] = Resources.getImage("/bomberDog/GUI/images/personaggio/diSpalle1.png");
        dogUp[1] = Resources.getImage("/bomberDog/GUI/images/personaggio/diSpalle2.png");
        dogUp[2] = Resources.getImage("/bomberDog/GUI/images/personaggio/diSpalle3.png");
        dogUp[3] = Resources.getImage("/bomberDog/GUI/images/personaggio/diSpalle4.png");

        this.dogDown = new Image[4];
        dogDown[0] = Resources.getImage("/bomberDog/GUI/images/personaggio/diFronte1.png");
        dogDown[1] = Resources.getImage("/bomberDog/GUI/images/personaggio/diFronte2.png");
        dogDown[2] = Resources.getImage("/bomberDog/GUI/images/personaggio/diFronte3.png");
        dogDown[3] = Resources.getImage("/bomberDog/GUI/images/personaggio/diFronte4.png");

        this.dogRight = new Image[4];
        dogRight[0] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoDX1.png");
        dogRight[1] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoDX2.png");
        dogRight[2] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoDX3.png");
        dogRight[3] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoDX4.png");

        this.dogLeft = new Image[4];
        dogLeft[0] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoSX1.png");
        dogLeft[1] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoSX2.png");
        dogLeft[2] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoSX3.png");
        dogLeft[3] = Resources.getImage("/bomberDog/GUI/images/personaggio/versoSX4.png");

        //Img Bomb
        this.bomb = new Image[4];
        bomb[0] = Resources.getImage("/bomberDog/GUI/images/bomb/bomb1.png");
        bomb[1] = Resources.getImage("/bomberDog/GUI/images/bomb/bomb2.png");
        bomb[2] = Resources.getImage("/bomberDog/GUI/images/bomb/bomb3.png");
        bomb[3] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/esplosione1.png");

        //Img Fire
        this.fire = new Image[7];
        fire[0] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/central.png");
        fire[1] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/middleSXDX.png");
        fire[2] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/finalUP.png");
        fire[3] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/finalDX.png");
        fire[4] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/finalDOWN.png");
        fire[5] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/finalSX.png");
        fire[6] = Resources.getImage("/bomberDog/GUI/images/bomb/explosion/middleUPDOWN.png");

        //Img Bonus
        this.bonus = new Image[4];
        bonus[0] = Resources.getImage("/bomberDog/GUI/images/bonus/fuocoPowerUp.png");
        bonus[1] = Resources.getImage("/bomberDog/GUI/images/bonus/1000pt.png");
        bonus[2] = Resources.getImage("/bomberDog/GUI/images/bonus/bone.png");
        bonus[3] = Resources.getImage("/bomberDog/GUI/images/bonus/500pt.png");

        //Img Enemy
        this.enemyDown = new Image[2];
        enemyDown[0] = Resources.getImage("/bomberDog/GUI/images/enemy/versoGiu1.png");
        enemyDown[1] = Resources.getImage("/bomberDog/GUI/images/enemy/versoGiu2.png");

        this.enemyUp = new Image[2];
        enemyUp[0] = Resources.getImage("/bomberDog/GUI/images/enemy/versoSopra1.png");
        enemyUp[1] = Resources.getImage("/bomberDog/GUI/images/enemy/versoSopra2.png");

        this.enemyRight = new Image[2];
        enemyRight[0] = Resources.getImage("/bomberDog/GUI/images/enemy/versoDx1.png");
        enemyRight[1] = Resources.getImage("/bomberDog/GUI/images/enemy/versoDx2.png");

        this.enemyLeft = new Image[2];
        enemyLeft[0] = Resources.getImage("/bomberDog/GUI/images/enemy/versoSx1.png");
        enemyLeft[1] = Resources.getImage("/bomberDog/GUI/images/enemy/versoSx2.png");

    }

    public Image getDogUp(int count) { //restituisco l'immagine in base al numero di volte che è stata premuta
        return dogUp[count];           // una certa freccetta direzionale.
    }

    public Image getDogDown(int count) {
        return dogDown[count];
    }

    public Image getDogRight(int count) {
        return dogRight[count];
    }

    public Image getDogLeft(int count) {
        return dogLeft[count];
    }

    public Image getBombImg(int count) {
        return bomb[count];
    }

    public Image getFireImg(int count) {
        return fire[count];
    }

    public Image getBonusImg(int count) {
        return bonus[count];
    }

    public Image getEnemyUp(int count) {
        return enemyUp[count];
    }

    public Image getEnemyDown(int count) {
        return enemyDown[count];
    }

    public Image getEnemyLeft(int count) {
        return enemyLeft[count];
    }

    public Image getEnemyRight(int count) {
        return enemyRight[count];
    }
}
