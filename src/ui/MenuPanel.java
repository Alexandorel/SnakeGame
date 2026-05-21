package ui;

import model.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class MenuPanel extends JPanel {

    private final JButton difficultyButton;
    private final Supplier<Difficulty> getDifficulty;

    public MenuPanel(Runnable onPlay,
                     Supplier<Difficulty> getDifficulty,
                     Runnable cycleDifficulty,
                     Runnable onQuit) {
        this.getDifficulty = getDifficulty;

        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(GamePanel.BOARD_PX, GamePanel.PANEL_HEIGHT));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("SNAKE GAME");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.GREEN);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton playButton = createButton("Play");
        playButton.addActionListener(e -> onPlay.run());

        difficultyButton = createButton("");
        updateDifficultyLabel();
        difficultyButton.addActionListener(e -> {
            cycleDifficulty.run();
            updateDifficultyLabel();
        });

        JButton quitButton = createButton("Quit");
        quitButton.addActionListener(e -> onQuit.run());

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(60));
        add(playButton);
        add(Box.createVerticalStrut(20));
        add(difficultyButton);
        add(Box.createVerticalStrut(20));
        add(quitButton);
        add(Box.createVerticalGlue());
    }

    private void updateDifficultyLabel() {
        difficultyButton.setText("Difficulty: " + getDifficulty.get());
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
