import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RandomWordTest {

    @Test
    void testToString() {
        RandomWord rw = new RandomWord();
        assertEquals(rw.getWord().length(), rw.toString().length() / 2);
    }

    @Test
    void checkGuess() {
        RandomWord rw = new RandomWord();
        char testInput = 'a';
        System.out.println(rw.getWord());
        boolean found = false;
        for(char c : rw.getWord().toCharArray()) {
            found = c == testInput ? true : found;
        }
        assertEquals(found, rw.checkGuess(testInput));

    }

    @Test
    void checkFinished() {
        RandomWord rw = new RandomWord();
        assertFalse(rw.checkFinished());
    }
}