package textgen;

import java.util.Random;
import java.util.HashMap;
import java.io.PrintWriter;

public class MarkovTextGeneratorGrader {
    private static final int LENGTH = 500;

    public static String makeJson(double score, String feedback) {
        return "{\"fractionalScore\": " + score + ", \"feedback\": \"" + feedback + "\"}";
    }

    public static void main(String[] args) {
        try {
            PrintWriter f = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            MarkovTextGenerator gen = new MarkovTextGeneratorLoL(new Random());
            
            int incorrect = 0;
            int tests = 0;
            String feedback = "";

            feedback += "** Test 1: Generating text before training...";
            try {
                String s = gen.generateText(20);
                feedback += "PASSED. ";
            } catch (Exception e) {
                incorrect++;
                feedback += "FAILED. Check that your program doesn't crash when the generator tries to generate text without being trained. ";
            }
            tests++;

            gen.train("");
            feedback += "** Test 2: Generating text after training on an empty file...";
            try {
                String s = gen.generateText(20);
                feedback += "PASSED. ";
            } catch (Exception e) {
                incorrect++;
                feedback += "FAILED. Make sure that your program doesn't crash when generating text after being trained with an empty file; ";
            }
            tests++;

            String input = "I love cats. I hate dogs. I I I I I I I I I I I I I I I I love cats. I I I I I I I I I I I I I I I I hate dogs. I I I I I I I I I like books. I love love. I am a text generator. I love cats. I love cats. I love cats. I love love love socks.";
            gen.retrain(input);
            String res = gen.generateText(LENGTH);

            String[] words = res.split("[\\s]+");
            feedback += "** Test #3: Checking requested generator word count...";
            if (res.split("[\\s]+").length != LENGTH) {
                incorrect++;
                feedback += "FAILED. Your generator produced " + res.split("[\\s]+").length + " words when it should have produced " + LENGTH + ". ";
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();

            for (String w : words) {
                if (wordCounts.containsKey(w)) {
                    wordCounts.put(w, wordCounts.get(w) + 1);
                }
                else {
                    wordCounts.put(w, 1);
                }
            }

            feedback += "** Test #4: Testing specific word counts...";
            if (wordCounts.get("I") < LENGTH / 2) {
                incorrect++;
                feedback += "FAILED. The expected word count of certain words isn't high enough. ";
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            boolean found = true;
            feedback += "** Test #5: Checking that every word is used at least once...";
            for (String w : input.split(" ")) {
                if (!wordCounts.containsKey(w)) {
                    incorrect++;
                    found = false;
                    feedback += "FAILED. Your generator doesn't produce every possible word. ";
                    break;
                }
            }
            if (found) {
                feedback += "PASSED. ";
            }
            tests++;

            found = true;
            feedback += "** Test 6: Seeing if last word is always followed by first word...";
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals("socks.")) {
                    if (i + 1 < words.length && !words[i + 1].equals("I")) {
                        incorrect++;
                        found = false;
                        feedback += "FAILED. Make sure that the last word of the input file is always followed by the first. ";
                        break;
                    }
                }
            }
            if (found) {
                feedback += "PASSED. ";
            }
            tests++;

            feedback += "** Test #7: Requesting zero words...";
            if (!gen.generateText(0).equals("")) {
                incorrect++;
                feedback += "FAILED. The text generator shouldn't produce anything when zero words are requested. ";
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            gen.train("");
            res = gen.generateText(LENGTH);
            words = res.split("[\\s]+");
            int i = 0;
            feedback += "** Test #8: Running train() on a generator that has already been trained...";
            for (String w : words) {
                if (w.equals("I")) {
                    i++;
                }
            }

            if (i < LENGTH / 2) {
                incorrect++;
                feedback += "FAILED. Make sure that train() doesn't remove the original word lists. Note that if Test #4 failed, this one will fail too. ";
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            gen.retrain("");
            feedback += "Test #9: Testing retrain()...";
            String s = gen.generateText(20);
            if(s.split("[\\s]+").length != 0 && s.length() != 0) {
                incorrect++;
                feedback += "FAILED. Ensure that retrain() removes the original word lists. ";
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            if (incorrect == 0) {
                feedback += "Congrats! You passed all the tests.";
            }
            else {
                feedback = "Some tests failed. Please check the following: " + feedback;
            }

            f.println(makeJson((double)(tests - incorrect) / tests, feedback));
            f.close();
            return;
        } catch (Exception e) {
            f.println(makeJson(0.0, "Error during runtime: " + e));
        }
    }
}
