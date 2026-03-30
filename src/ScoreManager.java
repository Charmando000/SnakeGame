import java.io.*;
import java.util.*;

public class ScoreManager {

    public static void saveScore(String name, int points) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt", true))) {
            bw.write(name + "," + points);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<score> loadScores() {
        List<score> scores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("scores.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                scores.add(new score(parts[0], Integer.parseInt(parts[1])));
            }
        } catch (IOException e) {
            System.out.println("don't have any score yet!");
        }
        scores.sort((a, b) -> b.points - a.points);

        return scores;
    }
}