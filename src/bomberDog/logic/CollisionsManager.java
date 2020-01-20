package bomberDog.logic;

import bomberDog.GUI.GamePanel;
import bomberDog.utils.SoundManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class CollisionsManager extends Rectangle {

    // Constants - Limiti Area di Gioco
    public static final int LIMIT_SX = 80;     //x     era 83
    public static final int LIMIT_DX = 773;  //era 773
    public static final int LIMIT_UP = 0;      //y   era 6
    public static final int LIMIT_DOWN = 600; //era 606

    public static boolean checkCollisions(Player player, Block[][] blocks, int dx, int dy) {

        Rectangle recPlayer = player.getBorders();
        Rectangle recCheck = new Rectangle(0, 0, 1, 1); //Rettangolo maggiorato per l'intersezione


        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {

                //Controllo del lato del movimento
                if (dx < 0) //SX
                    recCheck = new Rectangle(recPlayer.x + dx, recPlayer.y, recPlayer.width, recPlayer.height);
                else if (dx > 0)
                    recCheck = new Rectangle(recPlayer.x + dx, recPlayer.y, recPlayer.width + dx, recPlayer.height);
                else if (dy < 0)
                    recCheck = new Rectangle(recPlayer.x, recPlayer.y + dy, recPlayer.width, recPlayer.height);
                else if (dy > 0)
                    recCheck = new Rectangle(recPlayer.x, recPlayer.y + dy, recPlayer.width, recPlayer.height + dy);

                if (recCheck.intersects(blocks[i][j].getBorders())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkBorders(int pKeyPressed, Player player, GamePanel gamePanel) {
        switch (pKeyPressed) {
            case KeyEvent.VK_LEFT:
                if (player.getX() > LIMIT_SX) return true;
                break;
            case KeyEvent.VK_RIGHT:
                if (player.getX() <= LIMIT_DX) return true;
                break;
            case KeyEvent.VK_UP:
                if (player.getY() > LIMIT_UP) return true;
                break;
            case KeyEvent.VK_DOWN:
                if (player.getY() + GameManager.SPEED <= LIMIT_DOWN) return true;
                break;
        }
        return false;
    }


    public static boolean checkPlayerSingleCollision(Player player, int pX, int pY, int pW, int pH) {

        Rectangle recBall = player.getBorders();
        Rectangle recMalus = new Rectangle(pX, pY, pW, pH);

        if (recMalus.intersects(recBall)) {
            return true;   //c'è una collisione
        }
        return false;   //no collision
    }

    //                 **  --- ** CONTROLLI LATERALI FUOCO ** --- **
    public static int checkFireCollisionsBlockSX(Block[][] blocks, Fire pFire, GamePanel gamePanel) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireSX = new Rectangle(recFireCentral.x - 50, recFireCentral.y, recFireCentral.height, recFireCentral.width);
        Rectangle recFireSX1 = new Rectangle(recFireCentral.x - 100, recFireCentral.y, recFireCentral.height, recFireCentral.width); //check limite

        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {

                if (recFireSX.intersects(blocks[i][j].getBorders())) {
                    return 0; //Non disegnare nulla a SX
                }

            }
        }

        if ((recFireSX.getX() < LIMIT_SX)) {
            return 0; //non disegnare
        }

        if (recFireSX1.getX() >= LIMIT_SX) {
            if (gamePanel.getPlayer().isSuperRange() == false) {
                return 1;
            }
            return 2;   //Disegna 2 blocchi di Fuoco
        } else {
            return 1;
        }

    }

    public static int checkFireCollisionsBlockDX(Block[][] blocks, Fire pFire, GamePanel gamePanel) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireDX = new Rectangle(recFireCentral.x + 50, recFireCentral.y, recFireCentral.height, recFireCentral.width);
        Rectangle recFireDX1 = new Rectangle(recFireCentral.x + 100, recFireCentral.y, recFireCentral.height, recFireCentral.width); //check limite

        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {

                if (recFireDX.intersects(blocks[i][j].getBorders())) {
                    return 0; //Non disegnare nulla a DX
                }

            }
        }

        if ((recFireDX.getX() > LIMIT_DX + 7)) {
            return 0; //non disegnare
        }


        if (recFireDX1.getX() <= LIMIT_DX + 7) {
            if (gamePanel.getPlayer().isSuperRange() == false) {
                return 1;
            }
            return 2;   //Disegna 2 blocchi di Fuoco
        } else {
            return 1;
        }
    }


    public static int checkFireCollisionsBlockUP(Block[][] blocks, Fire pFire, GamePanel gamePanel) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireUP = new Rectangle(recFireCentral.x, recFireCentral.y - 50, recFireCentral.height, recFireCentral.width);
        Rectangle recFireUP1 = new Rectangle(recFireCentral.x, recFireCentral.y - 100, recFireCentral.height, recFireCentral.width); //check limite

        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {

                if (recFireUP.intersects(blocks[i][j].getBorders())) {
                    return 0; //Non disegnare nulla UP
                }

            }
        }

        if ((recFireUP.getY() < LIMIT_UP)) {
            return 0; //non disegnare
        }


        if (recFireUP1.getY() >= LIMIT_UP) {
            if (gamePanel.getPlayer().isSuperRange() == false) {
                return 1;
            }
            //Un blocco già deve essere disegnato, ora controllo se c'è collisione con il limite DX, se non c'è verrano disegnati 2 blocchi
            return 2;   //Disegna 2 blocchi di Fuoco
        } else {
            return 1;
        }
    }


    public static int checkFireCollisionsBlockDOWN(Block[][] blocks, Fire pFire, GamePanel gamePanel) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireDOWN = new Rectangle(recFireCentral.x, recFireCentral.y + 50, recFireCentral.height, recFireCentral.width);
        Rectangle recFireDOWN1 = new Rectangle(recFireCentral.x, recFireCentral.y + 100, recFireCentral.height, recFireCentral.width); //check limite

        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {

                if (recFireDOWN.intersects(blocks[i][j].getBorders())) {
                    return 0; //Non disegnare nulla DOWN
                }

            }
        }

        if ((recFireDOWN.getY() > LIMIT_DOWN)) {
            return 0; //non disegnare
        }


        if (recFireDOWN1.getY() <= LIMIT_DOWN) {
            if (gamePanel.getPlayer().isSuperRange() == false) {
                return 1;
            }
            return 2;   //Disegna 2 blocchi di Fuoco
        } else {
            return 1;
        }
    }

    //              --- ** --- ** --- ** --- ** --- ** ---


    //                    **  --- ** CONTROLLI FUOCO CON BLOCCHI RIMOVIBILI ** --- **

    public static boolean checkCollisionsREMOVABLE(Player player, RemovableObstacles[][] blocks, int dx, int dy) {

        Rectangle recPlayer = player.getBorders();
        Rectangle recCheck = new Rectangle(0, 0, 1, 1); //Rettangolo maggiorato per l'intersezione


        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                //Controllo del lato del movimento
                if (dx < 0) //SX
                    recCheck = new Rectangle(recPlayer.x + dx, recPlayer.y, recPlayer.width, recPlayer.height);
                else if (dx > 0)
                    recCheck = new Rectangle(recPlayer.x + dx, recPlayer.y, recPlayer.width + dx, recPlayer.height);
                else if (dy < 0)
                    recCheck = new Rectangle(recPlayer.x, recPlayer.y + dy, recPlayer.width, recPlayer.height);
                else if (dy > 0)
                    recCheck = new Rectangle(recPlayer.x, recPlayer.y + dy, recPlayer.width, recPlayer.height + dy);

                if (recCheck.intersects(blocks[i][j].getBorders())) {
                    if (blocks[i][j].isDESTROYED()) {
                        return false; //se l'immagine di quel barile è "null" (è stato distrutto e quindi può passare)
                    }
                    return true;
                }
            }
        }
        return false;
    }

    //CONTROLLI RIMOZIONE BARILE-NEMICO (OGG RIMOVIBILI)
    public static void checkFireCollisionsSX(RemovableObstacles[][] remBarrel, Fire pFire, Player pPlayer, ArrayList<Enemy> pEnemies, int pLeft) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireSX = new Rectangle(recFireCentral.x - 50, recFireCentral.y, recFireCentral.height, recFireCentral.width);
        Rectangle recFireSX1 = new Rectangle(recFireCentral.x - 100, recFireCentral.y, recFireCentral.height, recFireCentral.width); //check limite

        if (!pPlayer.isSuperRange()) {
            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                    if (recFireSX.intersects(remBarrel[i][j].getBorders())) {
                        remBarrel[i][j].setDESTROYED(true); // setto a true così non saranno disegnate
                    }

                }
            }
        } else {
            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                    if (pLeft > 0) {
                        if (pLeft == 2) {
                            if (recFireSX1.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                            if (recFireSX.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }
                        if (pLeft == 1) {
                            if (recFireSX.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }

                    }
                }
            }


        }
        for (int i = 0; i < pEnemies.size(); i++) {
            if (pEnemies.get(i).getBorders().intersects(recFireSX)) {
                pEnemies.remove(i);
                SoundManager.musicClipStart(SoundManager.deathMusic());
                pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
            }
            if (pPlayer.isSuperRange()) {
                if (pEnemies.get(i).getBorders().intersects(recFireSX1) && pLeft > 0) {
                    pEnemies.remove(i);
                    SoundManager.musicClipStart(SoundManager.deathMusic());
                    pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
                }
            }
        }

    }

    public static void checkFireCollisionsDX(RemovableObstacles[][] remBarrel, Fire pFire, Player pPlayer, ArrayList<Enemy> pEnemies, int pRight) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireDX = new Rectangle(recFireCentral.x + 50, recFireCentral.y, recFireCentral.height, recFireCentral.width);
        Rectangle recFireDX1 = new Rectangle(recFireCentral.x + 100, recFireCentral.y, recFireCentral.height, recFireCentral.width); //check limite

        if (!pPlayer.isSuperRange()) {

            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                    if (recFireDX.intersects(remBarrel[i][j].getBorders())) {
                        remBarrel[i][j].setDESTROYED(true);
                    }
                }
            }
        } else {
            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                    if (pRight > 0) {
                        if (pRight == 2) {
                            if (recFireDX1.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                            if (recFireDX.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }
                        if (pRight == 1) {
                            if (recFireDX.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }

                    }
                }
            }

        }
        for (int i = 0; i < pEnemies.size(); i++) {
            if (pEnemies.get(i).getBorders().intersects(recFireDX)) {
                pEnemies.remove(i);
                SoundManager.musicClipStart(SoundManager.deathMusic());
                pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
            }
            if (pPlayer.isSuperRange()) {
                if (pEnemies.get(i).getBorders().intersects(recFireDX1) && pRight > 0) {
                    pEnemies.remove(i);
                    SoundManager.musicClipStart(SoundManager.deathMusic());
                    pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
                }
            }
        }
    }

    public static void checkFireCollisionsUP(RemovableObstacles[][] remBarrel, Fire pFire, Player pPlayer, ArrayList<Enemy> pEnemies, int pUp) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireUP = new Rectangle(recFireCentral.x, recFireCentral.y - 50, recFireCentral.height, recFireCentral.width);
        Rectangle recFireUP1 = new Rectangle(recFireCentral.x, recFireCentral.y - 100, recFireCentral.height, recFireCentral.width); //check limite

        if (!pPlayer.isSuperRange()) {

            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {

                    if (recFireUP.intersects(remBarrel[i][j].getBorders())) {
                        remBarrel[i][j].setDESTROYED(true);
                    }

                }
            }
        } else {
            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                    if (pUp > 0) {
                        if (pUp == 2) {
                            if (recFireUP1.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                            if (recFireUP.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }
                        if (pUp == 1) {
                            if (recFireUP.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }

                    }
                }
            }

        }
        for (int i = 0; i < pEnemies.size(); i++) {
            if (pEnemies.get(i).getBorders().intersects(recFireUP)) {
                pEnemies.remove(i);
                SoundManager.musicClipStart(SoundManager.deathMusic());
                pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
            }
            if (pPlayer.isSuperRange()) {
                if (pEnemies.get(i).getBorders().intersects(recFireUP1) && pUp > 0) {
                    pEnemies.remove(i);
                    SoundManager.musicClipStart(SoundManager.deathMusic());
                    pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
                }
            }
        }
    }

    public static void checkFireCollisionsDOWN(RemovableObstacles[][] remBarrel, Fire pFire, Player pPlayer, ArrayList<Enemy> pEnemies, int pDown) {

        Rectangle recFireCentral = pFire.getBorders();

        Rectangle recFireDOWN = new Rectangle(recFireCentral.x, recFireCentral.y + 50, recFireCentral.height, recFireCentral.width);
        Rectangle recFireDOWN1 = new Rectangle(recFireCentral.x, recFireCentral.y + 100, recFireCentral.height, recFireCentral.width); //check limite

        if (!pPlayer.isSuperRange()) {
            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                    if (recFireDOWN.intersects(remBarrel[i][j].getBorders())) {
                        remBarrel[i][j].setDESTROYED(true);
                    }

                }
            }
        } else {
            for (int i = 0; i < RemovableObstacles.COLS; i++) {
                for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                    if (pDown > 0) {
                        if (pDown == 2) {
                            if (recFireDOWN1.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                            if (recFireDOWN.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }
                        if (pDown == 1) {
                            if (recFireDOWN.intersects(remBarrel[i][j].getBorders())) {
                                remBarrel[i][j].setDESTROYED(true);
                            }
                        }

                    }
                }
            }

        }
        for (int i = 0; i < pEnemies.size(); i++) {
            if (pEnemies.get(i).getBorders().intersects(recFireDOWN)) {
                pEnemies.remove(i);
                SoundManager.musicClipStart(SoundManager.deathMusic());
                pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
            }
            if (pPlayer.isSuperRange()) {
                if (pEnemies.get(i).getBorders().intersects(recFireDOWN1) && pDown > 0) {
                    pEnemies.remove(i);
                    SoundManager.musicClipStart(SoundManager.deathMusic());
                    pPlayer.setScore(pPlayer.getScore() + Enemy.POINTS);
                }
            }
        }
    }

    public static boolean checkBlocks(Block[][] pBlocks, int pX, int pY) {
        Rectangle recCheck = new Rectangle(pX, pY, Block.DIM_BLOCK, Block.DIM_BLOCK);
        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {
                if (!(recCheck.getX() <= LIMIT_DX)) {
                    return true;
                }
                if (recCheck.intersects(pBlocks[i][j].getBorders())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkEnemiesCoordinates(RemovableObstacles[][] pRemovableObstacles, int pX, int pY) {

        Rectangle recCheck = new Rectangle(pX, pY, Enemy.DIM_ENEMY, Enemy.DIM_ENEMY);
        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                if (recCheck.intersects(pRemovableObstacles[i][j].getBorders())) {
                    return true; //INTERSECA, NON VA BENE
                }
            }
        }
        return false; //NON INTERSECA, OK
    }

    public static boolean checkBordersEnemies(int pDirection, ArrayList<Enemy> pEnemies, int pIndex) {

        switch (pDirection) {
            case Enemy.LEFT:
                if (pEnemies.get(pIndex).getX() > LIMIT_SX) return true;
                break;
            case Enemy.RIGHT:
                if (pEnemies.get(pIndex).getX() <= LIMIT_DX) return true;
                break;
            case Enemy.UP:
                if (pEnemies.get(pIndex).getY() > LIMIT_UP) return true;
                break;
            case Enemy.DOWN:
                if (pEnemies.get(pIndex).getY() + GameManager.SPEED <= LIMIT_DOWN) return true;
                break;
        }
        return false;

    }

    public static boolean checkCollisionsEnemies(ArrayList<Enemy> pEnemies, Block[][] blocks, int dx, int dy, int pIndex) {

        Rectangle recEnemy = pEnemies.get(pIndex).getBorders();
        Rectangle recCheck = new Rectangle(0, 0, 1, 1); //Rettangolo maggiorato per l'intersezione


        for (int i = 0; i < Block.COLS; i++) {
            for (int j = 0; j < Block.ROWS; j++) {

                //Controllo del lato del movimento
                if (dx < 0) //SX
                    recCheck = new Rectangle(recEnemy.x + dx, recEnemy.y, recEnemy.width, recEnemy.height);
                else if (dx > 0)
                    recCheck = new Rectangle(recEnemy.x + dx, recEnemy.y, recEnemy.width + dx, recEnemy.height);
                else if (dy < 0)
                    recCheck = new Rectangle(recEnemy.x, recEnemy.y + dy, recEnemy.width, recEnemy.height);
                else if (dy > 0)
                    recCheck = new Rectangle(recEnemy.x, recEnemy.y + dy, recEnemy.width, recEnemy.height + dy);

                if (recCheck.intersects(blocks[i][j].getBorders())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkCollisionsRemovableEnemies(ArrayList<Enemy> pEnemies, RemovableObstacles[][] pRemovables, int dx, int dy, int pIndex) {

        Rectangle recEnemy = pEnemies.get(pIndex).getBorders();
        Rectangle recCheck = new Rectangle(0, 0, 1, 1); //Rettangolo maggiorato per l'intersezione


        for (int i = 0; i < RemovableObstacles.COLS; i++) {
            for (int j = 0; j < RemovableObstacles.ROWS; j++) {
                //Controllo del lato del movimento
                if (dx < 0) //SX
                    recCheck = new Rectangle(recEnemy.x + dx, recEnemy.y, recEnemy.width, recEnemy.height);
                else if (dx > 0)
                    recCheck = new Rectangle(recEnemy.x + dx, recEnemy.y, recEnemy.width + dx, recEnemy.height);
                else if (dy < 0)
                    recCheck = new Rectangle(recEnemy.x, recEnemy.y + dy, recEnemy.width, recEnemy.height);
                else if (dy > 0)
                    recCheck = new Rectangle(recEnemy.x, recEnemy.y + dy, recEnemy.width, recEnemy.height + dy);

                if (recCheck.intersects(pRemovables[i][j].getBorders())) {
                    if (pRemovables[i][j].isDESTROYED()) {
                        return false; //se l'immagine di quel barile è "null" (è stato distrutto e quindi può passare)
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static void checkEnemiesPlayerCollisions(ArrayList<Enemy> pEnemies, Player pPlayer) {
        for (int i = 0; i < pEnemies.size(); i++) {
            if (pPlayer.getBorders().intersects(pEnemies.get(i).getBorders())) {
                pPlayer.setLife(pPlayer.getLife() - 1);
                SoundManager.musicClipStart(SoundManager.lifeLostMusic());

                if (pPlayer.getLife() <= 0) {
                    GameManager.GAME_OVER = true;
                }
            }
        }
    }

}