import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScorePanel extends JPanel {

    public ScorePanel(JFrame frame) {
        setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 18));

        List<score> scores = ScoreManager.loadScores();

        StringBuilder sb = new StringBuilder();
        sb.append("🏆 RANKING 🏆\n\n");

        for (score s : scores) {
            sb.append(s.name + " - " + s.points + "\n");
        }

        textArea.setText(sb.toString());

        JButton backButton = new JButton("Volver");

        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new lobby(frame));
            frame.revalidate();
            frame.pack();
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}