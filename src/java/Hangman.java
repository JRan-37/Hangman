import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Hangman {


    private RandomWord hiddenWord;
    private boolean running;
    private int misses;
    private final Scanner input;
    private ArrayList<Character> missedCharacters;
    private ArrayList<Character> matchedCharacters;
    private String path = "src/hangstand.txt";
    private String path2 = "src/hangman.txt";

    private String[] hangmanText;
    private String display;

    private String name;
    private int score;
    private int highscore;

    HighScore hs;

    public Hangman() {

        hiddenWord = new RandomWord();
        input = new Scanner(System.in);
        misses = 0;
        missedCharacters = new ArrayList<>();
        matchedCharacters = new ArrayList<>();
        hangmanText = getHangPersonText();
        display = getHangmanText();

        hs = new HighScore();
        highscore = hs.getHighscore();
        score = 0;
    }

    public void run() {
        boolean retry = true;
        running = true;
        System.out.print("Enter your name: ");
        name = getInput();
        do {
            update();
            if(!running) {
                System.out.println("Play again? (Yes or No)");
                retry = getInput().startsWith("y");
                if (retry) {
                    reset();
                }
            }
        }while(retry);

    }

    private void update() {
        displayGame(misses);
        displayMisses();
        running = displayWord();
        if(running) { guess(promptUser()); }
    }



    private void displayGame(int miss) {
        String[] hangmanPieces = {" ", " ", " ", " ", " ", " ", " "};
        IntStream.range(0, miss)
                .forEachOrdered(i -> hangmanPieces[i] = hangmanText[i]);
        System.out.println(String.format(display,
                hangmanPieces[0], hangmanPieces[3], hangmanPieces[1],
                hangmanPieces[4], hangmanPieces[2], hangmanPieces[5],
                hangmanPieces[6]));
    }

    private String getHangmanText() {
        File file = new File(path);
        BufferedReader br;
        String displayText = "";

        try {
            br = new BufferedReader(new FileReader(file));
            displayText = br.lines()
                    .collect(Collectors.joining());
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return displayText;
    }

    private String[] getHangPersonText() {
        File file = new File(path2);
        BufferedReader br;
        String[] displayText = new String[7];

        try {
            br = new BufferedReader(new FileReader(file));
            displayText = br.lines()
                    .toArray(s -> new String[s]);
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return displayText;
    }

    private void displayMisses() {
        System.out.print("Missed Letters:");
        missedCharacters.stream()
                .forEach(c -> System.out.print(" " + c));
        System.out.println();
    }

    public boolean displayWord() {
        System.out.println(hiddenWord.toString());
        if(hiddenWord.checkFinished()) {
            System.out.println(String.format("Yes! The secret word is \"%s\"! You have won!", hiddenWord.getWord()));
            if(score >= highscore)
                System.out.println("Congratulations! You achieved the high score!");
            hs.saveScore(name, score);
            return false;
        }
        if(misses >= 7) {
            System.out.println(String.format("The secret word is \"%s\"!\n" +
                    "Hanged... You have lost!", hiddenWord.getWord()));
            return false;
        }
        return true;
    }

    private void guess(char input) {
        boolean match = hiddenWord.checkGuess(input);
        if(!match) {
            missedCharacters.add(input);
            Collections.sort(missedCharacters);
            misses++;
            score++;
        }
        else
            matchedCharacters.add(input);
    }


    private char promptUser() {
        char input;
        boolean repeat;

        System.out.println("Guess a letter.");
        input = getInput().charAt(0);
        System.out.println("Input gathered");
        if(matchedCharacters.contains(input) || missedCharacters.contains(input)) {
            System.out.println("You've already guessed that letter. Try again!");
            input = promptUser();
        }

        return input;
    }

    private String getInput() {
        String readIn;
        try{
            readIn = input.nextLine().toLowerCase();
        }
        catch (Exception e) {
            System.out.println("Invalid Input - Exception: " + e);
            readIn = getInput();
        }

        return readIn;

    }

    public void reset() {
        hiddenWord = new RandomWord();
        misses = 0;
        score = 0;
        missedCharacters = new ArrayList<>();
        matchedCharacters = new ArrayList<>();
    }

    public RandomWord getWordObj() {
        return hiddenWord;
    }
}
