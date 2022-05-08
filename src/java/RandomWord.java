import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class RandomWord {

    private String path = "src/dictionary.json";
    private Set<String> dictionary;
    private String word;
    private boolean[] guessed;

    public RandomWord() {
        dictionary = readJSONData();
        word = getRandomWord();
        guessed = new boolean[word.length()];
        Arrays.fill(guessed, false);
    }

    @Override
    public String toString() {
        String[] stringArray = new String[word.length()];
        IntStream.range(0, word.length())
                .forEachOrdered(i -> stringArray[i] = guessed[i] ? " " + word.charAt(i) : " _");
        String rString = Arrays.toString(stringArray).replace(",", "");
        return rString.substring(1, rString.length() - 1);
    }

    public String getWord() {
        return word;
    }

    public boolean checkGuess(char input) {
        boolean[] oldGuessed = guessed.clone();
        IntStream.range(0, word.length())
                .forEachOrdered(i -> guessed[i] = word.charAt(i) == input ? true : guessed[i]);
        return !Arrays.equals(oldGuessed, guessed);
    }

    public boolean checkFinished() {
        String[] stringArray = new String[guessed.length];
        IntStream.range(0, guessed.length)
                .forEachOrdered(i -> stringArray[i] = guessed[i] ? "t" : "f");
        return !Arrays.toString(stringArray).contains("f");
    }

    private String getRandomWord() {
        return dictionary.stream().skip(ThreadLocalRandom.current().nextInt(dictionary.size())).findAny().get();
    }
    private Set<String> readJSONData() {
        JSONObject jObj;
        File file = new File(path);
        JSONParser parser = new JSONParser();
        try {
            jObj = (JSONObject) parser.parse(new FileReader(file));
        }
        catch (IOException | ParseException e){
            jObj = null;
        }

        return jObj.keySet();
    }
}
