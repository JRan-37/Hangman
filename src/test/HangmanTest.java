import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class HangmanTest {

    @Test
    void displayWord() {
        Hangman hm = new Hangman();
        assertTrue(hm.displayWord());
    }

    @Test
    void reset() {
        Hangman hm = new Hangman();
        String firstWord = hm.getWordObj().getWord();
        hm.reset();
        assertNotEquals(hm.getWordObj().getWord(), firstWord);
    }
}