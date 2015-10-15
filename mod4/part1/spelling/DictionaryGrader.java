package spelling;

import java.io.PrintWriter;

public class DictionaryGrader {
    public static void main(String args[]) {
        PrintWriter out;
        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int incorrect = 0;
        int tests = 0;
        String feedback = "";

        try {
            Dictionary dictLL = new DictionaryLL();

            feedback += "** Test #1: Adding new word to the LL dictionary...";
            if (!dictLL.addWord("tEst")) {
                incorrect++;
                feedback += "FAILED. Adding a new word to the LL dictionary returned false. ";
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += "** Test #2: Adding a second word...";
            dictLL.addWord("second");
            if (dictLL.size() != 2) {
                incorrect++;
                feedback += "FAILED. Incorrect size of LL dictionary after insertion of two words. ";
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += "** Test #3: Looking up word from first test...";
            if (!dictLL.isWord("teSt")) {
                incorrect++;
                feedback += "FAILED. Inserted word is not found in LL dictionary. Make sure the word is converted to lowercase on both entry and lookup. ";
            }
            else {
                feedback += "PASSED. ";
            }

            tests += 3;

            Dictionary dictBST = new DictionaryBST();

            feedback += "** Test #4: Adding a new word to the BST dictionary...";
            if (!dictBST.addWord("tEst")) {
                incorrect++;
                feedback += "FAILED. Adding a new word to the BST dictionary returned false. ";
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += "** Test #5: Adding second word to BST dictionary...";
            dictBST.addWord("second");
            if (dictBST.size() != 2) {
                incorrect++;
                feedback += "FAILED. Incorrect size of BST dictionary after insertion of two words; ";
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += "** Test #6: Retrieving the word from the last test...";
            if (!dictBST.isWord("teSt")) {
                incorrect++;
                feedback += "FAILED. Inserted word is not found in BST dictionary. Make sure the word is converted to lowercase on both entry and lookup; ";
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += "** Test #7: Adding lots of words and retrieving some...";
            dictBST.addWord("seconds");
            dictBST.addWord("seconded");
            dictBST.addWord("secondhand");
            dictBST.addWord("selma");
            if (!(dictBST.isWord("seconded") && dictBST.isWord("selma"))) {
                incorrect++;
                feedback += "FAILED. Could not find 'seconded' and 'selma'. ";
            }
            else {
                feedback += "PASSED. ";
            }
            tests += 4;

            feedback += "** Test #8: Testing non-word in DictLL...";

            if (dictLL.isWord("soup")) {
                incorrect++;
                feedback += "FAILED. 'soup' should not be a word. ";
            }
            else {
                feedback += "PASSED. ";
            }

            feedback += "** Test #9: Testing non-word in DictBST...";
            if (dictBST.isWord("soup")) {
                incorrect++;
                feedback += "FAILED. 'soup' should not be a word. ";
            }
            else {
                feedback += "PASSED. ";
            }
            tests += 2;

        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"Runtime error: " + e + "\"}");
            out.close();
            return;
        }

        if (incorrect == 0) {
            feedback += "Congrats! You passed all the tests!";
        }
        else {
            feedback = "Some tests failed. Please check the following: " + feedback;
        }

        out.println("{\"fractionalScore\": " + ((float)(tests - incorrect) / tests) + ", \"feedback\": \"" + feedback + "\"}");
        out.close();
    }
}
