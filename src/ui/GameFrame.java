package ui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        int frameWidth = 614;
        int frameHeight = 680;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setBackground(Color.BLACK);
        setResizable(false);
        setTitle("Snake Game");
        setLocationRelativeTo(null);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setVisible(true);
    }
}
