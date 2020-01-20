package bomberDog.GUI;

import bomberDog.utils.Resources;
import bomberDog.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomePanel extends JPanel {

    private Rectangle playHotArea;
    private Rectangle infoHotArea;
    private MainFrame mainFrame;

    public WelcomePanel(MainFrame pMainFrame) {
        this.setSize(MainFrame.WIDTH_PANEL, MainFrame.HEIGHT_PANEL);
        this.setLayout(null);

        this.mainFrame = pMainFrame;

        playHotArea = new Rectangle();
        playHotArea.setBounds(146, 500, 100, 100);

        infoHotArea = new Rectangle();
        infoHotArea.setBounds(this.getWidth() - 146 - 100, 500, 100, 100);

        this.addMouseListener(new MyMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(Resources.getImage("/bomberDog/GUI/images/welcomePanel/sfondoWelcomePanel.png"), 0, 0, this.getWidth(), this.getHeight(), null);
        g.drawImage(Resources.getImage("/bomberDog/GUI/images/welcomePanel/play.png"), playHotArea.x, playHotArea.y, playHotArea.width, playHotArea.height, null);
        g.drawImage(Resources.getImage("/bomberDog/GUI/images/welcomePanel/info.png"), infoHotArea.x, infoHotArea.y, infoHotArea.width, infoHotArea.height, null);
    }

    private class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent e) {
            if (playHotArea.contains(e.getPoint())) {
                SoundManager.musicClipStart(SoundManager.clickMusic());

                mainFrame.switchPanels(mainFrame.welcomePanel, mainFrame.gamePanel);
            }

            if (infoHotArea.contains(e.getPoint())) {
                SoundManager.musicClipStart(SoundManager.clickMusic());

                mainFrame.switchPanels(mainFrame.welcomePanel, mainFrame.infoPanel);
            }
        }
    }
}
