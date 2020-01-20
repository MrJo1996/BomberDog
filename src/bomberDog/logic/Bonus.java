package bomberDog.logic;

import bomberDog.GUI.GamePanel;
import bomberDog.utils.Loader;
import bomberDog.utils.SoundManager;

import java.awt.*;
import java.util.Random;

public class Bonus extends Entity {

    private boolean DESTROYED;
    public static boolean THERE_IS_A_BONUS = false; //se c'è un bonus scatta l'azione nel Runnable di GamePanel, conto alla rovescia per il bonus

    protected static final int DIM_BONUS = 35;

    public static final int LIFE_TIME_FIRE_POWER_UP = 150; //30sec
    public static final int LIFE_TIME_IMMORTALITY_POWER_UP = 100; //20sec

    private int indexBonus;
    private GamePanel gamePanel;
    private int numCol;
    private int numRow;

    public Bonus(int pX, int pY, int pIndexBonus, int i, int j, GamePanel pGamePanel) {
        this.gamePanel = pGamePanel;
        this.x = pX + 10; // controllare valori x e y per migliorare collisioni
        this.y = pY + 20;
        this.width = DIM_BONUS;
        this.height = DIM_BONUS;
        this.numCol = i;
        this.numRow = j;
        this.loader = new Loader();
        this.indexBonus = pIndexBonus;

        if (randInitializer(1, 100) > 60) { //se il numero casuale generato dalla funzione è maggiore di 50 assegna img a bonus in base ad "indexBonus" (generato sempre casualmente)
            this.sprite = loader.getBonusImg(this.indexBonus);
        } else {
            setDESTROYED(true); //non dà vita, img a null, status Empty a true, non verrà disegnato ne incluso nei check
        }

    }

    public void drawBonus(Graphics g) {
        //Se il barile associato è stato distrutto allora disegna (sempre se è associato ad una immagine)
        if (gamePanel.getRemovableObstacles()[this.numCol][this.numRow].isDESTROYED()) {
            if (!this.isDESTROYED()) { //se non è distrutto -> Disegnalo
                setThereIsABonus(true);
                if (CollisionsManager.checkPlayerSingleCollision(gamePanel.getPlayer(), this.x, this.y, this.width, this.height)) {
                    //Se il player interseca il bonus parte l'azione e smette di disegnarlo assegnando al player il bonus
                    // tramite bonusManager
                    bonusManager(this.sprite, this.loader, gamePanel.getPlayer());
                    this.setDESTROYED(true);
                    setThereIsABonus(false);
                }
                g.drawImage(this.sprite, this.x, this.y, Bonus.DIM_BONUS, Bonus.DIM_BONUS, null);
            }
        }
    }

    public void setDESTROYED(boolean DESTROYED) {
        this.DESTROYED = DESTROYED;
        this.sprite = null;
    }

    public boolean isDESTROYED() {
        return DESTROYED;
    }

    public static int randInitializer(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static boolean isThereABonus() {
        return THERE_IS_A_BONUS;
    }

    public static void setThereIsABonus(boolean THERE_IS_A_BONUS) {
        Bonus.THERE_IS_A_BONUS = THERE_IS_A_BONUS;
    }

    public static void bonusManager(Image img, Loader loader, Player player) {
        if (img == loader.getBonusImg(0)) { //FirePowerUp
            SoundManager.musicClipStart(SoundManager.powerUpMusic());
            player.setSuperRange(true);
        }
        if (img == loader.getBonusImg(1)) { //1000pt
            SoundManager.musicClipStart(SoundManager.pointMusic());
            player.setScore(player.getScore() + 1000);
        }
        if (img == loader.getBonusImg(2)) { //ImmortalityPowerUp
            SoundManager.musicClipStart(SoundManager.powerUpMusic());
            player.setImmortality(true);
        }
        if (img == loader.getBonusImg(3)) { //500pt
            SoundManager.musicClipStart(SoundManager.pointMusic());
            player.setScore(player.getScore() + 500);
        }
    }

}
