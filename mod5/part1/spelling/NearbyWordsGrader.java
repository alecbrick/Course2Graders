package spelling;

import java.util.List;
import java.util.ArrayList;
import java.io.PrintWriter;

public class NearbyWordsGrader {
    public static void main(String args[]) {
        int tests = 0;
        int incorrect = 0;
        String feedback = "Some tests were incorrect. Please check the following: ";
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
            
            if (d1.size() != 5) {
                feedback += "distanceOne returned " + d1.size() + " words when it should have returned 5; ";
                incorrect++;
            }
            tests++;

            String expected[] = {"wore", "ward", "words", "worn", "lord"};

            for (String i : expected) {
                if (!d1.contains(i)) {
                    feedback += "distanceOne does not return all expected words; ";
                    incorrect++;
                    break;
                }
            }
            tests++;

            d1 = nw.distanceOne("word", false);
            if (d1.size() != 230) {
                feedback += "distanceOne with non-words returned " + d1.size() + " words when it should have returned 230; ";
                incorrect++;
            }
            tests++;
            
            d1 = new ArrayList<String>();
            
            nw.deletions("makers", d1, true);
            if (d1.size() != 2) {
                feedback += "deletions returned " + d1.size() + " words when it should have returned 2; ";
                incorrect++;
            }
            tests++;

            if (!(d1.contains("maker") && d1.contains("makes"))) {
                feedback += "The list returned by deletions does not contain the expected set of words; ";
                incorrect++;
            }
            tests++;

            d1 = new ArrayList<String>();

            nw.changeChar("say", d1, true);
            if (d1.size() != 3) {
                feedback += "changeChar returned " + d1.size() + " words when it should have returned 3; ";
                incorrect++;
            }
            tests++;

            if (!(d1.contains("sad") && d1.contains("day") && d1.contains("way"))) {
                feedback += "The list returned by changeChar did not contain the expected set of words; ";
                incorrect++;
            }
            tests++;

            d1 = new ArrayList<String>();

            nw.insertions("or", d1, true);
            if (d1.size() != 3) {
                feedback += "insertions returned " + d1.size() + " words when it should have returned 3; ";
                incorrect++;
            }
            tests++;

            if (!(d1.contains("ore") && d1.contains("tor") && d1.contains("oar"))) {
                feedback += "The list returned by insertions does not contain the expected set of words; ";
                incorrect++;
            }
            tests++;
            
        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"Runtime error: " + e + "\"}");
            return;
        }

        if (incorrect == 0) {
            feedback = "Congrats! All tests passed.";
        }

        out.println("{\"fractionalScore\": " + (float)(tests - incorrect) / tests + ", \"feedback\": \"" + feedback + "\"}");
        out.close();
    }
}
