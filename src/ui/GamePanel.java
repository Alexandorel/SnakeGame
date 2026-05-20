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

        snake = new Snake();
        snake.adaugaSegment(TILE_SIZE, TILE_SIZE);

        int randomFoodX = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
        int randomFoodY = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
        food = new Food(randomFoodX, randomFoodY);

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
        for (int i = 0; i < snake.corp.size(); i++) {
            g.fillRect(snake.corp.get(i).x, snake.corp.get(i).y, snake.size, snake.size);
        }

        //Desenarea mancarii
        g.setColor(Color.YELLOW);
        g.fillRect(food.x, food.y, food.size, food.size);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //In aceste case uri am interzis sarpelui sa se intoarca in directia opusa
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
        if (gameLoop.isRunning()) {
            Point previousHead = new Point(snake.corp.get(0));

            // Actualizează poziția capului în funcție de direcție
            switch (direction) {
                case UP -> snake.corp.get(0).y -= TILE_SIZE;
                case DOWN -> snake.corp.get(0).y += TILE_SIZE;
                case LEFT -> snake.corp.get(0).x -= TILE_SIZE;
                case RIGHT -> snake.corp.get(0).x += TILE_SIZE;
            }

            // Actualizează pozițiile segmentelor următoare
            for (int i = 1; i < snake.corp.size(); i++) {
                Point temp = new Point(snake.corp.get(i));
                snake.corp.set(i, previousHead);
                previousHead = temp;
            }

            //Verificam coliziunea cu mancarea
            if (snake.corp.get(0).x == food.x && snake.corp.get(0).y == food.y) {
                int randomFoodX = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
                int randomFoodY = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
                for (int i = 1; i < snake.corp.size(); i++) {
                    if (snake.corp.get(i).x == randomFoodX && snake.corp.get(i).y == randomFoodY) {
                        randomFoodX = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
                        randomFoodY = (int) (Math.random() * BOARD_TILES) * TILE_SIZE;
                    }
                }
                food.x = randomFoodX;
                food.y = randomFoodY;

                growSnake();

                score += POINTS_PER_FOOD;
                scoreLabel.setText("Score: " + score);
            }

            wrapAroundWalls();

            //Verificare coliziune cu coada
            if (snakeHitItself()) {
                gameOver();
            }
        }
        repaint();
    }

    private void wrapAroundWalls() {
        for (int i = 0; i < snake.corp.size(); i++) {
            if (snake.corp.get(i).x < 0) snake.corp.get(i).x = MAX_TILE_POS;
            if (snake.corp.get(i).x > MAX_TILE_POS) snake.corp.get(i).x = 0;
            if (snake.corp.get(i).y < 0) snake.corp.get(i).y = MAX_TILE_POS;
            if (snake.corp.get(i).y > MAX_TILE_POS) snake.corp.get(i).y = 0;
        }
    }

    private boolean snakeHitItself() {
        Point head = snake.corp.get(0);
        for (int i = 1; i < snake.corp.size(); i++) {
            if (head.x == snake.corp.get(i).x && head.y == snake.corp.get(i).y) {
                return true;
            }
        }
        return false;
    }

    private void gameOver() {
        gameLoop.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
        System.exit(0);
    }
}
