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

    enum GameState {
        MENU,
        PLAYING,
        GAME_OVER
    }

    GameState state = GameState.MENU;

    //game logic
    Timer gameLoop;
    int velocityX = 0;
    int velocityY = 0;
    boolean gameOver = false;
    boolean scoreSaved = false;


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
        if (state == GameState.MENU) {
            drawMenu(g);
            return;
        }

        //food
        g.setColor(Color.red);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);

        // snake head
        g.setColor(Color.green);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.white);
        g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);

        if (state == GameState.GAME_OVER) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", boardWidth / 2 - 130, boardHeight / 2 - 20);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to Restart", boardWidth / 2 - 120, boardHeight / 2 + 20);
        }
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, boardWidth, boardHeight);

        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("SNAKE GAME", 130, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press ENTER to Start", 150, 260);
        g.drawString("Press ESC to go back to lobby", 120, 300);
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

            soundPlayer.playSound("sounds/eat.wav");
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
                soundPlayer.playSound("sounds/gameover.wav");
                gameOver = true;
            }
        }
        //collision with walls
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth
            || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight) {
            soundPlayer.playSound("sounds/gameover.wav");
            gameOver = true;
        }
    }

    public void restartGame() {
        resetGame();
        state = GameState.PLAYING;
        soundPlayer.playMusic("sounds/music.wav");
        gameLoop.start();
    }

    public void resetGame() {
        snakeHead = new Tile(5, 5);
        snakeBody.clear();

        velocityX = 1;
        velocityY = 0;

        placefood();

        gameOver = false;
        scoreSaved = false;
    }

    public void startGame() {
        resetGame();
        state = GameState.PLAYING;
        soundPlayer.playMusic("sounds/music.wav");
        if (!gameLoop.isRunning()) {
            gameLoop.start();
        }
        requestFocusInWindow();
    }

    //game loop
    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == GameState.PLAYING) {
            move();
            repaint();

            if (gameOver) {
                state = GameState.GAME_OVER;
                gameLoop.stop();
                promptSaveScore();
            }
        } else {
            repaint();
        }
    }

    private void promptSaveScore() {
        if (!scoreSaved) {
            String name = JOptionPane.showInputDialog(this, "Ingresa tu nombre:");
            if (name != null && !name.isEmpty()) {
                ScoreManager.saveScore(name, snakeBody.size());
            }
            scoreSaved = true;
        }
    }

    private void returntolobby() {

        // detener el juego
        gameLoop.stop();
        soundPlayer.stopMusic();

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
            return;
        }

        if (state == GameState.MENU && e.getKeyCode() == KeyEvent.VK_ENTER) {
            startGame();
            return;
        }

        if (state == GameState.GAME_OVER && e.getKeyCode() == KeyEvent.VK_R) {
            restartGame();
            return;
        }

        if (state != GameState.PLAYING) {
            return;
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
