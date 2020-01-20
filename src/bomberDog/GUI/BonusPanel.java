package bomberDog.GUI;

import bomberDog.utils.Resources;
import bomberDog.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BonusPanel extends JPanel {

    private MainFrame mainFrame;
    private Rectangle home;

    public BonusPanel(MainFrame pMainFrame) {
        this.setSize(MainFrame.WIDTH_PANEL, MainFrame.HEIGHT_PANEL);
        this.setLayout(null);

        this.mainFrame = pMainFrame;
        this.home = new Rectangle();
        this.home.setBounds(70, 50, 100, 100);
        this.addMouseListener(new MyMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(Resources.getImage("/bomberDog/GUI/images/sfondoBonusPanel.png"), 0, 0, getWidth(), getHeight(), null);
        g.drawImage(Resources.getImage("/bomberDog/GUI/images/originalDogHouse.png"), home.x, home.y, home.width, home.height, null);
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            if (home.contains(e.getPoint())) {
                SoundManager.musicClipStart(SoundManager.clickMusic());

                mainFrame.switchPanels(mainFrame.bonusPanel, mainFrame.welcomePanel);
            }
        }
    }
}
