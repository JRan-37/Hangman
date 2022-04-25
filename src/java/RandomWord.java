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
        String rString = "";
        for(int i = 0; i < word.length(); i++) {
            rString += guessed[i] ? " " + word.charAt(i) : " _";
        }
        return rString;
    }

    public String getWord() {
        return word;
    }

    public boolean checkGuess(char input) {
        boolean match = false;
        for(int i = 0; i < word.length(); i++) {
            if(input == word.charAt(i)) {
                guessed[i] = true;
                match = true;
            }
        }
        return match;
    }

    public boolean checkFinished() {
        for(boolean bool : guessed) {
            if(!bool)
                return false;
        }
        return true;
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
