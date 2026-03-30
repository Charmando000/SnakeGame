import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    JFrame frame;
    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;


    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    
    //Food
    Tile food;
    Random random;

    //game logic
    Timer gameLoop;
    int velocityX = 0;
    int velocityY = 0;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight, JFrame frame) {
        this.frame = frame;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placefood();

        velocityX = 0; // Start moving right
        velocityY = 0; // Start moving downwards

        gameLoop = new Timer(100, this);
        gameLoop.start();
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //grid
        //for (int i = 0; i < boardWidth / tileSize; i++) {
            //(x1, y1, x2, y2)
            //g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            //g.drawLine(0, i* tileSize, boardWidth, i * tileSize);


        //}

        //food
        g.setColor(Color.red);
        //g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);


        // snake head
        g.setColor(Color.green);
        //g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // snake body
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        //Score
        g.setFont(new Font("Arial", Font.BOLD, 16));
        if (gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()) + " Press 'R' to restart", tileSize - 16, tileSize);
            String name = JOptionPane.showInputDialog(this, "Ingresa tu nombre:");
            if (name != null && !name.isEmpty()) {
            ScoreManager.saveScore(name, snakeBody.size());
        }

        }else{
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        
    }

    public void placefood(){
        food.x = random.nextInt(boardWidth / tileSize);  //600 / 25 = 24
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2){
        return tile1.x == tile2.x && tile1.y == tile2.y;

    }

    public void move(){
        //eat food
        if(collision(snakeHead, food)){
            snakeBody.add(new Tile(food.x, food.y));
            placefood();
        }

        //Snake body
        for (int i = snakeBody.size()-1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if (i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }else{
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        //Snake Head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);

            //collision with body
            if (collision(snakeHead, snakePart)){
                gameOver = true;
            }
        }
        //collision with walls
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth
            || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight) {
            gameOver = true;
        }
    }

    public void restartGame(){
        snakeHead = new Tile(5, 5);
        snakeBody.clear();

        velocityX = 1;
        velocityY = 0;

        placefood();

        gameOver = false;

        gameLoop.start();
    }

    //game loop
    @Override
    public void actionPerformed(ActionEvent e) {

        move();
        repaint();

        if(gameOver){
            gameLoop.stop();
        }
    }

    private void returntolobby() {

    // detener el juego
    gameLoop.stop();

    // guardar puntaje si no es 0
    if (snakeBody.size() > 0 && !gameOver) {
            String name = JOptionPane.showInputDialog(this, "Keep your Name - put your name:");
            if (name != null && !name.isEmpty()) {
                ScoreManager.saveScore(name, snakeBody.size());
            }
        }

        frame.getContentPane().removeAll();
        frame.add(new lobby(frame));
        frame.revalidate();
        frame.pack();
    }

    //controls with arrow keys
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            returntolobby();
        }
        if(gameOver && e.getKeyCode() == KeyEvent.VK_R){
            restartGame();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
