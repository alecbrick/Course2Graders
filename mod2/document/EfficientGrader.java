package document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class EfficientGrader {
    public static final int TESTS = 15;

    public static String appendFeedback(int num, String desc) {
        return "\\n** Test #" + num + ": " + desc + "...";
    }

    public static void main(String args[]) {
        PrintWriter out;
        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        int correct = 0;
        String feedback = "";

        try {
            Document doc;
            BufferedReader br = new BufferedReader(new FileReader("data/testCases.txt"));
            Scanner s = new Scanner(new File("data/counts.txt"));
            String input;
            int test = 1;

            while ((input = br.readLine()) != null) {
                doc = new EfficientDocument(input);
                
                feedback += "\\n\\nTesting input " + test + ":";

                int count = s.nextInt();
                feedback += appendFeedback(test * 3 - 2, "Testing number of sentences");
                if (count != doc.getNumSentences()) {
                    feedback += "FAILED. Got " + doc.getNumSentences() + "; expected " + count + ".";
                } else {
                    feedback += "PASSED.";
                    correct++;
                }

                count = s.nextInt();
                feedback += appendFeedback(test * 3 - 1, "Testing number of words");
                if (count != doc.getNumWords()) {
                    feedback += "FAILED. Got " + doc.getNumWords() + "; expected " + count + ".";
                } else {
                    feedback += "PASSED.";
                    correct++;
                }

                count = s.nextInt();
                feedback += appendFeedback(test * 3, "Testing number of syllables");
                if (count != doc.getNumSyllables()) {
                    feedback += "FAILED. Got " + doc.getNumSyllables() + "; expected " + count + ".";
                } else {
                    feedback += "PASSED.";
                    correct++;
                }
                test += 1;
            }
            if (correct == TESTS) {
                feedback = "Congrats! You passed all the tests!\\n" + feedback;
            }
            else {
                feedback = "Some tests failed. Please check the following: \\n" + feedback;
            }
        } catch (Exception e) {
            feedback += "\\nError during runtime: " + e;
        }

        out.println("{\"fractionalScore\": " + ((float)(correct) / TESTS) + ", \"feedback\": \"" + feedback + "\"}");
        out.close();
    }
}
