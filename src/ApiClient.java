import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ApiClient {
    public static void sendScore(String name, int points){
        try {
            URL url = new URL("https://java-spring-production-6df1.up.railway.app/scores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"name\": \"%s\", \"points\": %d}",
                name, points
            );

            OutputStream os = conn.getOutputStream();
            os.write(jsonInput.getBytes());
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Response Code :: " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getScores() {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL("https://java-spring-production-6df1.up.railway.app/scores");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                return "Error: " + responseCode;
            }

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading scores";
        }

        return result.toString();
    }
}
