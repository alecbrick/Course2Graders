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
            MarkovTextGenerator gen = new MarkovTextGeneratorLoL(new Random());
            
            int incorrect = 0;
            int tests = 0;
            String feedback = "";

            try {
                String s = gen.generateText(20);
            } catch (Exception e) {
                incorrect++;
                feedback = "Check that your program doesn't crash when the generator tries to generate text without being trained; ";
            }
            tests++;

            gen.train("");
            try {
                String s = gen.generateText(20);
            } catch (Exception e) {
                incorrect++;
                feedback += "Make sure that your program doesn't crash when generating text after being trained with an empty file; ";
            }
            tests++;

            String input = "I love cats. I hate dogs. I I I I I I I I I I I I I I I I love cats. I I I I I I I I I I I I I I I I hate dogs. I I I I I I I I I like books. I love love. I am a text generator. I love cats. I love cats. I love cats. I love love love socks.";
            gen.retrain(input);
            String res = gen.generateText(LENGTH);

            String[] words = res.split("[\\s]+");
            if (res.split("[\\s]+").length != LENGTH) {
                incorrect++;
                feedback += "Your generator doesn't always produce the right amount of words; ";
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

            if (wordCounts.get("I") < LENGTH / 2) {
                incorrect++;
                feedback += "The expected word count of certain words isn't high enough; ";
            }
            tests++;

            for (String w : input.split(" ")) {
                if (!wordCounts.containsKey(w)) {
                    incorrect++;
                    feedback += "Your generator doesn't produce every possible word; ";
                    break;
                }
            }
            tests++;

            for (int i = 0; i < words.length; i++) {
                if (words[i].equals("socks.")) {
                    if (i + 1 < words.length && !words[i + 1].equals("I")) {
                        incorrect++;
                        feedback += "Make sure that the last word of the input file is always followed by the first; ";
                        break;
                    }
                }
            }
            tests++;

            if (!gen.generateText(0).equals("")) {
                incorrect++;
                feedback += "The text generator shouldn't produce anything when zero words are requested; ";
            }
            tests++;

            gen.train("");
            res = gen.generateText(LENGTH);
            words = res.split("[\\s]+");
            int i = 0;
            for (String w : words) {
                if (w.equals("I")) {
                    i++;
                }
            }

            if (i < LENGTH / 2) {
                incorrect++;
                feedback += "Make sure that train() doesn't remove the original word lists; ";
            }
            tests++;

            gen.retrain("");
            String s = gen.generateText(20);
            if(s.split("[\\s]+").length != 0) {
                incorrect++;
                feedback += "Ensure that retrain() removes the original word lists; ";
            }
            tests++;

            if (incorrect == 0) {
                feedback = "Congrats! You passed all the tests.";
            }

            PrintWriter f = new PrintWriter("output.out");
            f.println(makeJson((double)(tests - incorrect) / tests, feedback));
            f.close();
            return;
        } catch (Exception e) {
            System.out.println(makeJson(0.0, "Error during runtime: " + e));
        }
    }
}
