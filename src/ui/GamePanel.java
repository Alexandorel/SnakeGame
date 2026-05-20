package ui;

import model.Food;
import model.Snake;
import movement.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    // board dimensions
    private static final int TILE_SIZE = 40;
    private static final int BOARD_TILES = 15;
    private static final int BOARD_PX = TILE_SIZE * BOARD_TILES;       // 600
    private static final int MAX_TILE_POS = BOARD_PX - TILE_SIZE;      // 560

    // board + score bar height
    private static final int SCORE_BAR_HEIGHT = 50;
    private static final int PANEL_HEIGHT = BOARD_PX + SCORE_BAR_HEIGHT;  // 650

    // Gameplay
    private static final int TICK_MS = 300;
    private static final int POINTS_PER_FOOD = 10;

    private final Timer gameLoop;

    private final Snake snake;
    private final Food food;

    private final JLabel scoreLabel;
    private int score = 0;

    private Direction direction = Direction.DOWN;

    public GamePanel() {
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(10, BOARD_PX + 5, 200, 30);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel);

        snake = new Snake(TILE_SIZE, TILE_SIZE);

        food = new Food(0, 0);
        spawnFood();

        gameLoop = new Timer(TICK_MS, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, BOARD_PX, PANEL_HEIGHT);

        //Desenarea patratelor
        g.setColor(Color.GREEN);
        for (int i = TILE_SIZE; i < BOARD_PX; i += TILE_SIZE) {
            g.drawLine(0, i, BOARD_PX, i);
            g.drawLine(i, 0, i, BOARD_PX);
        }
        g.drawLine(0, BOARD_PX, BOARD_PX, BOARD_PX);

        //Desenarea sarpelui
        g.setColor(Color.RED);
        for (Point segment : snake.getBody()) {
            g.fillRect(segment.x, segment.y, TILE_SIZE, TILE_SIZE);
        }

        //Desenarea mancarii
        g.setColor(Color.YELLOW);
        g.fillRect(food.getX(), food.getY(), TILE_SIZE, TILE_SIZE);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                if (direction != Direction.DOWN) direction = Direction.UP;
            }
            case KeyEvent.VK_DOWN -> {
                if (direction != Direction.UP) direction = Direction.DOWN;
            }
            case KeyEvent.VK_LEFT -> {
                if (direction != Direction.RIGHT) direction = Direction.LEFT;
            }
            case KeyEvent.VK_RIGHT -> {
                if (direction != Direction.LEFT) direction = Direction.RIGHT;
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameLoop.isRunning()) return;

        snake.move(direction, TILE_SIZE);

        Point head = snake.getHead();
        if (food.isAt(head.x, head.y)) {
            handleFoodEaten();
        }

        snake.wrapAround(MAX_TILE_POS);

        if (snake.collidesWithSelf()) {
            gameOver();
        }

        repaint();
    }

    private void handleFoodEaten() {
        spawnFood();
        snake.grow(direction, TILE_SIZE);
        score += POINTS_PER_FOOD;
        scoreLabel.setText("Score: " + score);
    }

    private void spawnFood() {
        int newX = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
        int newY = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
        for (int i = 1; i < snake.size(); i++) {
            if (snake.bodyContains(newX, newY)) {
                newX = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
                newY = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
            }
        }
        food.moveTo(newX, newY);
    }

    private void gameOver() {
        gameLoop.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        System.exit(0);
    }
}
