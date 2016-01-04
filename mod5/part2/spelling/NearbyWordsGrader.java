package spelling;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

public class NearbyWordsGrader implements Runnable {
    static PrintWriter out;

    public static String appendFeedback(int num, String desc) {
        return "\\n** Test #" + num + ": " + desc + "...";
    }

    public static void main(String args[]) {
        NearbyWordsGrader grader = new NearbyWordsGrader();
        Thread thread = new Thread(grader);
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

        if (infinite) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"Taking too long to run. Are you using a threshold of 1000?\"}");
            out.close();
        }
    }

    @Override
    public void run() {
        int tests = 0;
        int incorrect = 0;
        String feedback = "";

        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            Dictionary d = new DictionaryHashSet();
            DictionaryLoader.loadDictionary(d, "dict.txt");
            NearbyWords nw = new NearbyWords(d);

            feedback += appendFeedback(1, "2 suggestions");
            List<String> d1 = nw.suggestions("dag", 2);
            if (d1.size() != 2) {
                feedback += "FAILED. " + d1.size() + " suggestions returned instead. ";
                incorrect++;
            }
            else {
                feedback += "PASSED: ";
            }

            feedback += appendFeedback(2, "Checking suggestion correctness");
            if (!(d1.contains("dog") && d1.contains("dogs"))) {
                feedback += "FAILED. Suggestions don't contain expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests += 2;

            feedback += appendFeedback(3, "3 suggestions");
            d1 = nw.suggestions("fare", 3);
            if (d1.size() != 3) {
                feedback += "FAILED. " + d1.size() + " suggestions returned instead. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += appendFeedback(4, "Checking suggestion correctness");
            if (!(d1.contains("fir") && d1.contains("fire") && d1.contains("fired"))) {
                feedback += "FAILED. Suggestions don't contain expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests += 2;

            

            
        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"" + feedback + "\\nRuntime error: " + e + "\"}");
            out.close();
            return;
        }

        if (incorrect == 0) {
            feedback = "Congrats! All tests passed.\\n" + feedback;
        }
        else {
            feedback = "Some tests failed. Please check the following: \\n" + feedback;
        }

        out.println("{\"fractionalScore\": " + (float)(tests - incorrect) / tests + ", \"feedback\": \"" + feedback + "\"}");
        out.close();
    }
}
