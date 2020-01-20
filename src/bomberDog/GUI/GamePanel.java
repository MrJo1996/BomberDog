package bomberDog.GUI;

import bomberDog.logic.*;
import bomberDog.utils.SoundManager;
import bomberDog.utils.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {


    private MainFrame mainFrame;
    private boolean running;

    private Player player;
    private GameManager game;
    private Block[][] blocks;
    private Bonus[][] bonuses;
    private RemovableObstacles[][] removableObstacles;
    private ArrayList<Enemy> enemies;

    private Rectangle home;
    private int timeFire; //Per controllo durata bonus
    private int timeImm;
    private int time;

    public GamePanel(MainFrame pMainFrame) {
        this.setSize(MainFrame.WIDTH_PANEL, MainFrame.HEIGHT_PANEL);
        this.setLayout(null);

        this.addMouseListener(new GamePanelListner());
        this.addKeyListener(new GamePanelKeyListener());

        this.home = new Rectangle();
        this.home.setBounds(1071, 525, 50, 50);

        this.mainFrame = pMainFrame;

        this.player = new Player(80, 50, Block.DIM_BLOCK, Block.DIM_BLOCK); //creo player

        //creo HotArea blocchi fissi
        this.blocks = new Block[Block.COLS][Block.ROWS];
        for (int i = 0; i < Block.COLS; i++) {
            int x = (100 * i) + 130;
            for (int j = 0; j < Block.ROWS; j++) {
                if (j == 0) {
                    int y = (50 * j) + 50;
                    this.blocks[i][j] = new Block(x, y, Block.DIM_BLOCK, Block.DIM_BLOCK);
                } else {
                    int y = (100 * j) + 50;
                    this.blocks[i][j] = new Block(x, y, Block.DIM_BLOCK, Block.DIM_BLOCK);
                }
            }
        }

        //creo HotArea blocchi rimovibili (barili) e Bonus Inerenti (casuali)
        RemovableObstacles.setCOLS(RemovableObstacles.randInitializer(5, Block.COLS));
        RemovableObstacles.setROWS(RemovableObstacles.randInitializer(5, Block.ROWS));
        this.removableObstacles = new RemovableObstacles[RemovableObstacles.COLS][RemovableObstacles.ROWS];
        this.bonuses = new Bonus[RemovableObstacles.COLS][RemovableObstacles.ROWS];
        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            int x;
            int y;
            if (RemovableObstacles.randInitializer(0, 100) > 50) {
                x = (100 * i) + 130 - 50;
            } else {
                x = (150 * i) + 130 - 50;
            }
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                if (j == 0) {
                    y = (50 * j);
                    this.removableObstacles[i][j] = new RemovableObstacles(x, y);
                    this.bonuses[i][j] = new Bonus(x, y, bonuses[i][j].randInitializer(0, 3), i, j, this);
                } else {
                    if (RemovableObstacles.randInitializer(0, 100) > 50) {
                        y = (100 * j);
                    } else {
                        y = (150 * j);
                    }
                    this.removableObstacles[i][j] = new RemovableObstacles(x, y);
                    this.bonuses[i][j] = new Bonus(x, y, bonuses[i][j].randInitializer(0, 3), i, j, this);
                }

                //Controllo se l'ultimo barile creato è andato a finire su un blocco fisso, o oltre il limite di gioco.
                //in tal caso rimuovo il barile ed il bonus, non rendendoli visibili.
                if (CollisionsManager.checkBlocks(getBlocks(), removableObstacles[i][j].getX(), removableObstacles[i][j].getY())) {
                    removableObstacles[i][j].setDESTROYED(true);
                    bonuses[i][j].setDESTROYED(true);
                }
            }
        }
        // --- ** ---

        //Creo ArrayList Nemici con coordinate casuali
        enemies = new ArrayList<>();
        GameManager.NUM_OF_ENEMIES = RemovableObstacles.randInitializer(3, 5); //min 3 nemici, max 5
        for (int i = 0; i < GameManager.NUM_OF_ENEMIES; i++) {
            int x = this.blocks[RemovableObstacles.randInitializer(0, Block.COLS - 1)][RemovableObstacles.randInitializer(0, Block.ROWS - 1)].getX() + 50; //-1 altrimenti andrebbe "out of bounds"

            if (x >= CollisionsManager.LIMIT_DX) {
                //controllo che il nemico non superi il limite destro
                //( controllo solo a DX poichè i nemici sono creati con x+50 così da distanziarsi anche dal player il più possibile
                x -= 100;
            }

            int y = this.blocks[RemovableObstacles.randInitializer(0, Block.COLS - 1)][RemovableObstacles.randInitializer(0, Block.ROWS - 1)].getY();

            if (!CollisionsManager.checkEnemiesCoordinates(getRemovableObstacles(), x, y)) {
                //Controllo se le coordinate per il nuovo nemico non intersechino le coordinate dei barili,
                //non è possibile che intersechino le coordinate dei blocchi fissi poichè sono creati con x+50.
                enemies.add(new Enemy(x, y));
            }
        }

    }


    @Override
    protected void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Banner laterale per punteggi
        g2.drawImage(Resources.getImage("/bomberDog/GUI/images/complementoSfondo225NEW.png"), 921, 0, 225, this.getHeight(), null);
        g2.drawImage(Resources.getImage("/bomberDog/GUI/images/dogHouse.png"), home.x, home.y, home.width, home.height, null);
        g2.setColor(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("Time: " + game.GAME_TIME, 921 + 25, 160 - 80);
        g2.drawString("Stage: " + game.NUM_OF_STAGE, 921 + 25, 250 - 90);
        g2.drawString("Score: " + this.player.getScore(), 921 + 25, 340 - 90);
        g2.drawString("Lifes: ", 921 + 25, 340);
        g2.drawString("Bonus: ", 921 + 25, 430 - 1);

        //Disegno bonus presi
        if (this.player.isSuperRange()) {
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/bonus/fuocoPowerUp.png"), 921 + 130, 429 - 25, 20, 20, null);
        }
        if (this.player.isImmortality()) {
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/bonus/bone.png"), 921 + 130 + 40, 429 - 25, 20, 20, null);
        }

        //Disegno numero di vite
        if (player.getLife() == 1) {
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/life.png"), 1041, 340 - 20, 20, 20, null);
        }
        if (player.getLife() == 2) {
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/life.png"), 1041, 340 - 20, 20, 20, null);
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/life.png"), 1041 + 25, 340 - 20, 20, 20, null);
        }
        if (player.getLife() == 3) {
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/life.png"), 1041, 340 - 20, 20, 20, null);
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/life.png"), 1041 + 25, 340 - 20, 20, 20, null);
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/life.png"), 1041 + 25 + 25, 340 - 20, 20, 20, null);
        }

        //Disegno Sfondo
        g2.drawImage(Resources.getImage("/bomberDog/GUI/images/sfondoGamePanel.png"), 0, 0, this.getWidth() - 242 + 83 - 10 - 56, this.getHeight(), null);

        //Disegno Blocchi Fissi
        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {
                blocks[i][j].drawRectangleBlock(g2);
            }
        }

        //Disegno Bonus
        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                bonuses[i][j].drawBonus(g2);
            }
        }

        //Disegno Blocchi Rimovibili
        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                removableObstacles[i][j].drawRemovableBlock(g2);
            }
        }

        //Disegno Player
        player.drawPlayer(g2);
        //player.drawRectanglePlayer(g2); test collisioni

        //Disegno Nemici
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).drawEnemy(g2);
        }

        //Disegno Bomba  (controllo se è stato premuto il tasto "space", prendo la bomba dal gameManager)
        if (game.getStatusBomb()) {
            game.drawExplosion(g2);
        }

        //Disegno Fuoco (controllo se è attivo lo status "Possibility_Of_Death" in Player,
        //Si setta a true nel timer dopo che è finita l'animazione di esplosione della bomba)
        if (this.player.isPossibilityOfDeath()) {
            game.drawFire(g2); //Terminerà al termine del Timer(timer che controlla anche le collisioni con il fuoco)
        }

        if (game.LOADING == true) { //PANNELLO CARICAMENTO NEXT STAGE SE FINISCONO I NEMICI
            g2.drawImage(Resources.getImage("/bomberDog/GUI/images/levelLoading.png"), 0, 0, this.getWidth(), this.getHeight(), null);
        }


    }

    //Controllo azioni in base al tasto premuto, gestendole dalle functions del GameManager (game)
    private class GamePanelKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();

            switch (key) {
                case (KeyEvent.VK_RIGHT):
                    game.dogMovement(key);
                    //System.out.println("x: " + player.getX() + "  " + "y: " + player.getY());
                    break;
                case (KeyEvent.VK_LEFT):
                    game.dogMovement(key);
                    //System.out.println("x: " + player.getX() + "  " + "y: " + player.getY());
                    break;
                case (KeyEvent.VK_UP):
                    game.dogMovement(key);
                    //System.out.println("x: " + player.getX() + "  " + "y: " + player.getY());
                    break;
                case (KeyEvent.VK_DOWN):
                    game.dogMovement(key);
                    //System.out.println("x: " + player.getX() + "  " + "y: " + player.getY());
                    break;

                case (KeyEvent.VK_SPACE):
                    //funzione piazzamento bomba
                    game.bombPlaced();
                    break;
            }
        }
    }

    private class RepainterThread implements Runnable {
        @Override
        public void run() {
            while (running) {
                time++;  //Regola il timer generale di gioco

                //Controllo LifeTime SuperRange
                if (player.isSuperRange()) {
                    timeFire++;
                    if (timeFire >= Bonus.LIFE_TIME_FIRE_POWER_UP) { // 30 sec
                        player.setSuperRange(false);
                        timeFire = 0;
                    }
                }
                //Controllo LifeTime Immortalità
                if (player.isImmortality()) {
                    timeImm++;
                    if (timeImm >= Bonus.LIFE_TIME_IMMORTALITY_POWER_UP) { // 15 sec
                        player.setImmortality(false);
                        timeImm = 0;
                    }
                }
                //}

                if (time == 5) { //ogni 5*200ms scala un sec da GAME_TIME, alla fine dei nemici gameTime si incrementerà, se scende a zero è GameOver
                    game.GAME_TIME--;
                    time = 0;
                }

                if (game.GAME_TIME <= 0) { //Controllo se il tempo di gioco è terminato
                    System.out.println("Tempo Scaduto!");
                    game.setGAME_OVER(true);
                }

                //MOVIMENTO NEMICI E CHECK COLLISIONI NEMICO-PLAYER
                if (!getEnemies().isEmpty()) { //se l'ArrayList dei nemici contiene almeno un elemento, non è vuoto, passa al controllo dei nemici
                    game.enemiesMovement(getEnemies());
                    if (!player.isImmortality()) { //Controllo collisioni dei nemici con il Player se solo se il bonus Immortlità non è attivo
                        CollisionsManager.checkEnemiesPlayerCollisions(getEnemies(), getPlayer());
                    }
                }

                if (getEnemies().isEmpty()) {
                    game.LOADING = true; //per disegnare pannello caricamento in attesa della creazione del nuovo livello
                }

                if (game.isGAME_OVER()) {
                    running = false; //stop Thread, se è gameOver fermo tale thread
                    SoundManager.musicStop(); //stop per ogni tipo di musica attiva al momento
                    SoundManager.musicClipStart(SoundManager.gameOverMusic()); //avvio musica "gameOver"

                    mainFrame.switchPanels(mainFrame.gamePanel, mainFrame.gameOverPanel);
                }

                repaint();

                if (game.LOADING && player.POSSIBILITY_OF_DEATH == false) { //controllo anche che non ci sia fuoco in fase di disegno
                    game.LOADING = false; //switcho, attivo l'azione dal paint component così da poter disegnare l'img di caricamento
                    System.out.println("Nemici Finiti... Preparo nuovo stage.");

                    nextStage(); //Carico nuovo livello, barili casuali, nemici casuali! (Per disposizione e numero)
                }

                try {
                    Thread.sleep(200);  //regolare valore in base alla "velocità" (Sempre stato 50 o 100)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Block[][] getBlocks() {
        return blocks;
    }

    public RemovableObstacles[][] getRemovableObstacles() {
        return removableObstacles;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    private class GamePanelListner extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (home.contains(e.getPoint())) {
                SoundManager.musicClipStart(SoundManager.clickMusic()); //Starto sound del click

                //Mostro immagine di caricamento momentanea, in attesa che resetGame arrivi a termine.
                getGraphics().drawImage(Resources.getImage("/bomberDog/GUI/images/welcomeScreenLoading.png"), 0, 0, getWidth(), getHeight(), null);
                resetGame();

                mainFrame.switchPanels(mainFrame.gamePanel, mainFrame.welcomePanel);
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void prepareNewGame() {
        this.game = new GameManager(this.player, this);
        Thread repainter = new Thread(new RepainterThread());
        repainter.start(); //creo ed avvio Thread che gestisce disegno ed alcuni controlli del Pannello
        this.running = true;
        game.GAME_TIME = 90; //tempo di gioco
        game.ON_GOING = true;
        this.player.setImmortality(true); //Bonus d'Immortalità è true per i primi 20 secondi di ogni lv
        SoundManager.musicThemeStart(); //Starto musica di sottofondo
    }
//Funzioni di reset del gioco e caricamento nuovo livello
    public void nextStage() { //resetto tutto prima di creare nuovo stage
        SoundManager.musicStop();
        SoundManager.musicClipStart(SoundManager.youWinMusic());
        this.running = true;
        game.GAME_TIME += 45; //era 30
        this.player.setX(80);
        this.player.setY(50);
        this.player.setImgPlayer(Resources.getImage("/bomberDog/GUI/images/personaggio/diFronte1.png"));
        game.NUM_OF_STAGE++; //incremento numero livello

        time = 0;
        timeFire = 0;
        timeImm = 0;
        this.player.setImmortality(true);

        this.blocks = new Block[Block.COLS][Block.ROWS];
        for (int i = 0; i < Block.COLS; i++) {
            int x = (100 * i) + 130;
            for (int j = 0; j < Block.ROWS; j++) {
                if (j == 0) {
                    int y = (50 * j) + 50;
                    this.blocks[i][j] = new Block(x, y, Block.DIM_BLOCK, Block.DIM_BLOCK);
                } else {
                    int y = (100 * j) + 50;
                    this.blocks[i][j] = new Block(x, y, Block.DIM_BLOCK, Block.DIM_BLOCK);
                }
            }
        }

        RemovableObstacles.setCOLS(RemovableObstacles.randInitializer(5, Block.COLS));
        RemovableObstacles.setROWS(RemovableObstacles.randInitializer(5, Block.ROWS));
        this.removableObstacles = new RemovableObstacles[RemovableObstacles.COLS][RemovableObstacles.ROWS];
        this.bonuses = new Bonus[RemovableObstacles.COLS][RemovableObstacles.ROWS];
        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            int x;
            int y;
            if (RemovableObstacles.randInitializer(0, 100) > 50) {
                x = (100 * i) + 130 - 50;
            } else {
                x = (150 * i) + 130 - 50;
            }
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                if (j == 0) {
                    y = (50 * j);
                    this.removableObstacles[i][j] = new RemovableObstacles(x, y);
                    this.bonuses[i][j] = new Bonus(x, y, bonuses[i][j].randInitializer(0, 3), i, j, this);
                } else {
                    if (RemovableObstacles.randInitializer(0, 100) > 50) {
                        y = (100 * j);
                    } else {
                        y = (150 * j);
                    }
                    this.removableObstacles[i][j] = new RemovableObstacles(x, y);
                    this.bonuses[i][j] = new Bonus(x, y, bonuses[i][j].randInitializer(0, 3), i, j, this);
                }

                //Controllo se l'ultimo barile creato è andato a finire su un blocco fisso, o oltre il limite di gioco.
                //in tal caso rimuovo il barile ed il bonus, non rendendoli visibili.
                if (CollisionsManager.checkBlocks(getBlocks(), removableObstacles[i][j].getX(), removableObstacles[i][j].getY())) {
                    removableObstacles[i][j].setDESTROYED(true);
                    bonuses[i][j].setDESTROYED(true);
                }
            }
        }

        enemies = new ArrayList<>();
        GameManager.NUM_OF_ENEMIES = RemovableObstacles.randInitializer(3, 5); //min 3 nemico, max 5 --> Aumentare max e min in base al numero di STAGE, ogni STAGE donare 30 secondi
        for (int i = 0; i < GameManager.NUM_OF_ENEMIES; i++) {
            int x = this.blocks[RemovableObstacles.randInitializer(0, Block.COLS - 1)][RemovableObstacles.randInitializer(0, Block.ROWS - 1)].getX() + 50; //-1 altrimenti andrebbe "out of bounds"

            if (x >= CollisionsManager.LIMIT_DX) {
                //controllo che il nemico non superi il limite destro
                //( controllo solo a DX poichè i nemici sono creati con x+50 così da distanziarsi anche dal player il più possibile
                x -= 100;
            }

            int y = this.blocks[RemovableObstacles.randInitializer(0, Block.COLS - 1)][RemovableObstacles.randInitializer(0, Block.ROWS - 1)].getY();

            if (!CollisionsManager.checkEnemiesCoordinates(getRemovableObstacles(), x, y)) {
                //Controllo se le coordinate per il nuovo nemico non intersechino le coordinate dei barili,
                //non è possibile che intersechino le coordinate dei blocchi fissi poichè sono creati con x+50.
                enemies.add(new Enemy(x, y));
            }
        }

        SoundManager.musicThemeStart();

    }

    public void resetGame() { //RESETTO TUTTO SE VIENE PREMUTO TASTO HOME
        this.player.setScore(0);
        this.player.setX(80);
        this.player.setY(50);
        this.player.setImgPlayer(Resources.getImage("/bomberDog/GUI/images/personaggio/diFronte1.png"));
        this.player.setLife(3);
        this.player.setImmortality(true);
        this.player.setSuperRange(false);

        time = 0;
        timeFire = 0;
        timeImm = 0;

        this.setRunning(false);
        game.NUM_OF_STAGE = 1;
        game.setGAME_OVER(false);

        SoundManager.musicStop();

        this.blocks = new Block[Block.COLS][Block.ROWS];
        for (int i = 0; i < Block.COLS; i++) {
            int x = (100 * i) + 130;
            for (int j = 0; j < Block.ROWS; j++) {
                if (j == 0) {
                    int y = (50 * j) + 50;
                    this.blocks[i][j] = new Block(x, y, Block.DIM_BLOCK, Block.DIM_BLOCK);
                } else {
                    int y = (100 * j) + 50;
                    this.blocks[i][j] = new Block(x, y, Block.DIM_BLOCK, Block.DIM_BLOCK);
                }
            }
        }

        RemovableObstacles.setCOLS(RemovableObstacles.randInitializer(5, Block.COLS));
        RemovableObstacles.setROWS(RemovableObstacles.randInitializer(5, Block.ROWS));
        this.removableObstacles = new RemovableObstacles[RemovableObstacles.COLS][RemovableObstacles.ROWS];
        this.bonuses = new Bonus[RemovableObstacles.COLS][RemovableObstacles.ROWS];
        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            int x;
            int y;
            if (RemovableObstacles.randInitializer(0, 100) > 50) {
                x = (100 * i) + 130 - 50;
            } else {
                x = (150 * i) + 130 - 50;
            }
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                if (j == 0) {
                    y = (50 * j);
                    this.removableObstacles[i][j] = new RemovableObstacles(x, y);
                    this.bonuses[i][j] = new Bonus(x, y, bonuses[i][j].randInitializer(0, 3), i, j, this);
                } else {
                    if (RemovableObstacles.randInitializer(0, 100) > 50) {
                        y = (100 * j);
                    } else {
                        y = (150 * j);
                    }
                    this.removableObstacles[i][j] = new RemovableObstacles(x, y);
                    this.bonuses[i][j] = new Bonus(x, y, bonuses[i][j].randInitializer(0, 3), i, j, this);
                }

                //Controllo se l'ultimo barile creato è andato a finire su un blocco fisso, o oltre il limite di gioco.
                //in tal caso rimuovo il barile ed il bonus, non rendendoli visibili.
                if (CollisionsManager.checkBlocks(getBlocks(), removableObstacles[i][j].getX(), removableObstacles[i][j].getY())) {
                    removableObstacles[i][j].setDESTROYED(true);
                    bonuses[i][j].setDESTROYED(true);
                }
            }

        }

        enemies = new ArrayList<>();
        GameManager.NUM_OF_ENEMIES = RemovableObstacles.randInitializer(3, 5); //min 3 nemico, max 5 --> Aumentare max e min in base al numero di STAGE, ogni STAGE donare 30 secondi
        for (int i = 0; i < GameManager.NUM_OF_ENEMIES; i++) {
            int x = this.blocks[RemovableObstacles.randInitializer(0, Block.COLS - 1)][RemovableObstacles.randInitializer(0, Block.ROWS - 1)].getX() + 50; //-1 altrimenti andrebbe "out of bounds"

            if (x >= CollisionsManager.LIMIT_DX) {
                //controllo che il nemico non superi il limite destro
                //( controllo solo a DX poichè i nemici sono creati con x+50 così da distanziarsi anche dal player il più possibile
                x -= 100;
            }

            int y = this.blocks[RemovableObstacles.randInitializer(0, Block.COLS - 1)][RemovableObstacles.randInitializer(0, Block.ROWS - 1)].getY();

            if (!CollisionsManager.checkEnemiesCoordinates(getRemovableObstacles(), x, y)) {
                //Controllo se le coordinate per il nuovo nemico non intersechino le coordinate dei barili,
                //non è possibile che intersechino le coordinate dei blocchi fissi poichè sono creati con x+50.
                enemies.add(new Enemy(x, y));
            }
        }
    }

    public GameManager getGame() {
        return game;
    }
}
