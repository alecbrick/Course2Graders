package spelling;

import java.io.PrintWriter;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.List;

public class TrieGrader implements Runnable {
    private static final int LENGTH = 500;

    private int correct = 0;
    public static final int TESTS = 20;
    StringBuilder feedback; 

    public PrintWriter out;

    public TrieGrader() {
        feedback = new StringBuilder();
    }


    public static String makeJson(double score, String feedback) {
        return "{\"fractionalScore\": " + score + ", \"feedback\": \"" + feedback + "\"}";
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            AutoCompleteDictionaryTrie ac = new AutoCompleteDictionaryTrie();
            this.testAddWords(ac);
            this.testWordsInOut(ac);
            this.testPredictions(ac);
        } catch (Exception e) {
            feedback.insert(0, "Error during runtime: " + feedback);
            return;
        }
    }
    
    public static void main(String[] args) {
        TrieGrader g = new TrieGrader();
        Thread thread = new Thread(g);
        thread.start();
        long endTimeMillis = System.currentTimeMillis() + 10000;
        boolean infinite = false;
        while(thread.isAlive()) {
            if (System.currentTimeMillis() > endTimeMillis) {
                thread.stop();
                infinite = true;
                break;
            }
        }

        StringBuilder feedback = g.getFeedback();

        if (infinite) {
            feedback.append("\\nYour program entered an infinite loop. Check for places that might cause problems (potentially infinite while loops, infinite recursion, etc.) and try again.");
        }

        int correct = g.getCorrect();
            
        if (correct == TESTS) {
            feedback.insert(0,"Congrats! All tests passed. :)\\n\\n");
        }
        else {
            feedback.insert(0, "Some tests failed. Please check tests marked FAILED: \\n\\n");
        }
            
        g.out.println(g.makeJson((double)correct / TESTS, feedback.toString()));
        g.out.close();
    }


    private void testAddWords(AutoCompleteDictionaryTrie ac) {
        feedback.append( "//TESTING ADDING WORDS (addWord)// ");
        appendTestString(1, "Adding first word to dictionary...");
        if (!ac.addWord("dog")) {
            feedback.append("FAILED. Simple word add failed ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        appendTestString(2,"Adding two more words and testing size...");
        ac.addWord("downhill");
        ac.addWord("downhiller");
        
        if(ac.size() != 3) {
            feedback.append("FAILED. Incorrect size after adding three words. ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        appendTestString(3, "Adding more words to dictionary trie (testing size after insertions)...");

        ac.addWord("doge");
        ac.addWord("dogg");
        ac.addWord("dawg");
        ac.addWord("dage");
        ac.addWord("doggo");
        ac.addWord("doggie");
        ac.addWord("doggos");
        ac.addWord("doggoes");
        ac.addWord("doggies");
        ac.addWord("test");
        ac.addWord("tester");
        ac.addWord("testing");
        ac.addWord("tested");
        ac.addWord("testin");
        ac.addWord("teston");
        ac.addWord("testone");
        ac.addWord("testine");
        ac.addWord("testell");
        ac.addWord("testcase");
        ac.addWord("testbase");
        ac.addWord("testcases");

        if (ac.size() != 24) {
            feedback.append("FAILED. Incorrect size after adding many words: expected 24, got " + ac.size() + " ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        // get current size before trying to add duplicate word
        int expectedSize = ac.size();

        appendTestString(4,"Adding duplicate word...");
        if(ac.addWord("dog")) {
            feedback.append("FAILED. addWord returned true when adding duplicate word ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }

        appendTestString(5, "Checking size after try to add duplicate word...");
        if(ac.size() != expectedSize) {
            feedback.append("FAILED. Incorrect size after trying to add duplicate word: expected " + expectedSize + ", got " + ac.size() + " ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


    }

    private void testWordsInOut(AutoCompleteDictionaryTrie ac) {

        feedback.append(" \\n\\n//TESTING FOR WORDS IN/OUT OF DICTIONARY (isWord)//");
        appendTestString(6,"Checking empty string...");
        // test empty string
        if(ac.isWord("")) {
            feedback.append("FAILED. Empty string found in dictionary. ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        appendTestString(7, "Checking for word in dictionary...");
        if (!ac.isWord("doggoes")) {
            feedback.append("FAILED. Can't find word added in dictionary");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }





        // test word only missing last letter
        appendTestString(8, "Testing word only missing last letter...");
        if(ac.isWord("downhil")) {
            feedback.append("FAILED. Make sure you are testing for the entire word in isWord. i.e. 'downhil' is not valid, 'downhill' is ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        //test word with added letter
        appendTestString(9, "Testing word with one extra letter...");
        if(ac.isWord("downhille")) {
            feedback.append("FAILED. isWord returns true when word valid except for additional last letter. i.e. 'downhille' is not valid, 'downhill' is. ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        
        appendTestString(10, "Testing for more words in dictionary...");
        if(!ac.isWord("test") || !ac.isWord("testcases") || !ac.isWord("testone")) {
            feedback.append("FAILED. isWord returns false when passed. ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        appendTestString(11, "Testing word with capital letters..."); 
        if(!ac.isWord("TeSt")) {
            feedback.append("FAILED. Make sure you are converting to lowercase when adding and checking words ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }

        
            

    }

    private void testPredictions(AutoCompleteDictionaryTrie ac) {

        feedback.append("\\n\\n//TESTING AUTO COMPLETE FUNCTIONALITY (predictCompletions)//");
        List<String> auto = ac.predictCompletions("dog", 3);
        
        appendTestString(12, "3 completions requested...");
        if (!(auto.contains("dog") && auto.contains("dogg") && auto.contains("doge"))) {
            feedback.append("FAILED. predictCompletions doesn't return the correct completions - make sure it returns the shortest words ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }

        appendTestString(13,"Testing size of list...");
        if (auto.size() != 3) {
            feedback.append("FAILED. predictCompletions returns " + auto.size() + " elements when it should return 3 ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        appendTestString(14, "6 completions requested, 0 expected...");
        auto = ac.predictCompletions("soup", 6);
        if (auto.size() != 0) {
            feedback.append("FAILED. predictCompletions found words where no words should have been found ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        appendTestString(15, "10 completions requested, 6 expected...");
        auto = ac.predictCompletions("dogg", 10);
        if (auto.size() != 6) {
            feedback.append("FAILED. predictCompletions returns " + auto.size() + " elements when it should return 6 ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }
        
        appendTestString(16, "Testing for correctness of 6 words...");
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("doggo");
        expected.add("doggie");
        expected.add("doggos");
        expected.add("doggoes");
        expected.add("doggies");
        if(!auto.containsAll(expected)) {
            feedback.append("FAILED. predictCompletions does not return expected words ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }

        appendTestString(17, "7 completions requested (test for size)...");
        auto = ac.predictCompletions("test", 7);

        if(auto.size() != 7) {
            feedback.append("FAILED. predictCompletions returns " + auto.size() + " elements when it should return 7 ");
        }
        else {
            feedback.append("PASSED. ");
            correct++;
        }


        expected.clear();
        expected.add("test");
        expected.add("tester");
        expected.add("tested");
        expected.add("testin");
        expected.add("teston");

        appendTestString(18, "Testing if list is sorted from shortest to longest...");
        boolean failed = false;
        for(int i = 0; i < auto.size() - 1; i++) {

            if(auto.get(i+1).length() < auto.get(i).length()) {
                feedback.append("FAILED. String at index " + (i+1) + " is shorter than string at index " + i + " ");
                failed = true;
                break;
            }

        }
        
        if(!failed) {
            feedback.append("PASSED. ");
            correct++;
        }


        List<String> partialList;


        appendTestString(19, "Testing if list contains correct shorter words...");
        if (auto.size() < 5) {
            feedback.append("FAILED. List does not contain 5 words.");
        } else {
            partialList = auto.subList(0, 5);
            
            if(!partialList.containsAll(expected)) {
                feedback.append("FAILED. predictCompletion returns incorrect words ");
            } else {
                feedback.append("PASSED. ");
                correct++;
            }
        }



        appendTestString(20, "Testing for remaining words...");
        if (auto.size() <= 5) {
            feedback.append("FAILED. List must contain more than 5 elements to pass.");
        } else {
            partialList = auto.subList(5, auto.size());

            int count = 0;

            count = partialList.contains("testone") ? ++count:count ;
            count = partialList.contains("testine") ? ++count:count;
            count = partialList.contains("testell") ? ++count:count;
            count = partialList.contains("testing") ? ++count:count;

            if(count != 2) {
                feedback.append("FAILED. Last (longer) words in list not as expected. ");
            }
            else {
                feedback.append("PASSED. ");
                correct++;
            }
        }
    }

    private void appendTestString(int num, String description) {
        feedback.append("\\n** Test #" + num + ": " + description);
    }

    private int getCorrect() {
        return this.correct;
    }
    private StringBuilder getFeedback() {
        return this.feedback;
    }


}

