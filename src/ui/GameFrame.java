package ui;

import model.Difficulty;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private static final String CARD_MENU = "menu";
    private static final String CARD_GAME = "game";
    private static final String CARD_GAME_OVER = "gameOver";

    private final CardLayout cardLayout;
    private final JPanel cards;

    private Difficulty difficulty = Difficulty.MEDIUM;
    private GamePanel currentGame;
    private GameOverPanel currentGameOver;

    public GameFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setResizable(false);
        setTitle("Snake Game");

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        MenuPanel menuPanel = new MenuPanel(
                this::startGame,
                () -> difficulty,
                this::cycleDifficulty,
                () -> System.exit(0)
        );
        cards.add(menuPanel, CARD_MENU);

        add(cards);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void cycleDifficulty() {
        difficulty = difficulty.next();
    }

    private void startGame() {
        if (currentGame != null) {
            cards.remove(currentGame);
        }
        currentGame = new GamePanel(difficulty, this::showGameOver);
        cards.add(currentGame, CARD_GAME);
        cardLayout.show(cards, CARD_GAME);
        currentGame.requestFocusInWindow();
    }

    private void showGameOver(int score) {
        if (currentGameOver != null) {
            cards.remove(currentGameOver);
        }
        currentGameOver = new GameOverPanel(
                score,
                this::startGame,
                this::showMenu,
                () -> System.exit(0)
        );
        cards.add(currentGameOver, CARD_GAME_OVER);
        cardLayout.show(cards, CARD_GAME_OVER);
    }

    private void showMenu() {
        cardLayout.show(cards, CARD_MENU);
    }
}
