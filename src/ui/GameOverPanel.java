package ui;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    public GameOverPanel(int score,
                         Runnable onPlayAgain,
                         Runnable onMainMenu,
                         Runnable onQuit) {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GamePanel.BOARD_PX, GamePanel.PANEL_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("GAME OVER");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton playAgainButton = createButton("Play Again");
        playAgainButton.addActionListener(e -> onPlayAgain.run());

        JButton mainMenuButton = createButton("Main Menu");
        mainMenuButton.addActionListener(e -> onMainMenu.run());

        JButton quitButton = createButton("Quit");
        quitButton.addActionListener(e -> onQuit.run());

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(30));
        add(scoreLabel);
        add(Box.createVerticalStrut(50));
        add(playAgainButton);
        add(Box.createVerticalStrut(20));
        add(mainMenuButton);
        add(Box.createVerticalStrut(20));
        add(quitButton);
        add(Box.createVerticalGlue());
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setForeground(Color.WHITE);
        b.setBackground(Color.DARK_GRAY);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(250, 50));
        return b;
    }
}
