package bomberDog.GUI;

import bomberDog.utils.Resources;

import javax.swing.*;

public class MainFrame extends JFrame {

    //Dim Frame
    public static final int WIDTH_FRAME = 1152;
    public static final int HEIGHT_FRAME = 708;
    //Dim Pannelli
    public static final int WIDTH_PANEL = 1146;
    public static final int HEIGHT_PANEL = 679;

    GamePanel gamePanel;
    WelcomePanel welcomePanel;
    InfoPanel infoPanel;
    BonusPanel bonusPanel;
    GameOverPanel gameOverPanel;

    public MainFrame() {
        this.setSize(WIDTH_FRAME, HEIGHT_FRAME);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Resources.getImage("/bomberDog/GUI/images/cocoprova.png"));
        this.setTitle("BomberDog");
        this.setLayout(null);

        welcomePanel = new WelcomePanel(this);
        this.getContentPane().add(welcomePanel);
        welcomePanel.setVisible(true);
        welcomePanel.setFocusable(true);
        welcomePanel.requestFocus();

        gamePanel = new GamePanel(this);
        this.getContentPane().add(gamePanel);
        gamePanel.setVisible(false);

        infoPanel = new InfoPanel(this);
        this.getContentPane().add(infoPanel);
        infoPanel.setVisible(false);

        bonusPanel = new BonusPanel(this);
        this.getContentPane().add(bonusPanel);
        bonusPanel.setVisible(false);

        gameOverPanel = new GameOverPanel(this, gamePanel.getPlayer(), gamePanel);
        this.getContentPane().add(gameOverPanel);
        gameOverPanel.setVisible(false);
    }

    public void switchPanels(JPanel panelToDisable, JPanel panelToSetVisible) {
        panelToDisable.setVisible(false);
        panelToDisable.setFocusable(false);

        panelToSetVisible.setVisible(true);
        panelToSetVisible.setFocusable(true);
        panelToSetVisible.requestFocus();

        if (panelToSetVisible == gamePanel) {
            gamePanel.prepareNewGame();
        }
    }

}
