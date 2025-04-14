import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.random.RandomGenerator;

public class GamePanel extends JPanel implements KeyListener, ActionListener{

    //SA INCLUD SI FIRE DE EXECUTIE DACA SE POATE

    private int squareSize = 40;
    private int step = 40;
    private int dimPatrat = 40;
    private Timer timer;


    //Snake
    class Snake {
        ArrayList<Point> corp;
        int size = 40;

        public Snake() {
            corp = new ArrayList<>();
        }

        public void adaugaSegment(int x, int y) {
            corp.add(new Point(x, y));  // Adăugăm un punct cu coordonatele (x, y)
        }

        public void afiseazaCoordonate() {
            for (Point coord : corp) {
                System.out.println("x: " + coord.x + ", y: " + coord.y);
            }
        }
    }

    //Game Logic
    Snake snake;



    //Game Loop

    Timer gameLoop ;

    //Food
    class Food{
        int x;
        int y;
        int size = 40;
        Food(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private int randomFoodX = (int)(Math.random() * 16) * 40;
    private int randomFoodY = (int)(Math.random() * 16) * 40;

    Food food;

    //Score
    private JLabel scoreLabel;
    private int score = 0;



    private int direction = DOWN;

    public GamePanel(){
        setLayout(null);
        addKeyListener(this);
        setFocusable(true);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBounds(10, 605, 200, 30);
        scoreLabel.setForeground(Color.WHITE); // Setează culoarea textului
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(scoreLabel);

        snake = new Snake();
        snake.adaugaSegment(40,40);

        food = new Food(randomFoodX, randomFoodY);

        gameLoop = new Timer(300,this);
        gameLoop.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,600,650);
        //Desenarea patratelor
        g.setColor(Color.GREEN);
        for(int i=40;i<600;i=i+dimPatrat){
            g.drawLine(0,i,600,i);
            g.drawLine(i,0,i,600);
        }
        g.drawLine(0,600,600,600);
        //Desenarea sarpelui
        g.setColor(Color.RED);
        for(int i=0;i<snake.corp.size();i++){
            g.fillRect(snake.corp.get(i).x, snake.corp.get(i).y, snake.size, snake.size);
        }

        //Desenarea mancarii
        g.setColor(Color.YELLOW);
        g.fillRect(food.x, food.y, food.size, food.size);
    }

    //Directiile sarpelui
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //In aceste case uri am interzis sarpelui sa se intoarca in directia opusa
            case KeyEvent.VK_UP -> {
                if (direction != DOWN) {
                    direction = UP;
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (direction != UP) {
                    direction = DOWN;
                }
            }
            case KeyEvent.VK_LEFT -> {
                if (direction != RIGHT) {
                    direction = LEFT;
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (direction != LEFT) {
                    direction = RIGHT;
                }
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameLoop.isRunning()){
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

            //Verificam coliziunea
            if(snake.corp.get(0).x == food.x && snake.corp.get(0).y == food.y){
                int randomFoodX = (int)(Math.random() * 15) * 40;
                int randomFoodY = (int)(Math.random() * 15) * 40;
                for (int i = 1; i < snake.corp.size(); i++){
                    if(snake.corp.get(i).x == randomFoodX && snake.corp.get(i).y == randomFoodY){
                        randomFoodX = (int)(Math.random() * 15) * 40;
                        randomFoodY = (int)(Math.random() * 15) * 40;
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
            for(int i=0;i<snake.corp.size();i++){
                if(snake.corp.get(i).x < 0) snake.corp.get(i).x = 560;
                if(snake.corp.get(i).x > 560) snake.corp.get(i).x = 0;
                if(snake.corp.get(i).y < 0) snake.corp.get(i).y = 560;
                if(snake.corp.get(i).y > 560) snake.corp.get(i).y = 0;
            }

            //Verificare coliziune cu coada
            for (int i = 1; i < snake.corp.size(); i++){
                if(snake.corp.get(0).x == snake.corp.get(i).x && snake.corp.get(0).y == snake.corp.get(i).y){
                    gameLoop.stop();
                    JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
                    System.exit(0);
                }
            }

        }
        // Redesenare
        repaint();
    }

}
