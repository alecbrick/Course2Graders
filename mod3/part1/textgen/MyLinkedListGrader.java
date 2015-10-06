package textgen;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class MyLinkedListGrader {
	
	PrintWriter out;
	
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
	
	public void doTest()
	{
                int incorrect = 0;
                int tests = 0;
                String feedback = "";
		try {
			out = new PrintWriter("output.out", "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		MyLinkedList<Integer> lst = new MyLinkedList<Integer>();
		int nums[] = {1, 2, 3, 4, 5};
		
		for (int i : nums) {
			lst.add(i);
		}
                if (!printListForwards(lst).equals("12345")) {
                        feedback += "Adding to end of list failed - list does not contain expected elements. ";
                        incorrect++;
                }
                tests++;

                if (lst.get(3) != 4 && incorrect == 0) {
                    feedback += "Get from middle failed. ";
                    incorrect++;
                }
                tests++; 
		
		lst.add(2, 6);
		
		if (!printListForwards(lst).equals("126345")) {
                    feedback += "Adding in the middle incorrect - make sure you correctly update your 'next' pointers. ";
                    incorrect++;
                }
		if (!printListBackwards(lst).equals("543621")) {
                    feedback += "Adding in the middle incorrect - make sure you correctly update your 'prev' pointers. ";
                    incorrect++;
                }
		if (lst.size() != 6) {
                    feedback += "Size incorrect after adding in the middle. ";
                    incorrect++;
                }
                tests += 3;
		
		lst.remove(2);
		if (!printListForwards(lst).equals("12345") && incorrect == 0) {
                    feedback += "Removing from the middle incorrect - make sure you correctly update your 'next' pointers. ";
                    incorrect++;
                }
		if (!printListBackwards(lst).equals("54321") && incorrect <= 1) {
                    feedback += "Removing from the middle incorrect - make sure you correctly update your 'prev' pointers. ";
                    incorrect++;
                }
		if (lst.size() != 5 && incorrect <= 2) {
                    feedback += "Size incorrect after removing from the middle. ";
                    incorrect++;
		}
                tests += 3;

		lst = new MyLinkedList<Integer>();
		lst.add(0, 1);
		lst.remove(0);
		lst.add(0, 1);
		if (!printListForwards(lst).equals("1")) {
                    feedback += "Add, remove, and add to empty list failed. ";
                    incorrect++;
                }
                if (lst.size() != 1) {
                    feedback += "Size of list after add, remove, and add to empty list is incorrect; check that you correctly update your size count where required. ";
                    incorrect++;
                }
                tests += 2;

		try
		{
			lst.get(-1);
			feedback += "Your list should throw an exception on a negative 'get'. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{

		}
		
		try
		{
			lst.get(2);
			feedback += "Your list should throw an exception when the argument to 'get' is too high. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{

		}
		
		try
		{
			lst.set(-1, 2);
			feedback += "Your list should throw an exception when the index of 'set' is too low. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{

		}
		
		try
		{
			lst.set(2, 2);
                        feedback += "Your list should throw an exception when the index of 'set' is too high. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
		
		try
		{
			lst.set(0, null);
                        feedback += "Your list should throw an exception when the element of 'set' is null. ";
                        incorrect++;
		}
		catch (NullPointerException e)
		{

		}
		
		try
		{
			lst.remove(-1);
                        feedback += "Your list should throw an exception when the index of 'remove' is too low. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{

		}
		
		try
		{
			lst.remove(2);
                        feedback += "Your list should throw an exception when the index of 'remove' is too high. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{

		}

                try
		{
			lst.add(-1, 5);
                        feedback += "Your list should throw an exception when the index of 'add' is too low. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{

		}

                try
		{
			lst.add(3, 5);
                        feedback += "Your list should throw an exception when the index of 'add' is too high. ";
                        incorrect++;
		}
		catch (IndexOutOfBoundsException e)
		{

		}

                try
		{
			lst.add(1, null);
                        feedback += "Your list should throw an exception when the element of 'add' is null. ";
                        incorrect++;
		}
		catch (NullPointerException e)
		{

		}
                tests += 10;

                if (incorrect == 0) {
                        feedback = "Congrats! All test cases passed.";
                }
                out.println("{\"fractionalScore\": " + (1.0 - incorrect/ (float)tests) + ", \"feedback\": \"" + feedback + "\"}");
                out.close();
	}
	
	public static void main(String args[])
	{
		MyLinkedListGrader grader = new MyLinkedListGrader();
		grader.doTest();
	}
	
	
}
