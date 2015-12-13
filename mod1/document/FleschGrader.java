package document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class FleschGrader {
    public static final int TESTS = 6;

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
            Scanner s = new Scanner(new File("data/flesch.txt"));
            String input;
            int test = 1;

            while ((input = br.readLine()) != null) {
                doc = new BasicDocument(input);
                
                feedback += appendFeedback(test, "Testing input " + test);
                double score = (double)Math.round(s.nextDouble() * 10d) / 10d;
                double result = (double)Math.round(doc.getFleschScore() * 10d) / 10d;
                if (score != result) {
                    feedback += "FAILED. Got " + result + "; expected " + score + ".";
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
