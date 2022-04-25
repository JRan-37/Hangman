import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Hangman {


    private RandomWord hiddenWord;
    private boolean running;
    private int misses;
    private Scanner input;
    private ArrayList<Character> missedCharacters;
    private ArrayList<Character> matchedCharacters;

    public Hangman() {

        hiddenWord = new RandomWord();
        input = new Scanner(System.in);
        misses = 0;
        missedCharacters = new ArrayList<Character>();
        matchedCharacters = new ArrayList<Character>();
    }

    public void run() {
        boolean retry = false;
        do {
            startGame();
            System.out.println("Play again? (Yes or No)");
            retry = getInput().startsWith("y") ? true : false;
            if(retry) {
                reset();
            }
        }while(retry);



    }

    private void startGame() {
        running = true;
        while(running) {
            update();
        }
    }
    private void update() {
        displayGame(misses);
        displayMisses();
        running = displayWord();
        if(running) { guess(promptUser()); };
    }



    private void displayGame(int miss) {
        String[] hangmanPieces = {" ", " ", " ", " ", " ", " ", " "};
        for(int i = 0; i < miss; i++) {
            switch(i) {
                case 0 :
                    hangmanPieces[i] = "O";
                    break;
                case 1, 2 :
                    hangmanPieces[i] = "|";
                    break;
                case 3, 5 :
                    hangmanPieces[i] = "/";
                    break;
                case 4, 6 :
                    hangmanPieces[i] = "\\";
                    break;

            }
        }
        System.out.println(String.format("H A N G M A N%n%n" +
                "     +---+%n" +
                "     %s   |%n" +
                "    %s%s%s  |%n" +
                "     %s   |%n" +
                "    %s %s  |%n" +
                "        ====",
                hangmanPieces[0], hangmanPieces[3], hangmanPieces[1],
                hangmanPieces[4], hangmanPieces[2], hangmanPieces[5],
                hangmanPieces[6]));
    }

    private void displayMisses() {
        String displayString = "Missed Letters: ";
        for(char c : missedCharacters) {
            displayString += " " + c;
        }
        System.out.println(displayString);
    }

    public boolean displayWord() {
        System.out.println(hiddenWord.toString());
        if(hiddenWord.checkFinished()) {
            System.out.println(String.format("Yes! The secret word is \"%s\"! You have won!", hiddenWord.getWord()));
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
        }
        else
            matchedCharacters.add(input);
    }


    private char promptUser() {
        char input;
        boolean repeat = false;
        do {
            System.out.println("Guess a letter.");
            input = getInput().charAt(0);
            System.out.println("Input gathered");
            if(matchedCharacters.contains(input) || missedCharacters.contains(input)) {
                System.out.println("You've already guessed that letter. Try again!");
                repeat = true;
            }
            else
                repeat = false;
        }while(repeat);
        return input;
    }

    private String getInput() {
        String readIn;
        try{
            readIn = input.nextLine().toLowerCase();
        }
        catch (Exception e) {
            System.out.println("Invalid Input - Exception: " + e.toString());
            readIn = getInput();
        }

        return readIn;

    }

    public void reset() {
        hiddenWord = new RandomWord();
        misses = 0;
        missedCharacters = new ArrayList<Character>();
        matchedCharacters = new ArrayList<Character>();
    }

    public RandomWord getWordObj() {
        return hiddenWord;
    }
}
