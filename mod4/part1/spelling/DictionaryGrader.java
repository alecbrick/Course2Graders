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
        String feedback = "Some tests failed. Please check the following: ";

        try {
            Dictionary dictLL = new DictionaryLL();

            if (!dictLL.addWord("tEst")) {
                incorrect++;
                feedback += "adding a new word to the LL dictionary returned false; ";
            }

            if (!dictLL.isWord("teSt")) {
                incorrect++;
                feedback += "inserted word is not found in LL dictionary - make sure the word is converted to lowercase on both entry and lookup; ";
            }

            dictLL.addWord("second");

            if (dictLL.size() != 2) {
                incorrect++;
                feedback += "incorrect size of LL dictionary after insertion of two words; ";
            }

            tests += 3;

            Dictionary dictBST = new DictionaryBST();

            if (!dictBST.addWord("tEst")) {
                incorrect++;
                feedback += "adding a new word to the BST dictionary returned false; ";
            }

            if (!dictBST.isWord("teSt")) {
                incorrect++;
                feedback += "inserted word is not found in BST dictionary - make sure the word is converted to lowercase on both entry and lookup; ";
            }

            dictBST.addWord("second");

            if (dictBST.size() != 2) {
                incorrect++;
                feedback += "incorrect size of BST dictionary after insertion of two words; ";
            }
            tests += 3;
        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"Runtime error: " + e + "\"}");
            out.close();
            return;
        }

        if (incorrect == 0) {
            feedback = "You passed all the tests!";
        }

        out.println("{\"fractionalScore\": " + ((float)(tests - incorrect) / tests) + ", \"feedback\": \"" + feedback + "\"}");
        out.close();
    }
}
