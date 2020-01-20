package bomberDog.GUI;

import bomberDog.logic.Player;
import bomberDog.utils.Resources;
import bomberDog.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverPanel extends JPanel {

    private MainFrame mainFrame;
    private Player player;
    private GamePanel gamePanel;

    private Rectangle home;

    public GameOverPanel(MainFrame pMainFrame, Player pPlayer, GamePanel pGamePanel) {
        this.setSize(MainFrame.WIDTH_PANEL, MainFrame.HEIGHT_PANEL);
        this.setLayout(null);

        this.mainFrame = pMainFrame;
        this.gamePanel = pGamePanel;
        this.player = pPlayer;
        this.home = new Rectangle();
        this.home.setBounds(1021, 525, 100, 100);
        this.addMouseListener(new MyMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(Resources.getImage("/bomberDog/GUI/images/gameOverPanel1.png"), 0, 0, MainFrame.WIDTH_FRAME, MainFrame.HEIGHT_FRAME, null);
        g.drawImage(Resources.getImage("/bomberDog/GUI/images/dogHouse.png"), home.x, home.y, home.width, home.height, null);

        g.setColor(Color.WHITE);
        this.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString(String.valueOf(this.player.getScore()), this.getWidth() / 2 + 10, this.getHeight() / 2 + 35);
        g.drawString(String.valueOf(this.gamePanel.getGame().NUM_OF_STAGE), this.getWidth() / 2 + 10, this.getHeight() / 2 + 95);
        if (gamePanel.getGame().GAME_TIME <= 0) {
            g.drawString("Time Elapsed!", this.getWidth() / 2 - 125, this.getHeight() / 2 + 95 + 80);
        }
    }

    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (home.contains(e.getPoint())) {
                SoundManager.musicClipStart(SoundManager.clickMusic());
                gamePanel.resetGame();

                mainFrame.switchPanels(mainFrame.gameOverPanel, mainFrame.welcomePanel);

            }
        }
    }
}
