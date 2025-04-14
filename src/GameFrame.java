import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame{

    private CardLayout cardLayout;
    private JPanel cardPanel;

    public GameFrame(){
        int frameWidth = 614;
        int frameHeight = 680;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setBackground(Color.BLACK);
        setResizable(false);
        setTitle("Snake Game");
        setLocationRelativeTo(null); // center the window

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }

    public void showGamePanel() {
        cardLayout.show(cardPanel, "Game");
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}
