import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    public ScorePanel(JFrame frame) {
        setPreferredSize(new Dimension(600, 600));
        setLayout(new BorderLayout());
        setBackground(Color.black);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 18));

        String json = ApiClient.getScores();

        String[] lines = json
        .replace("[", "")
        .replace("]", "")
        .replace("{", "")
        .replace("}", "")
        .replace("\"", "")
        .split(",");

        StringBuilder sb = new StringBuilder();
        sb.append("🏆 TOP SCORES 🏆\n\n");

        for (int i = 0; i < lines.length; i += 2) {
            String name = lines[i].replace("name:", "");
            String points = lines[i + 1].replace("points:", "");

            sb.append((i / 2 + 1)).append(". ")
            .append(name)
            .append(" - ")
            .append(points)
            .append("\n");
        }

        textArea.setText(sb.toString());

        JButton backButton = new JButton("Back to Menu");

        backButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(new Lobby(frame));
            frame.revalidate();
            frame.pack();
        });

        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);
    }
}