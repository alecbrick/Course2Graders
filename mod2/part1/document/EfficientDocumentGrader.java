package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class EfficientDocumentGrader {
    PrintWriter out;
    String feedback;

    public String appendFeedback(int num, String desc) {
        return "\\n** Test #" + num + ": " + desc + "...";
    }

    public String appendFailure(int s, int w, int syl, EfficientDocument doc) {
        return "FAILED. Expected " + s + " sentences, " + w + " words, " + syl + " syllables; got " + doc.getNumSentences() + " sentences, " + doc.getNumWords() + " words, " + doc.getNumSyllables() + " syllables.";
    }

    public void runTests() {
        int incorrect = 0;
        int tests = 0;
        feedback = "";
        try {
            out = new PrintWriter("output.out", "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("testcases.txt"));
            feedback += appendFeedback(1, "Testing first string");
            
            EfficientDocument doc = new EfficientDocument(br.readLine());
            if (doc.getNumSentences() != 2 || doc.getNumWords() != 16 || doc.getNumSyllables() != 29) {
                feedback += appendFailure(2, 16, 29, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(2, "Testing second string");
            
            doc = new EfficientDocument(br.readLine());
            if (doc.getNumSentences() != 2 || doc.getNumWords() != 7 || doc.getNumSyllables() != 9) {
                feedback += appendFailure(2, 7, 9, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(3, "Testing third string");
            
            doc = new EfficientDocument(br.readLine());
            if (doc.getNumSentences() != 3 || doc.getNumWords() != 7 || doc.getNumSyllables() != 8) {
                feedback += appendFailure(3, 7, 8, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(4, "Testing fourth string");
            
            doc = new EfficientDocument(br.readLine());
            if (doc.getNumSentences() != 5 || doc.getNumWords() != 5 || doc.getNumSyllables() != 6) {
                feedback += appendFailure(5, 5, 6, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(5, "Testing fifth string");
            
            doc = new EfficientDocument(br.readLine());
            if (doc.getNumSentences() != 118 || doc.getNumWords() != 990 || doc.getNumSyllables() != 1984) {
                feedback += appendFailure(118, 990, 1984, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            double score;
            if (incorrect == 0) {
                feedback = "Congrats! All tests passed. \\n\\n" + feedback;
                score = 1.0;
            } else {
                feedback = "Some tests failed. Please check the following: \\n\\n" + feedback;
                score = 0.0;
            }
            out.println("{\"fractionalScore\": " + score + ", \"feedback\": \"" + feedback + "\"}");
            out.close();
        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"Error during runtime: " + e + "\"}");
            out.close();
        }
    }

    public static void main(String args[]) {
        EfficientDocumentGrader grader = new EfficientDocumentGrader();
        grader.runTests();
    }
}
