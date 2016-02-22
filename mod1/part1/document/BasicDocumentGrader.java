package document;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class BasicDocumentGrader {
    PrintWriter out;
    String feedback;

    public String appendFeedback(int num, String desc) {
        return "\\n** Test #" + num + ": " + desc + "...";
    }

    public String appendFailure(int s, int w, int syl, BasicDocument doc) {
        return "FAILED. Expected " + s + " sentences, " + w + " words, " + syl + "syllables; got " + doc.getNumSentences() + " sentences, " + doc.getNumWords() + " words, " + doc.getNumSyllables() + " syllables.";
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
            BufferedReader br = new BufferedReader(new FileReader("data/testcases.txt"));
            feedback += appendFeedback(1, "Testing first string");
            
            BasicDocument doc = new BasicDocument(br.readLine());
            if (doc.getNumSentences() != 1 || doc.getNumWords() != 4 || doc.getNumSyllables() != 5) {
                feedback += appendFailure(1, 4, 5, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(2, "Testing second string");
            
            doc = new BasicDocument(br.readLine());
            if (doc.getNumSentences() != 3 || doc.getNumWords() != 12 || doc.getNumSyllables() != 18) {
                feedback += appendFailure(3, 12, 18, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(3, "Testing third string");
            
            doc = new BasicDocument(br.readLine());
            if (doc.getNumSentences() != 2 || doc.getNumWords() != 13 || doc.getNumSyllables() != 16) {
                feedback += appendFailure(2, 13, 16, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(4, "Testing fourth string");
            
            doc = new BasicDocument(br.readLine());
            if (doc.getNumSentences() != 4 || doc.getNumWords() != 4 || doc.getNumSyllables() != 7) {
                feedback += appendFailure(4, 4, 7, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(5, "Testing fifth string");
            
            doc = new BasicDocument(br.readLine());
            if (doc.getNumSentences() != 3 || doc.getNumWords() != 7 || doc.getNumSyllables() != 8) {
                feedback += appendFailure(3, 7, 8, doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(6, "Testing sixth string");
            
            doc = new BasicDocument(br.readLine());
            if (doc.getNumSentences() != 115 || doc.getNumWords() != 1000 || doc.getNumSyllables() != 2008) {
                feedback += appendFailure(115, 1000, 2008, doc);
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
        BasicDocumentGrader grader = new BasicDocumentGrader();
        grader.runTests();
    }
}
