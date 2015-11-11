package textgen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MyLinkedListGrader {
	
	PrintWriter out;
        String feedback;
	
	public String printListForwards(MyLinkedList<Integer> lst)
	{
		LLNode<Integer> curr;
                String ret = "";
		if (lst.head.data == null)
			curr = lst.head.next;
		else
			curr = lst.head;
		
		while (curr != null && curr.data != null)
		{
			ret += curr.data;
			curr = curr.next;
		}
                return ret;
	}
	
	public String printListBackwards(MyLinkedList<Integer> lst) {
		LLNode<Integer> curr;
                String ret = "";
		if (lst.tail.data == null)
			curr = lst.tail.prev;
		else
			curr = lst.tail;
		while (curr != null && curr.data != null)
		{
		        ret += curr.data;
			curr = curr.prev;
		}
                return ret;
	}

        public String appendFeedback(int num, String desc) {
                return "\\n** Test #" + num + ": " + desc + "...";
        }
	
	public void doTest()
	{
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
                        MyLinkedList<Integer> lst = new MyLinkedList<Integer>();
                        int nums[] = {1, 2, 3, 4, 5};
                        
                        feedback += appendFeedback(1, "Adding to end of list");
                        for (int i : nums) {
                                lst.add(i);
                        }
                        if (!printListForwards(lst).equals("12345")) {
                                feedback += "FAILED. Expected list to contain 12345, got " + printListForwards(lst) + ". ";
                                incorrect++;
                        }
                        else {
                                feedback += "PASSED. ";
                        }
                        tests++;

                        feedback += appendFeedback(2, "Getting from the middle");
                        if (lst.get(3) != 4 && incorrect == 0) {
                            feedback += "FAILED. Fourth element was " + lst.get(3) + "; expected 4. ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }
                        tests++; 
                        
                        lst.add(2, 6);
                        
                        feedback += appendFeedback(3, "Adding to the middle");
                        if (!printListForwards(lst).equals("126345")) {
                            feedback += "FAILED. Expected 126345, got " + printListForwards(lst) + ". Make sure you correctly update your 'next' pointers. ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }

                        feedback += appendFeedback(4, "Testing 'prev' pointers");
                        if (!printListBackwards(lst).equals("543621")) {
                            feedback += "FAILED. Expected 543621, got " + printListBackwards(lst) + ". Make sure you correctly update your 'prev' pointers. ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }

                        feedback += appendFeedback(5, "Testing list size");
                        if (lst.size() != 6) {
                            feedback += "FAILED. Incorrect list size after adding in the middle. Expected list size of 6, got " + lst.size() + ". ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }
                        tests += 3;
                        
                        lst.remove(2);
                        feedback += appendFeedback(6, "Removing from the middle");
                        if (!printListForwards(lst).equals("12345") && incorrect == 0) {
                            feedback += "FAILED. Expected 12345, got " + printListForwards(lst) + ". Make sure you correctly update your 'next' pointers. ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }
                        
                        feedback += appendFeedback(7, "Testing 'prev' pointers on remove");
                        if (!printListBackwards(lst).equals("54321")) {
                            feedback += "FAILED. Expected 54321, got " + printListBackwards(lst) + ". Make sure you correctly update your 'prev' pointers. ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }

                        feedback += appendFeedback(8, "Testing size after remove");
                        if (lst.size() != 5) {
                            feedback += "FAILED. Expected list size of 5, got " + lst.size() + ". ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }
                        tests += 3;

                        feedback += appendFeedback(9, "Testing add, remove, and add on new list");
                        lst = new MyLinkedList<Integer>();
                        lst.add(0, 1);
                        lst.remove(0);
                        lst.add(0, 1);
                        if (!printListForwards(lst).equals("1")) {
                            feedback += "FAILED. Expected a list containing just 1, got " + printListForwards(lst) + ". ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }

                        feedback += appendFeedback(10, "Checking size after previous test");
                        if (lst.size() != 1) {
                            feedback += "FAILED. List size is " + lst.size() + ", expected 1. Check that you correctly update your size count where required. ";
                            incorrect++;
                        }
                        else {
                            feedback += "PASSED. ";
                        }
                        tests += 2;

                        feedback += appendFeedback(11, "Testing lower bound of get");
                        try
                        {

                                lst.get(-1);
                                feedback += "FAILED. Your list should throw an exception on a negative 'get'. ";
                                incorrect++;
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";
                        }
                        
                        feedback += appendFeedback(12, "Testing upper bound of get");
                        try
                        {
                                lst.get(2);
                                feedback += "FAILED. Your list should throw an exception when the argument to 'get' is too high. ";
                                incorrect++;
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";
                        }
                        
                        feedback += appendFeedback(13, "Testing lower bound of set");
                        try
                        {
                                lst.set(-1, 2);
                                feedback += "FAILED. Your list should throw an exception when the index of 'set' is too low. ";
                                incorrect++;
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";
                        }
                        
                        feedback += appendFeedback(14, "Testing upper bound of set");
                        try
                        {
                                lst.set(2, 2);
                                feedback += "FAILED. Your list should throw an exception when the index of 'set' is too high. ";
                                incorrect++;
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";	
                        }
                        
                        feedback += appendFeedback(15, "Using set with null value");
                        try
                        {
                                lst.set(0, null);
                                feedback += "FAILED. Your list should throw an exception when the element of 'set' is null. ";
                                incorrect++;
                        }
                        catch (NullPointerException e)
                        {
                                feedback += "PASSED. ";
                        }
                        
                        feedback += appendFeedback(16, "Testing lower bound of remove");
                        try
                        {
                                lst.remove(-1);
                                feedback += "FAILED. Your list should throw an exception when the index of 'remove' is too low. ";
                                incorrect++;
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";
                        }
                        
                        feedback += appendFeedback(17, "Testing upper bound of remove");
                        try
                        {
                                lst.remove(2);
                                feedback += "FAILED. Your list should throw an exception when the index of 'remove' is too high. ";
                                incorrect++;
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";
                        }

                        feedback += appendFeedback(18, "Testing lower bound of add");
                        try
                        {
                                lst.add(-1, 5);
                                feedback += "FAILED. Your list should throw an exception when the index of 'add' is too low. ";
                                incorrect++;
                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";
                        }

                        feedback += appendFeedback(19, "Testing upper bound of add");
                        try
                        {
                                lst.add(3, 5);
                                feedback += "FAILED. Your list should throw an exception when the index of 'add' is too high. ";
                                incorrect++;

                        }
                        catch (IndexOutOfBoundsException e)
                        {
                                feedback += "PASSED. ";
                        }

                        feedback += appendFeedback(20, "Adding null element");
                        try
                        {
                                lst.add(1, null);
                                feedback += "FAILED. Your list should throw an exception when the element of 'add' is null. ";
                                incorrect++;
                        }
                        catch (NullPointerException e)
                        {
                                feedback += "PASSED. ";
                        }
                        tests += 10;

                        if (incorrect == 0) {
                                feedback = "Congrats! All test cases passed.\\n\\n" + feedback;
                        }
                        else {
                                feedback = "Some test cases failed. Please check the following: \\n\\n" + feedback;
                        }
                } catch (Exception e) {
                        out.println("{\"fractionalScore\": 0.0, \"feedback\": \""+ feedback + "Error during runtime: " + e + "\"}");
                        out.close();
                        return;
                }
    
                out.println("{\"fractionalScore\": " + (float)(tests - incorrect) / tests + ", \"feedback\": \"" + feedback + "\"}");
                out.close();
        }
	
	public static void main(String args[])
	{
		MyLinkedListGrader grader = new MyLinkedListGrader();
		grader.doTest();
	}
	
	
}
