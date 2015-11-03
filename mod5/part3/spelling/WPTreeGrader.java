package spelling;

import java.io.PrintWriter;
import java.util.List;

public class WPTreeGrader {
    public static String printPath(List<String> path) {
        if (path == null) {
            return "NULL PATH";
        }
        String ret = "";
        for (int i = 0; i < path.size(); i++) {
            ret += path.get(i);
            if (i < path.size() - 1) {
                ret += ", ";
            }
        }
        return ret;
    }

    public static String appendFeedback(int num, String desc) {
        return "\\n** Test #" + num + ": " + desc + "...";
    }

    public static void main (String[] args) {
        int incorrect = 0;
        int tests = 0;
        String feedback = "";

        PrintWriter out;
        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            WPTree tree = new WPTree(new NearbyWords(new DictionaryHashSet("data/dict.txt")));

            List<String> path = tree.findPath("pool", "spoon");

            feedback += appendFeedback(1, "Testing short path");
            if (!(path != null && path.size() == 3 && path.get(0).equals("pool") && path.get(1).equals("spool") && path.get(2).equals("spoon"))) {
                feedback += "FAILED. Your list was: " + printPath(path) + "; expected: pool, spool, spoon. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            path = tree.findPath("stools", "moon");

            feedback += appendFeedback(2, "Testing long path");
            if (!(path != null && path.size() == 9 &&
                path.get(0).equals("stools") &&
                path.get(1).equals("tools") &&
                path.get(2).equals("fools") &&
                path.get(3).equals("fool") &&
                path.get(4).equals("pool") &&
                path.get(5).equals("spool") &&
                path.get(6).equals("spoon") &&
                path.get(7).equals("soon") &&
                path.get(8).equals("moon"))) {
            
                feedback += "FAILED. Your list was " + printPath(path) + "; expected: stools, tools, fools, fool, pool, spool, spoon, soon, moon. ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            path = tree.findPath("foal", "needless");

            feedback += appendFeedback(3, "Testing impossible path");
            if (path != null) {
                feedback += "FAILED. Path found between 'foal' and 'needless' that should not exist: " + printPath(path) + ". ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;

            path = tree.findPath("needle", "kitten");
            
            feedback += appendFeedback(4, "Testing using a nonexistent word");
            if (path != null) {
                feedback += "FAILED. Path found involving nonexistent word: " + printPath(path) + ". ";
                incorrect++;
            }
            else {
                feedback += "PASSED. ";
            }
            tests++;
        } catch (Exception e) {
            out.println("{\"fractionalScore\": 0.0, \"feedback\": \"" + feedback + "\\nError during runtime: " + e + "\"}");
        }

        if (incorrect == 0) {
            feedback = "All tests passed. Well done!\\n" + feedback;
        } 
        else {
            feedback = "Some tests failed. Please check the following: \\n" + feedback;
        }

        out.println("{\"fractionalScore\": " + (float)(tests - incorrect) / tests + ", \"feedback\": \"" + feedback + "\"}");
        out.close();
    }
}
