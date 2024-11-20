import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for HashMarkov
 *
 * @author Emily Du
 * @author Havish Malladi
 * @author Owen Astrachan 2024
 */
public class MarkovModelTest {

    private AbstractMarkovModel getModel(int order) {
        // Choose which implementation to test here

        return new BaseMarkovModel(order);
        //return new HashMarkovModel(order);
    }

    /**
     * This test checks your return value of getFollows() contains
     * all characters that follow the k-gram.
     */
    @Test
    public void testGetFollows() {

        String testString = "e f g e f g f e f g g e f g h e f g x e f g y e f g z y efg x h g f e";  // Note it sterts with "efg" insteeh of "efge"
        String[][] inStrings = {
                {"e", "f", "g"},
                {"f", "g"},
                {"g", "g"},
                {"f", "g", "h", "e"},
                {"y"},
                {"h", "g", "f"}};
        String[][] out = {
                {"e", "f", "g", "h", "x", "y", "z"},
                {"e", "f", "g", "h", "x", "y", "z"},
                {"e"},
                {"f"},
                {"e", "efg"},
                {"e"}};


        WordGram[] in = new WordGram[inStrings.length];
        for (int i = 0; i < inStrings.length; i++) {
            in[i] = new WordGram(inStrings[i], 0, inStrings[i].length);
        }

        for (int i = 0; i < in.length; i++) {
            int k = in[i].length();
            AbstractMarkovModel markov = getModel(k);
            markov.setTraining(testString);
            List<String> expected = Arrays.asList(out[i]);
            List<String> actual = markov.getFollows(in[i]);
            checkListUnordered(expected, actual);
        }
    }

    /**
     * This test checks your return value of getFollows() contains all characters that follow the k-gram,
     * with the corresponding frequencies for duplicates.
     */
    @Test
    public void testGetFollowsRepeats() {
        String testString = "w x y w w x y x w x y x w x y y w x y y w x y y w x y z w x y z w x y z w x y z y y x w";
        String[][] inStrings = {
                {"w", "x", "y"},
                {"x", "y"},
                {"y", "y"},
                {"z"},
                {"w", "x", "y", "y"}};
        String[][] out = {
                {"w", "x", "x", "y", "y", "y", "z", "z", "z", "z"},
                {"w", "x", "x", "y", "y", "y", "z", "z", "z", "z"},
                {"w", "w", "w", "x"},
                {"w", "w", "w", "y"},
                {"w", "w", "w"}};

        WordGram[] in = new WordGram[inStrings.length];
        for (int i = 0; i < inStrings.length; i++) {
            in[i] = new WordGram(inStrings[i], 0, inStrings[i].length);
        }

        for (int i = 0; i < in.length; i++) {
            int k = in[i].length();
            AbstractMarkovModel markov = getModel(k);
            markov.setTraining(testString);
            List<String> expected = Arrays.asList(out[i]);
            List<String> actual = markov.getFollows(in[i]);
            checkListUnordered(expected, actual);
        }
    }


    /**
     * @param expected elements of correct result
     * @param actual elements of generated result from student code
     * Helper method to verify tested elements in list
     */
    private static void checkListUnordered(List<String> expected, List<String> actual) {
        HashMap<String, Integer> expectedFreq = new HashMap<>();
        HashMap<String, Integer> actualFreq = new HashMap<>();
        for (String s : expected) {
            if (!expectedFreq.containsKey(s))
                expectedFreq.put(s, 0);
            expectedFreq.put(s, expectedFreq.get(s) + 1);
        }
        for (String s : actual) {
            if (!actualFreq.containsKey(s))
                actualFreq.put(s, 0);
            actualFreq.put(s, actualFreq.get(s) + 1);
        }

        for (String s : expectedFreq.keySet()) {
            if (!actualFreq.containsKey(s)) {
                fail("\nExpected element " + s + " does not exist");
            }
            assertEquals(expectedFreq.get(s), actualFreq.get(s));
        }
        for (String s : actualFreq.keySet()) {
            if (!expectedFreq.containsKey(s)) {
                fail("\nElement " + s + " in returned list should not exist");
            }
        }
    }

}
