package bomberDog.utils;

import bomberDog.logic.Bomb;
import bomberDog.logic.Entity;
import bomberDog.logic.GameManager;
import bomberDog.logic.Player;

public class Timer extends Entity {
    private int time; //indice immagine in base al tempo passato
    private boolean runningStatus;

    private GameManager gameManager;
    private Player player;

    public Timer(GameManager pGameManager, Player pPlayer) {
        this.gameManager = pGameManager;
        this.player = pPlayer;
    }

    private class TimerThread implements Runnable {
        @Override
        public void run() {
            while (runningStatus == true) {
                time++;
                if (time > 3) {
                    SoundManager.musicClipStart(SoundManager.bombMusic());
                    stopTimer();
                    //false runningStatus, ferma il timer
                    gameManager.setTheBombCanBePlaced(true);
                    //solo quando termina l'esplosione sarà possibile piazzare una nuova bomba
                    player.setPossibilityOfDeath(true);
                    //setto a "true" la possibilità di morire per il giocatore in quanto la bomba
                    // rilascia fuoco, scatta azione di check collisioni  e disegno fuoco
                    gameManager.setStatusBomb(false);
                    //setto a false così che non continui il controllo nel gamePanel(in PaintComponent) - in quanto rimarrebbe sempre a true
                    //ed ogni 50ms controllerebbe lo status del piazzamento, passa a true ogni volta che viene piazzata una bomba (tasto space)
                    //se commentata tale istruzione fa vedere il System.println in drawExplosion di GameManager
                }
                try {
                    Thread.sleep(Bomb.SEC_FOR_THE_EXPLOSION / 4); //
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startTimer() {
        this.time = -1; //parte da tale numero altrimenti non avrebbe mai disegnato la prima immagine
        this.runningStatus = true;
        Thread timer = new Thread(new TimerThread());
        timer.start();
    }

    public void stopTimer() {
        this.runningStatus = false;
    }

    public int getTime() {
        return time;
    }
}

