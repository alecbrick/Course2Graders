package textgen;

import java.util.Random;
import java.util.HashMap;
import java.io.PrintWriter;

public class MarkovTextGeneratorGrader implements Runnable{
    private final int LENGTH = 500;

    PrintWriter f;
    String feedback = "";
    public int correct = 0;
    public static int tests = 9;

    public String makeJson(double score, String feedback) {
        return "{\"fractionalScore\": " + score + ", \"feedback\": \"" + feedback + "\"}";
    }

    public String appendFeedback(int num, String desc) {
        return "\\n** Test #" + num + ": " + desc + "...";
    }

    public PrintWriter getOutfile() {
        return f;
    }

    public String getFeedback() {
        return feedback;
    }

    // For generating text after an empty string (sometimes causes an infinite loop
    private class TextGenerator implements Runnable {
        MarkovTextGenerator gen;
        int num;

        public TextGenerator(MarkovTextGenerator gen, int num) {
            this.gen = gen;
            this.num = num;
        }

        @Override
        public void run() {
            gen.generateText(num);
        }
    }

    @Override
    public void run() {
        this.runTests();
    }

    public static void main(String[] args) {
        MarkovTextGeneratorGrader gr = new MarkovTextGeneratorGrader();
        Thread thread = new Thread(gr);
        thread.start();
        long endTimeMillis = System.currentTimeMillis() + 10000;
        boolean infinite = false;
        while(thread.isAlive()) {
            if (System.currentTimeMillis() > endTimeMillis) {
                thread.stop();
                infinite = true;
                break;
            }
        }
        String feedback = gr.getFeedback();
        PrintWriter out = gr.getOutfile();
        if (infinite) {
            feedback += "\\nYour code created an infinite loop.";
        }
        
        out.println(gr.makeJson((double)gr.correct / tests, feedback));
        out.close();
    }

    public void runTests() {
        try {
            f = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            MarkovTextGenerator gen = new MarkovTextGeneratorLoL(new Random());
            
            Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
                    public void uncaughtException(Thread th, Throwable ex) {
                    }
            };

            feedback += appendFeedback(1, "Generating text before training");

            try {
                String s = gen.generateText(20);
                feedback += "PASSED. ";
                correct++;
            } catch (Exception e) {
                feedback += "FAILED. Check that your program doesn't crash when the generator tries to generate text without being trained. ";
            }

            feedback += appendFeedback(2, "Generating text after training on an empty file");
            gen.train("");

            try {
                gen.generateText(20);
                feedback += "PASSED. ";
                correct++;
            } catch (Exception e) {
                feedback += "FAILED. Make sure that your program doesn't crash when generating text after being trained with an empty file. ";
            }

            feedback += appendFeedback(3, "Training on input, then checking requested generator word count");
            String input = "I love cats. I hate dogs. I I I I I I I I I I I I I I I I love cats. I I I I I I I I I I I I I I I I hate dogs. I I I I I I I I I like books. I love love. I am a text generator. I love cats. I love cats. I love cats. I love love love socks.";
            gen.retrain(input);
            
            String res = gen.generateText(LENGTH);

            String[] words = res.split("[\\s]+");
            if (res.split("[\\s]+").length != LENGTH) {
                feedback += "FAILED. Your generator produced " + res.split("[\\s]+").length + " words when it should have produced " + LENGTH + ". ";
            }
            else {
                feedback += "PASSED. ";
                correct++;
            }

            HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();

            for (String w : words) {
                if (wordCounts.containsKey(w)) {
                    wordCounts.put(w, wordCounts.get(w) + 1);
                }
                else {
                    wordCounts.put(w, 1);
                }
            }

            feedback += appendFeedback(4, "Testing specific word counts");
            if (wordCounts.get("I") == null || wordCounts.get("I") < LENGTH / 2) {
                feedback += "FAILED. The expected word count of certain words isn't high enough. ";
            }
            else {
                feedback += "PASSED. ";
                correct++;
            }

            boolean found = true;
            feedback += appendFeedback(5, "Checking that every word is used at least once");
            for (String w : input.split(" ")) {
                if (!wordCounts.containsKey(w)) {
                    found = false;
                    feedback += "FAILED. Your generator doesn't produce every possible word. ";
                    break;
                }
            }
            if (found) {
                feedback += "PASSED. ";
                correct++;
            }

            found = true;
            feedback += appendFeedback(6, "Seeing if last word is always followed by first word");
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals("socks.")) {
                    if (i + 1 < words.length && !words[i + 1].equals("I")) {
                        found = false;
                        feedback += "FAILED. Make sure that the last word of the input file is always followed by the first. ";
                        break;
                    }
                }
            }
            if (words.length == 0) {
                feedback += "FAILED. Your generator generated no text to test this with. ";
            } else if (found) {
                feedback += "PASSED. ";
                correct++;
            }

            feedback += appendFeedback(7, "Requesting zero words");
            if (!gen.generateText(0).equals("")) {
                feedback += "FAILED. The text generator shouldn't produce anything when zero words are requested. ";
            }
            else {
                feedback += "PASSED. ";
                correct++;
            }

            feedback += appendFeedback(8, "Running train() on a generator that has already been trained");
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
                feedback += "FAILED. Make sure that train() doesn't remove the original word lists. Note that if Test #4 failed, this one will fail too. ";
            }
            else {
                feedback += "PASSED. ";
                correct++;
            }

            feedback += appendFeedback(9, "Testing retrain()");
            gen.retrain("");
            try {
                String s = gen.generateText(20);
                if (s.split("[\\s]+").length != 0 && s.length() != 0) {
                    feedback += "FAILED. Ensure that retrain() removes the original word lists. ";
                }
                else {
                    feedback += "PASSED. ";
                    correct++;
                }
            }
            catch (Exception e) {
                feedback += "FAILED. Make sure that your program doesn't crash when generating text after being trained with an empty file. ";
            }

            if (correct == tests) {
                feedback = "Congrats! You passed all the tests. \\n\\n" + feedback;
            }
            else {
                feedback = "Some tests failed. Please check the tests marked FAILED: \\n\\n" + feedback;
            }

            return;
        } catch (Exception e) {
            feedback += "\\nError during runtime: " + e;
        }
    }
}
