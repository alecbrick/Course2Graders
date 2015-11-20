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

    public String appendFailure(double score, BasicDocument doc) {
        return "FAILED. Expected a Flesch score of " + score + "; got " + doc.getFleschScore() + ".";
    }

    public double realFleschScore(int numSentences, int numWords, int numSyllables) {
        
        return 206.835 - (1.015 * ((double)numWords/(double)numSentences)) 
		       - 84.6*((double)numSyllables/(double)numWords);
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
            
            BasicDocument doc = new BasicDocument(br.readLine());
            if (Math.round(doc.getFleschScore() * 10) != Math.round(realFleschScore(1, 4, 5) * 10)) {
                feedback += appendFailure(realFleschScore(1, 4, 5), doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(2, "Testing second string");
            
            doc = new BasicDocument(br.readLine());
            if (Math.round(doc.getFleschScore() * 10) != Math.round(realFleschScore(3, 12, 18) * 10)) {
                feedback += appendFailure(realFleschScore(3, 12, 18), doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(3, "Testing third string");
            
            doc = new BasicDocument(br.readLine());
            if (Math.round(doc.getFleschScore() * 10) != Math.round(realFleschScore(2, 13, 16) * 10)) {
                feedback += appendFailure(realFleschScore(2, 13, 16), doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(4, "Testing fourth string");
            
            doc = new BasicDocument(br.readLine());
            if (Math.round(10 * doc.getFleschScore()) != Math.round(10 * realFleschScore(4, 4, 7))) {
                feedback += appendFailure(realFleschScore(4, 4, 7), doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(5, "Testing fifth string");
            
            doc = new BasicDocument(br.readLine());
            if (Math.round(10 * doc.getFleschScore()) != Math.round(10 * realFleschScore(3, 7, 8))) {
                feedback += appendFailure(realFleschScore(3, 7, 8), doc);
                incorrect++;
            } else {
                feedback += "PASSED.";
            }
            tests++;

            feedback += appendFeedback(6, "Testing sixth string");
            
            doc = new BasicDocument(br.readLine());
            if (Math.round(10 * doc.getFleschScore()) != Math.round(10 * realFleschScore(115, 1000, 2008))) {
                feedback += appendFailure(realFleschScore(115, 1000, 2008), doc);
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
            out.println("{\"fractionalScore\": " + score + ", \"feedback\": " + feedback + "\"}");
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
