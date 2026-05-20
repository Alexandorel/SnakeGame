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

    private final int step = 40;
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
        scoreLabel.setBounds(10, 605, 200, 30);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel);

        snake = new Snake();
        snake.adaugaSegment(40, 40);

        int randomFoodX = (int) (Math.random() * 15) * 40;
        int randomFoodY = (int) (Math.random() * 15) * 40;
        food = new Food(randomFoodX, randomFoodY);

        gameLoop = new Timer(300, this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 600, 650);

        //Desenarea patratelor
        g.setColor(Color.GREEN);
        for (int i = 40; i < 600; i = i + step) {
            g.drawLine(0, i, 600, i);
            g.drawLine(i, 0, i, 600);
        }
        g.drawLine(0, 600, 600, 600);

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
                case UP -> snake.corp.get(0).y -= step;
                case DOWN -> snake.corp.get(0).y += step;
                case LEFT -> snake.corp.get(0).x -= step;
                case RIGHT -> snake.corp.get(0).x += step;
            }

            // Actualizează pozițiile segmentelor următoare
            for (int i = 1; i < snake.corp.size(); i++) {
                Point temp = new Point(snake.corp.get(i));
                snake.corp.set(i, previousHead);
                previousHead = temp;
            }

            //Verificam coliziunea cu mancarea
            if (snake.corp.get(0).x == food.x && snake.corp.get(0).y == food.y) {
                int randomFoodX = (int) (Math.random() * 15) * 40;
                int randomFoodY = (int) (Math.random() * 15) * 40;
                for (int i = 1; i < snake.corp.size(); i++) {
                    if (snake.corp.get(i).x == randomFoodX && snake.corp.get(i).y == randomFoodY) {
                        randomFoodX = (int) (Math.random() * 15) * 40;
                        randomFoodY = (int) (Math.random() * 15) * 40;
                    }
                }
                food.x = randomFoodX;
                food.y = randomFoodY;

                //Adaugam un segment la sarpe
                Point lastSegment = snake.corp.get(snake.corp.size() - 1);
                int newX = lastSegment.x;
                int newY = lastSegment.y;

                switch (direction) {
                    case UP -> newY += step;
                    case DOWN -> newY -= step;
                    case LEFT -> newX += step;
                    case RIGHT -> newX -= step;
                }
                snake.adaugaSegment(newX, newY);

                score += 10;
                scoreLabel.setText("Score: " + score);
            }

            //Verificare coliziune cu peretele
            for (int i = 0; i < snake.corp.size(); i++) {
                if (snake.corp.get(i).x < 0) snake.corp.get(i).x = 560;
                if (snake.corp.get(i).x > 560) snake.corp.get(i).x = 0;
                if (snake.corp.get(i).y < 0) snake.corp.get(i).y = 560;
                if (snake.corp.get(i).y > 560) snake.corp.get(i).y = 0;
            }

            //Verificare coliziune cu coada
            for (int i = 1; i < snake.corp.size(); i++) {
                if (snake.corp.get(0).x == snake.corp.get(i).x && snake.corp.get(0).y == snake.corp.get(i).y) {
                    gameLoop.stop();
                    JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
                    System.exit(0);
                }
            }
        }
        repaint();
    }
}
