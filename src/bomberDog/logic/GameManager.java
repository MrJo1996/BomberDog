package bomberDog.logic;

import bomberDog.GUI.GamePanel;
import bomberDog.GUI.MainFrame;
import bomberDog.utils.Loader;
import bomberDog.utils.Timer;
import bomberDog.utils.TimerFire;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import static bomberDog.logic.Enemy.randInitializer;

public class GameManager {
    // --- * CONSTANTS * ---
    public static final int SPEED = 50; //10


    // --- * STATUS * ---
    private boolean theBombCanBePlaced = true; /* se è possibile piazzare o meno la bomba. Viene settato dal timer della bomba.
                                                  inizializzato a "true" per permettere il primo piazzamento di Bomba*/
    private boolean statusBomb;                // se è piazzata o meno la bomba che viene creata
    public static boolean GAME_OVER;
    public static boolean ON_GOING;
    public static boolean LOADING;

    public static int NUM_OF_ENEMIES;
    public static int GAME_TIME;
    public static int NUM_OF_STAGE = 1;

    private Player player;
    private GamePanel gamePanel;
    private Bomb bomb;
    private Fire fire;
    private Timer timer;
    private TimerFire timerFire;
    private Loader loader;

    private int indexImg = 0; //indice sprite player in base al num di click
    private int indexImgEnemy = 0;


    public GameManager(Player pPlayer, GamePanel pGamePanel) {
        this.player = pPlayer;
        this.gamePanel = pGamePanel;
        this.loader = new Loader();
    }

