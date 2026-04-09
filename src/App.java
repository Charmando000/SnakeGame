import javax.swing.*;

public class App {
    public static void main(String[] args) {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Lobby menu = new Lobby(frame);
        frame.add(menu);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(boardWidth, boardHeight);
        frame.setVisible(true);
    }
}
