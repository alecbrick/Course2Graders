package spelling;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

public class NearbyWordsGrader {
    public static void main(String args[]) {
        int tests = 0;
        int incorrect = 0;
        String feedback = "";
        PrintWriter out;

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

            feedback += "** Test 1: 2 suggestions... ";
            List<String> d1 = nw.suggestions("dag", 2);
            if (d1.size() != 2) {
                feedback += "FAILED. " + d1.size() + " suggestions returned instead. ";
                incorrect++;
            }
            else {
                feedback += "PASSED: ";
            }

            feedback += "** Test 2: Checking suggestion correctness... ";
            if (!(d1.contains("dog") && d1.contains("dogs"))) {
                feedback += "FAILED. Suggestions don't contain expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests += 2;

            feedback += "** Test 3: 3 suggestions... ";
            d1 = nw.suggestions("fare", 3);
            if (d1.size() != 3) {
                feedback += "FAILED. " + d1.size() + " suggestions returned instead. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += "** Test 4: Checking suggestion correctness... ";
            if (!(d1.contains("fir") && d1.contains("fire") && d1.contains("fired"))) {
                feedback += "FAILED. Suggestions don't contain expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests += 2;

            

            
        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"" + feedback + "Runtime error: " + e + "\"}");
            return;
        }

        if (incorrect == 0) {
            feedback = feedback + "Congrats! All tests passed.";
        }
        else {
            feedback = "Some tests failed. Please check the following: " + feedback;
        }

        out.println("{\"fractionalScore\": " + (float)(tests - incorrect) / tests + ", \"feedback\": \"" + feedback + "\"}");
        out.close();
    }
}
