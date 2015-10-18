package spelling;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

public class NearbyWordsGrader {
    public static String appendFeedback(int num, String desc) {
        return "\\n** Test #" + num + ": " + desc + "...";
    }

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

            List<String> d1 = nw.distanceOne("word", true);
            
            feedback += appendFeedback(1, "distanceOne list size");
            if (d1.size() != 5) {
                feedback += "FAILED. distanceOne returned " + d1.size() + " words when it should have returned 5. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            String expected[] = {"wore", "ward", "words", "worn", "lord"};

            boolean failed = false;
            feedback += appendFeedback(2, "distanceOne words returned");
            for (String i : expected) {
                if (!d1.contains(i)) {
                    feedback += "FAILED. distanceOne does not return all expected words. ";
                    incorrect++;
                    failed = true;
                    break;
                }
            }
            if (!failed) {
                feedback += "PASSED. ";
            }
            tests++;

            feedback += appendFeedback(3, "distanceOne list size (allowing non-words)");
            d1 = nw.distanceOne("word", false);
            if (d1.size() != 230) {
                feedback += "FAILED. distanceOne with non-words returned " + d1.size() + " words when it should have returned 230. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;
            
            d1 = new ArrayList<String>();
            
            feedback += appendFeedback(4, "deletions list size");
            nw.deletions("makers", d1, true);
            if (d1.size() != 2) {
                feedback += "FAILED. deletions returned " + d1.size() + " words when it should have returned 2. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            feedback += appendFeedback(5, "deletions words returned");
            if (!(d1.contains("maker") && d1.contains("makes"))) {
                feedback += "FAILED. The list returned by deletions does not contain the expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            d1 = new ArrayList<String>();

            feedback += appendFeedback(6, "insertions list size");
            nw.insertions("or", d1, true);
            if (d1.size() != 3) {
                feedback += "FAILED. insertions returned " + d1.size() + " words when it should have returned 3. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            feedback += appendFeedback(7, "insertions words returned");
            if (!(d1.contains("ore") && d1.contains("tor") && d1.contains("oar"))) {
                feedback += "FAILED. The list returned by insertions does not contain the expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;
            
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
