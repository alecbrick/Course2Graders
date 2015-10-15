package spelling;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TrieGrader {
    private static final int LENGTH = 500;

    private int incorrect = 0;
    private int tests = 0;
    String feedback = ""; 

    public static String makeJson(double score, String feedback) {
        return "{\"fractionalScore\": " + score + ", \"feedback\": \"" + feedback + "\"}";
    }

    
    
    public static void main(String[] args) {
        TrieGrader g = new TrieGrader();

        PrintWriter out;
        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        try {
            AutoCompleteDictionaryTrie ac = new AutoCompleteDictionaryTrie();
            
            g.testAddWords(ac);

            g.testWordsInOut(ac);

            g.testPredictions(ac);

        } catch (Exception e) {
            out.println(g.makeJson(0.0, g.getFeedback() + "Error during runtime: " + e ));
            out.close();
            return;
        }

        String feedback = g.getFeedback();
        int tests = g.getTests();
        int incorrect = g.getIncorrect();
            
        if (incorrect == 0) {
            feedback = feedback + "Congrats! All tests passed. :)";
        }
        else {
            feedback = "Some tests failed. Please check the following:;  " + feedback;
        }
            
        out.println(g.makeJson((double)(tests - incorrect) / tests, feedback));
        out.close();
    }


    private void testAddWords(AutoCompleteDictionaryTrie ac) {
        feedback += "TESTING ADDING WORDS (addWord, insert)  ";
        feedback += "//*  TEST #1 : Adding first word to dictionary...  *// ";
        if (!ac.addWord("dog")) {
            feedback += "FAILED: Simple word add failed ";
            incorrect++;
        }
        else {
            feedback += "PASSED: First word successfully added ";
        }

        tests++;

        feedback += "//*  TEST #2 : Adding two more words and testing size...  *// ";
        ac.addWord("downhill");
        ac.addWord("downhiller");
        
        if(ac.size() != 3) {
            feedback += "FAILED: Incorrect size after adding three words ";
            incorrect++;
        }
        else {
            feedback += "PASSED: Correct size after adding three words ";
        }
        tests++;


        feedback += "//* TEST #3 : Adding list of words to dictionary trie... (testing size after insert)  *// ";
        feedback += "Populating List of words... ";

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
            feedback += "FAILED: Incorrect size after adding list: expected 24, got " + ac.size() + " ";
            incorrect++;
        }
        else {
            feedback += "PASSED: Correct size after adding list of words! ";
        }

        tests++;

        // get current size before trying to add duplicate word
        int expectedSize = ac.size();

        feedback += "//* TEST #4 : Adding duplicate word...  *// ";
        if(ac.addWord("dog")) {
            feedback += "FAILED: addWord returned true when adding duplicate word ";
            incorrect++;
        }
        else {
            feedback += "PASSED: addWord returned false when adding duplicate word ";
        }
        tests++;

        feedback += "//* TEST #5 : Checking size after try to add duplicate word...  *// ";
        if(ac.size() != expectedSize) {
            feedback += "FAILED: Incorrect size after trying to add duplicate word: expected " + expectedSize + ", got " + ac.size() + " ";
            incorrect++;
        }
        else {
            feedback += "PASSED: Size unchanged when adding duplicate ";
        }

        tests++;

    }

    private void testWordsInOut(AutoCompleteDictionaryTrie ac) {
        // TEST WORDS IN/OUT OF DICTIONARY

        feedback += "TESTING FOR WORDS IN/OUT OF DICTIONARY (isWord) ";
        feedback += "//*  TEST #6 : Checking empty string...  *// ";
        // test empty string
        if(ac.isWord("")) {
            incorrect++;
            feedback += "FAILED: Empty string found in dictionary. ";
        }
        else {
            feedback += "PASSED ";
        }

        tests++;

        feedback += "//*  TEST #7 : Checking for word inserted from list...  *// ";
        if (!ac.isWord("doggoes")) {
            feedback += "FAILED: Can't find word inserted from a list ";
            incorrect++;
        }
        else {
            feedback += "PASSED: Word inserted from list found ";
        }


        tests++;



        // test word only missing last letter
        feedback += "//*  TEST #8 : Testing word only missing last letter...  *// ";
        if(ac.isWord("downhil")) {
            incorrect++;
            feedback += "FAILED: Make sure you are testing for the entire word in isWord. i.e. 'downhil' is not valid, 'downhill' is ";
        }
        else {
            feedback += "PASSED: Off by one word not found ";
        }

        tests++;

        //test word with added letter
        feedback += "//*  TEST #9 : Testing word with one extra letter...  *// ";
        if(ac.isWord("downhille")) {
            incorrect++;
            feedback += "FAILED: isWord returns true when word valid except for additional last letter. i.e. 'downhille' is not valid, 'downhill' is. ";
        }
        else {
            feedback += "PASSED: Off by one word not found. ";
        }

        tests++;

        
        feedback += "//*  TEST #10 : Testing for more words in dictionary...  *// ";
        if(!ac.isWord("test") || !ac.isWord("testcases") || !ac.isWord("testone")) {
            incorrect++;
            feedback += "FAILED: isWord returns false when passed valid string. ";
        }
        else {
            feedback += "PASSED: Standard word lookups return as expected ";
        }

        tests++;

        feedback += "//*  TEST #11 : Testing word with capital letters...  *// "; 
        if(!ac.isWord("TeSt")) {
            incorrect++;
            feedback += "FAILED: Make sure you are converting to lowercase when adding and checking words ";
        }
        else {
            feedback += "PASSED: Word with capitals still found ";
        }

        tests++;
        
            

    }

    private void testPredictions(AutoCompleteDictionaryTrie ac) {

        feedback += "//*  TEST #12 : TESTING AUTO COMPLETE FUNCTIONALITY (predictCompletions)  *// ";
        List<String> auto = ac.predictCompletions("dog", 3);
        
        feedback += "//*  TEST #13 : completions requested...  *// ";
        if (!(auto.contains("dog") && auto.contains("dogg") && auto.contains("doge"))) {
            feedback += "FAILED: predictCompletions doesn't return the correct completions - make sure it returns the shortest words ";
            incorrect++;
        }
        else {
            feedback += "PASSED: predictCompletions returns list with correct 3 completions ";
        }

        feedback += "//*  TEST #14 : Testing size of list...  *// ";
        if (auto.size() != 3) {
            feedback += "FAILED: predictCompletions returns " + auto.size() + " elements when it should return 3 ";
            incorrect++;
        }
        else {
            feedback += "PASSED: predictCompletions returns list of correct size ";
        }

        tests += 2;

        auto = ac.predictCompletions("soup", 6);
        feedback += "//*  TEST #14 : 6 completions requested, 0 expected...  *// ";
        if (auto.size() != 0) {
            feedback += "FAILED: predictCompletions found words where no words should have been found ";
            incorrect++;
        }
        else {
            feedback += "PASSED: predictCompletions returned list of size 0 ";
        }

        tests++;

        auto = ac.predictCompletions("dogg", 10);
        feedback += "//*  TEST #15 : 10 completions requested, 6 expected...  *// ";
        if (auto.size() != 6) {
            feedback += "FAILED: predictCompletions returns " + auto.size() + " elements when it should return 6 ";
            incorrect++;
        }
        else {
            feedback += "PASSED: predictCompletions returns list of size 6 as expected ";
        }
        
        feedback += "//*  TEST #16 : Testing for correctness of 6 words...  *//";
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("doggo");
        expected.add("doggie");
        expected.add("doggos");
        expected.add("doggoes");
        expected.add("doggies");
        if(!auto.containsAll(expected)) {
            feedback += "FAILED: predictCompletions does not return expected words ";
            incorrect++;
        }
        else {
            feedback += "PASSED: predictCompletions returned expected words ";
        }
        tests += 2;

        auto = ac.predictCompletions("test", 7);

        feedback += "//*  TEST #17 : 7 completions requested... (test for size)  *// ";
        if(auto.size() != 7) {
            feedback += "FAILED: predictCompletions returns " + auto.size() + " elements when it should return 7 ";
            incorrect++;
        }
        else {
            feedback += "PASSED: predictCompletions returned list of size 7 as expected ";
        }

        tests++;

        expected.clear();
        expected.add("test");
        expected.add("tester");
        expected.add("tested");
        expected.add("testin");
        expected.add("teston");

        feedback += "//*  TEST #18 : Testing if list is sorted from shortest to longest...  *// ";
        boolean failed = false;
        for(int i = 0; i < auto.size() - 1; i++) {

            if(auto.get(i+1).length() < auto.get(i).length()) {
                feedback += "FAILED: String at index " + (i+1) + " is shorter than string at index " + i + " ";
                incorrect++;
                failed = true;
                break;
            }

        }
        
        if(!failed) {
            feedback += "PASSED: List sorted correctly ";
        }

        tests++;

        

        List<String> partialList = auto.subList(0, 5);
        
        feedback += "//*  TEST #19 : Testing if list contains correct shorter words...  *// ";
        if(!partialList.containsAll(expected)) {
            feedback += "FAILED: predictCompletion returns incorrect words ";
            incorrect++;
        }  
        else {
            feedback += "PASSED: predictCompletions returns expected shorter words ";
        }

        tests++;


        feedback += "//*  TEST #20 : Testing for remaining words...  *// ";
        partialList = auto.subList(5, auto.size());

        int count = 0;

        count = partialList.contains("testone") ? ++count:count ;
        count = partialList.contains("testine") ? ++count:count;
        count = partialList.contains("testell") ? ++count:count;
        count = partialList.contains("testing") ? ++count:count;

        if(count != 2) {
            feedback += "FAILED: Last (longer) words in list not as expected ";
        }
        else {
            feedback += "PASSED: Valid longer words found in list ";
        }
       
        tests++;

            
    }

    private int getIncorrect() {
        return this.incorrect;
    }
    private int getTests() {
        return this.tests;
    }
    private String getFeedback() {
        return this.feedback;
    }


}

