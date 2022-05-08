import java.io.*;

public class HighScore {

    private String path = "src/highscore.txt";
    private int highscore;

    public HighScore() {
        File file = new File(path);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            highscore = br.lines()
                    .mapToInt(s -> Integer.parseInt(s.split(",")[0]))
                    .reduce(Integer::min)
                    .getAsInt();
            br.close();
        }
        catch(FileNotFoundException e) {
            highscore = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getHighscore() {
        return highscore;
    }

    public void saveScore(String name, int score) {
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter br = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(br);

            pw.println(score + "," + name);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
