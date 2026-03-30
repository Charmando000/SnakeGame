import javax.swing.*;
import java.awt.*;

public class lobby extends JPanel{

    public lobby (JFrame frame){
        setPreferredSize(new Dimension(600, 600));
        setLayout(new GridBagLayout());
        

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        JButton playButton = new JButton("Play");
        JButton scoreButton = new JButton("Scores");
        JButton exitButton = new JButton("Exit");

        styleButton(playButton);
        styleButton(scoreButton);
        styleButton(exitButton);

        gbc.gridy = 0;
        add(playButton, gbc);

        gbc.gridy = 1;
        add(scoreButton, gbc);

        gbc.gridy = 2;
        add(exitButton, gbc);

        add(playButton);
        add(scoreButton);
        add(exitButton);

        //Button play
        playButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            SnakeGame game = new SnakeGame(600, 600, frame);
            frame.add(game);
            frame.revalidate();
            frame.pack();
            game.requestFocus();
        });
        //Score button
        scoreButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            ScorePanel scorePanel = new ScorePanel(frame);
            frame.add(scorePanel);
            frame.revalidate();
            frame.pack();
        });
        //exit button
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
        
    }
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBackground(Color.darkGray);
        button.setForeground(Color.white);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // fondo oscuro
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        // grid estilo snake
        g.setColor(new Color(40, 40, 40));

        int tileSize = 25;

        for (int i = 0; i < getWidth(); i += tileSize) {
            g.drawLine(i, 0, i, getHeight());
        }

        for (int i = 0; i < getHeight(); i += tileSize) {
            g.drawLine(0, i, getWidth(), i);
        }
    }
    public class Score {
        String name;
        int points;

        public Score(String name, int points) {
            this.name = name;
            this.points = points;
        }
    }
    
}
