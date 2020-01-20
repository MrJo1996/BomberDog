package bomberDog.utils;

import bomberDog.GUI.GamePanel;
import bomberDog.logic.*;

public class TimerFire {

    public final int FIRE_RELEASE_TIME = 1500;

    // --- * STATUS * ---
    private boolean runningStatus;
    // --- ** ---

    private int time; // controllo durata tempo controllo (1500ms)
    private GameManager gameManager;
    private Player player;
    private Fire fire;
    private GamePanel gamePanel;

    // --- * Valori utili a capire come fare il controllo Player con Fuoco generato * ---
    private int up;
    private int down;
    private int right;
    private int left;
    // --- ** ---

    public TimerFire(GameManager pGameManager, Player pPlayer, Fire pFire, int pUp, int pDown, int pRight, int pLeft, GamePanel pGamePanel) {
        this.gameManager = pGameManager;
        this.player = pPlayer;
        this.fire = pFire;
        this.up = pUp;
        this.down = pDown;
        this.right = pRight;
        this.left = pLeft;
        this.gamePanel = pGamePanel;
    }

    private class TimerThread implements Runnable {
        @Override
        public void run() {
            while (runningStatus == true) {
                time++;

                //Controllo se il player può morire (se c'è il fuoco)
                if (player.isPossibilityOfDeath()) {
                    //Solo se c'è il fuoco passo a questo controllo (controllo se c'è una collisione tra Fuoco appena creato e Player
                    //Viene controllato per tutta la durata del Fuoco (1500 ms)

                    if (!player.isImmortality()) { //se è attivo il powerUp dell'immortalità non controllo collisioni con nemici o fuoco
                        checkFirePlayer();
                    }

                    //Controllo fino a dove creare fuoco per ogni direzione
                    //Quindi controllo collisioni con Bordi e Blocchi fissi
                    int up = CollisionsManager.checkFireCollisionsBlockUP(gamePanel.getBlocks(), fire, gamePanel);
                    int down = CollisionsManager.checkFireCollisionsBlockDOWN(gamePanel.getBlocks(), fire, gamePanel);
                    int left = CollisionsManager.checkFireCollisionsBlockSX(gamePanel.getBlocks(), fire, gamePanel);
                    int right = CollisionsManager.checkFireCollisionsBlockDX(gamePanel.getBlocks(), fire, gamePanel);

                    //Eseguo controllo collisioni con blocchi rimovibili e controllo Fuoco-Nemico per ogni direzione in base alle var settate prima
                    CollisionsManager.checkFireCollisionsSX(gamePanel.getRemovableObstacles(), fire, player, gamePanel.getEnemies(), left); //settano a null img se c'è collisione
                    CollisionsManager.checkFireCollisionsDX(gamePanel.getRemovableObstacles(), fire, player, gamePanel.getEnemies(), right);
                    CollisionsManager.checkFireCollisionsUP(gamePanel.getRemovableObstacles(), fire, player, gamePanel.getEnemies(), up);
                    CollisionsManager.checkFireCollisionsDOWN(gamePanel.getRemovableObstacles(), fire, player, gamePanel.getEnemies(), down);

                }

                if (time > 1) {
                    player.setPossibilityOfDeath(false);
                    //essendo terminata l'esplosione scompare la possibiltà di morte per il player da fuoco, termina così anche il disegno del Fuoco (drawFire)
                    stopTimer();
                    gameManager.setGAME_OVER(false); //PER PROVARE DIVERSE COLLISIONI
                }

                try {

                    Thread.sleep(FIRE_RELEASE_TIME); //durata rilascio fuoco

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startTimer() {
        this.time = 0;
        this.runningStatus = true;
        Thread timer = new Thread(new TimerThread());
        timer.start();
    }

    public void stopTimer() {
        this.runningStatus = false;
    }


    public void checkFirePlayer() { //Check morte con Fuoco in base al numero di blocchi di fuoco per una certa direzione
        //Es.
        if (up > 0) {
            if (up == 1) {

                if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX(), fire.getY() - 50, 50, 50)) {
                    player.setLife(player.getLife() - 1);
                    SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                    if (player.getLife() <= 0) {
                        GameManager.GAME_OVER = true;
                    }
                }
                if (up == 2) {
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX(), fire.getY() - 50, 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX(), fire.getY() - 100, 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                }
            }

            if (down > 0) {
                if (down == 1) {
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX(), fire.getY() + 50, 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                }
                if (down == 2) {
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX(), fire.getY() + 50, 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX(), fire.getY() + 100, 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                }
            }

            if (left > 0) {
                if (left == 1) {
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX() - 50, fire.getY(), 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                }
                if (left == 2) {
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX() - 50, fire.getY(), 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX() - 100, fire.getY(), 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                }
            }

            if (right > 0) {
                if (right == 1) {
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX() + 50, fire.getY(), 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                }
                if (right == 2) {
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX() + 50, fire.getY(), 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                    if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX() + 100, fire.getY(), 50, 50)) {
                        player.setLife(player.getLife() - 1);
                        SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                        if (player.getLife() <= 0) {
                            GameManager.GAME_OVER = true;
                        }
                    }
                }
            }

            //Controllo per il blocco di fuoco centrale
            if (CollisionsManager.checkPlayerSingleCollision(player, fire.getX(), fire.getY(), 50, 50)) {
                player.setLife(player.getLife() - 1);
                SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                if (player.getLife() <= 0) {
                    GameManager.GAME_OVER = true;
                }
            }

        }

    }
}
