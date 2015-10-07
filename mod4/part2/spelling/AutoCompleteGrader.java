package spelling;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class AutoCompleteGrader {
    public static void main(String args[]) {
        int incorrect = 0;
        int tests = 0;
        String feedback = "Some tests failed. Please check the following: ";
        PrintWriter out;
        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            AutoCompleteDictionaryTrie acDict = new AutoCompleteDictionaryTrie();
        
            if (!acDict.addWord("dog")) {
                feedback += "Simple word add failed; ";
                incorrect++;
            }
            tests++;

            acDict.addWord("cat");

            if (!(acDict.isWord("dog") && acDict.isWord("cat"))) {
                feedback += "Words added earlier not in dictionary; ";
                incorrect++;
            }
            tests++;

            if (acDict.size() != 2) {
                feedback += "Incorrect size after adding two words; ";
                incorrect++;
            }
            tests++;

            ArrayList<String> words = new ArrayList<String>();
            words.add("doge");
            words.add("dogg");
            words.add("dawg");
            words.add("dage");
            words.add("doggo");
            words.add("doggie");
            words.add("doggos");
            words.add("doggoes");
            words.add("doggies");
            acDict.insert(words);

            if (acDict.size() != 11) {
                feedback += "Incorrect size after adding list: expected 11, got " + acDict.size() + "; ";
                incorrect++;
            }
            tests++;

            if (!acDict.isWord("doggoes")) {
                feedback += "Can't find word after inserting a list; ";
                incorrect++;
            }
            tests++;

            List<String> auto = acDict.predictCompletions("dog", 3);
            
            if (!(auto.contains("dog") && auto.contains("dogg") && auto.contains("doge"))) {
                feedback += "predictCompletions doesn't return the correct completions - make sure it returns the shortest words; ";
                incorrect++;
            }

            if (auto.size() != 3) {
                feedback += "predictCompletions returns " + auto.size() + " elements when it should return 3; ";
                incorrect++;
            }
            tests += 2;

            auto = acDict.predictCompletions("soup", 6);
            if (auto.size() != 0) {
                feedback += "Autocomplete found words where no words should have been found; ";
                incorrect++;
            }
            tests++;

            auto = acDict.predictCompletions("dogg", 10);
            if (auto.size() != 6) {
                feedback += "predictCompletions returns " + auto.size() + " elements when it should return 6; ";
                incorrect++;
            }
            
            ArrayList<String> expected = new ArrayList<String>();
            expected.add("doggo");
            expected.add("doggie");
            expected.add("doggos");
            expected.add("doggoes");
            expected.add("doggies");
            if(!auto.containsAll(expected)) {
                feedback += "predictCompletions does not return expected words; ";
                incorrect++;
            }
            tests += 2;
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
