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

            List<String> d1 = nw.distanceOne("word", true);
            
            feedback += "Testing distanceOne. Test 1: distanceOne list size... ";
            if (d1.size() != 5) {
                feedback += "FAILED. distanceOne returned " + d1.size() + " words when it should have returned 5. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. distanceOne returned 5 words. ";
            }
            tests++;

            String expected[] = {"wore", "ward", "words", "worn", "lord"};

            boolean failed = false;
            feedback += "Test 2: distanceOne words returned... ";
            for (String i : expected) {
                if (!d1.contains(i)) {
                    feedback += "FAILED. distanceOne does not return all expected words. ";
                    incorrect++;
                    failed = true;
                    break;
                }
            }
            if (!failed) {
                feedback += "PASSED. All expected words were returned. ";
            }
            tests++;

            feedback += "Testing distanceOne with isWord set to false. Test 3: distanceOne list size... ";
            d1 = nw.distanceOne("word", false);
            if (d1.size() != 230) {
                feedback += "FAILED. distanceOne with non-words returned " + d1.size() + " words when it should have returned 230. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. distanceOne returned 230 words. ";
            }
            tests++;
            
            d1 = new ArrayList<String>();
            
            feedback += "Testing deletions. Test 4: deletions list size... ";
            nw.deletions("makers", d1, true);
            if (d1.size() != 2) {
                feedback += "FAILED. deletions returned " + d1.size() + " words when it should have returned 2. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. deletions returned 2 words. ";
            }
            tests++;

            feedback += "Test 5: deletions words returned... ";
            if (!(d1.contains("maker") && d1.contains("makes"))) {
                feedback += "FAILED. The list returned by deletions does not contain the expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. The list returned by deletions includes the correct set of words. ";
            }
            tests++;

            d1 = new ArrayList<String>();

            feedback += "Testing insertions. Test 6: insertions list size... ";
            nw.insertions("or", d1, true);
            if (d1.size() != 3) {
                feedback += "FAILED. insertions returned " + d1.size() + " words when it should have returned 3. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. insertions returned 3 words. ";
            }
            tests++;

            feedback += "Test 7: insertions words returned... ";
            if (!(d1.contains("ore") && d1.contains("tor") && d1.contains("oar"))) {
                feedback += "FAILED. The list returned by insertions does not contain the expected set of words. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. The list returned by insertions includes the expected set of words. ";
            }
            tests++;
            
        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"Runtime error: " + e + "\"}");
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