    public void dogMovement(int keyPressed) {
        int offset = 1; //Pixel aggiuntivo del rettangolo per l'intersezione

        //controllo una volta sola all'inizio
        if ((CollisionsManager.checkBorders(keyPressed, this.player, this.gamePanel))) {

            switch (keyPressed) {
                case (KeyEvent.VK_UP):
                    player.setImgPlayer(player.getLoader().getDogUp(indexImg));
                    if (!CollisionsManager.checkCollisions(this.player, gamePanel.getBlocks(), 0, -offset)) {
                        if (!CollisionsManager.checkCollisionsREMOVABLE(this.player, gamePanel.getRemovableObstacles(), 0, -offset)) {

                            player.setY(player.getY() - SPEED);
                            indexImg++;
                            if (indexImg == 4) {
                                indexImg = 0;
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    player.setImgPlayer(player.getLoader().getDogDown(indexImg));
                    if (!CollisionsManager.checkCollisions(this.player, gamePanel.getBlocks(), 0, offset)) {
                        if (!CollisionsManager.checkCollisionsREMOVABLE(this.player, gamePanel.getRemovableObstacles(), 0, offset)) {

                            player.setY(player.getY() + SPEED);
                            indexImg++;
                            if (indexImg == 4) {
                                indexImg = 0;
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setImgPlayer(player.getLoader().getDogRight(indexImg));
                    if (!CollisionsManager.checkCollisions(this.player, gamePanel.getBlocks(), offset, 0)) {
                        if (!CollisionsManager.checkCollisionsREMOVABLE(this.player, gamePanel.getRemovableObstacles(), offset, 0)) {

                            this.player.setX(this.player.getX() + SPEED);
                            indexImg++;
                            if (indexImg == 4) {
                                indexImg = 0;
                            }
                        }
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    player.setImgPlayer(player.getLoader().getDogLeft(indexImg));
                    if (!CollisionsManager.checkCollisions(this.player, gamePanel.getBlocks(), -offset, 0)) {
                        if (!CollisionsManager.checkCollisionsREMOVABLE(this.player, gamePanel.getRemovableObstacles(), -offset, 0)) {

                            player.setX(player.getX() - SPEED);
                            indexImg++;
                            if (indexImg == 4) {
                                indexImg = 0;
                            }
                        }
                    }
                    break;
            }
        }
    }

    public void bombPlaced() {
        if (theBombCanBePlaced) {
            this.bomb = new Bomb(player.getX(), player.getY());  //la bomba sarà piazzata al centro dei piedi del player
            this.setStatusBomb(true);    //setta status del piazzamento della bomba a true nel GameManager, così scatta l'azione nel paintComponent
            this.setTheBombCanBePlaced(false); //non permette il piazzamento di ulteriori bombe se una già è stata piazzata
            //(a meno di eventuali bonus). Quando arriva a 4 il timer, passa a true ed è di nuovo possibile piazzare una nuova bomba.
            this.timer = new Timer(this, this.player);   //quando viene piazzata una bomba si crea un Timer per quella bomba
            this.startTimer();        //si avvia il timer che gestirà poi le animazioni per l'esplosione
        } else {
            System.out.println("La bomba non può essere piazzata poichè gia ne è piazzata una");
        }
    }

    public void setStatusBomb(boolean statusBomb) {
        this.statusBomb = statusBomb;
    }

    public boolean getStatusBomb() {
        return this.statusBomb;
    }

    public Bomb getBomb() {
        return bomb;
    }

    public void startTimer() { //avvia il timer
        this.timer.startTimer();
    }

    public void drawExplosion(Graphics g) {
        if (this.timer.getTime() == 0) {
            g.drawImage(this.loader.getBombImg(0), this.getBomb().getX(), this.getBomb().getY(), this.getBomb().getWidth(), this.getBomb().getHeight(), null);
        }
        if (this.timer.getTime() == 1) {
            g.drawImage(this.loader.getBombImg(1), this.getBomb().getX(), this.getBomb().getY(), this.getBomb().getWidth(), this.getBomb().getHeight(), null);
        }
        if (this.timer.getTime() == 2) {
            g.drawImage(this.loader.getBombImg(2), this.getBomb().getX(), this.getBomb().getY(), this.getBomb().getWidth(), this.getBomb().getHeight(), null);
        }
        if (this.timer.getTime() == 3) {
            g.drawImage(this.loader.getBombImg(3), this.getBomb().getX() - 10, this.getBomb().getY() - 20, 50, 50, null);
        }
    }

    public void drawFire(Graphics g) {
        //Creo nuovo fuoco per l'ultima bomba estinta
        this.fire = new Fire(this.getBomb().getX() - 10, this.getBomb().getY() - 20);

        int up = CollisionsManager.checkFireCollisionsBlockUP(gamePanel.getBlocks(), this.fire, this.gamePanel);
        int down = CollisionsManager.checkFireCollisionsBlockDOWN(gamePanel.getBlocks(), this.fire, this.gamePanel);
        int left = CollisionsManager.checkFireCollisionsBlockSX(gamePanel.getBlocks(), this.fire, this.gamePanel);
        int right = CollisionsManager.checkFireCollisionsBlockDX(gamePanel.getBlocks(), this.fire, this.gamePanel);

        //Disegno prima immagine FUOCO (img centrale), passandogli le coordinate di dove è stata piazzata l'ultima bomba piazzata dal player
        g.drawImage(this.loader.getFireImg(0), this.fire.getX(), this.fire.getY(), 50, 50, null);

        // --- * Controlli per capire come disegnare il FUOCO * ---
        if (up > 0) {
            if (up == 1) {
                g.drawImage(this.loader.getFireImg(2), this.fire.getX(), this.fire.getY() - 50, 50, 50, null);
            }
            if (up == 2) {
                g.drawImage(this.loader.getFireImg(6), this.fire.getX(), this.fire.getY() - 50, 50, 50, null);
                g.drawImage(this.loader.getFireImg(2), this.fire.getX(), this.fire.getY() - 100, 50, 50, null);
            }
        }

        if (down > 0) {
            if (down == 1) {
                g.drawImage(this.loader.getFireImg(4), this.fire.getX(), this.fire.getY() + 50, 50, 50, null);
            }
            if (down == 2) {
                g.drawImage(this.loader.getFireImg(6), this.fire.getX(), this.fire.getY() + 50, 50, 50, null);
                g.drawImage(this.loader.getFireImg(4), this.fire.getX(), this.fire.getY() + 100, 50, 50, null);
            }
        }

        if (left > 0) {
            if (left == 1) {
                g.drawImage(this.loader.getFireImg(5), this.fire.getX() - 50, this.fire.getY(), 50, 50, null);
            }
            if (left == 2) {
                g.drawImage(this.loader.getFireImg(1), this.fire.getX() - 50, this.fire.getY(), 50, 50, null);
                g.drawImage(this.loader.getFireImg(5), this.fire.getX() - 100, this.fire.getY(), 50, 50, null);
            }
        }

        if (right > 0) {
            if (right == 1) {
                g.drawImage(this.loader.getFireImg(3), this.fire.getX() + 50, this.fire.getY(), 50, 50, null);
            }
            if (right == 2) {
                g.drawImage(this.loader.getFireImg(1), this.fire.getX() + 50, this.fire.getY(), 50, 50, null);
                g.drawImage(this.loader.getFireImg(3), this.fire.getX() + 100, this.fire.getY(), 50, 50, null);
            }
        }
        // --- ** ---

        //Avvio TimerFire - Timer che detterà la durata dell'animazione del fuoco e che setterà a false la possibilità di morte per il player solo
        // dopo che sarà terminato il periodo di animazione, così da aggiornare anche il gamePanel, di ripulirlo
        // --- * CONTROLLA ANCHE LE EVENTUALI COLLISIONI DEL PLAYER CON IL FUOCO.  * ---
        this.timerFire = new TimerFire(this, this.player, this.fire, up, down, right, left, gamePanel); //passo "direzioni" per capire dove controllare collisioni del fuoco
        this.timerFire.startTimer();
    }


    public void enemiesMovement(ArrayList<Enemy> pEnemies) {
        int offset = 1; //Pixel aggiuntivo del rettangolo per l'intersezione

        for (int i = 0; i < pEnemies.size(); i++) {
            if ((CollisionsManager.checkBordersEnemies(pEnemies.get(i).getDIRECTION(), pEnemies, i))) {
                switch (pEnemies.get(i).getDIRECTION()) {

                    case Enemy.RIGHT:
                        if (!CollisionsManager.checkCollisionsEnemies(pEnemies, gamePanel.getBlocks(), offset, 0, i)) {
                            if (!CollisionsManager.checkCollisionsRemovableEnemies(pEnemies, gamePanel.getRemovableObstacles(), offset, 0, i)) {
                                pEnemies.get(i).setImg(loader.getEnemyRight(indexImgEnemy));
                                pEnemies.get(i).setX(pEnemies.get(i).getX() + SPEED);
                                indexImgEnemy++;
                                if (indexImgEnemy == 2) {
                                    indexImgEnemy = 0;
                                }
                            } else {
                                pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                            }
                        } else {
                            pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                        }

                        break;

                    case Enemy.LEFT:
                        if (!CollisionsManager.checkCollisionsEnemies(pEnemies, gamePanel.getBlocks(), -offset, 0, i)) {
                            if (!CollisionsManager.checkCollisionsRemovableEnemies(pEnemies, gamePanel.getRemovableObstacles(), -offset, 0, i)) {

                                pEnemies.get(i).setImg(loader.getEnemyLeft(indexImgEnemy));
                                pEnemies.get(i).setX(pEnemies.get(i).getX() - SPEED);
                                indexImgEnemy++;
                                if (indexImgEnemy == 2) {
                                    indexImgEnemy = 0;
                                }
                            } else {
                                pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                            }
                        } else {
                            pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                        }
                        break;

                    case Enemy.UP:
                        if (!CollisionsManager.checkCollisionsEnemies(pEnemies, gamePanel.getBlocks(), 0, -offset, i)) {
                            if (!CollisionsManager.checkCollisionsRemovableEnemies(pEnemies, gamePanel.getRemovableObstacles(), 0, -offset, i)) {

                                pEnemies.get(i).setImg(loader.getEnemyUp(indexImgEnemy));
                                pEnemies.get(i).setY(pEnemies.get(i).getY() - SPEED);
                                indexImgEnemy++;
                                if (indexImgEnemy == 2) {
                                    indexImgEnemy = 0;
                                }
                            } else {
                                pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                            }
                        } else {
                            pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                        }
                        break;

                    case Enemy.DOWN:
                        if (!CollisionsManager.checkCollisionsEnemies(pEnemies, gamePanel.getBlocks(), 0, offset, i)) {
                            if (!CollisionsManager.checkCollisionsRemovableEnemies(pEnemies, gamePanel.getRemovableObstacles(), 0, offset, i)) {

                                pEnemies.get(i).setImg(loader.getEnemyDown(indexImgEnemy));
                                pEnemies.get(i).setY(pEnemies.get(i).getY() + SPEED);
                                indexImgEnemy++;
                                if (indexImgEnemy == 2) {
                                    indexImgEnemy = 0;
                                }
                            } else {
                                pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                            }
                        } else {
                            pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
                        }
                        break;
                }
            } else {
                pEnemies.get(i).setDIRECTION(randInitializer(0, 3));
            }

        }

    }

    public void setTheBombCanBePlaced(boolean theBombCanBePlaced) {
        this.theBombCanBePlaced = theBombCanBePlaced;
    }

    public boolean isGAME_OVER() {
        return GAME_OVER;
    }

    public void setGAME_OVER(boolean GAME_OVER) {
        this.GAME_OVER = GAME_OVER;
    }


    public static int randInitializer(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }


}
