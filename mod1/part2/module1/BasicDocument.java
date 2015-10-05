package module1;

// Included because you'll probably want it.
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * A naive implementation of the Document abstract class. 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class BasicDocument extends Document 
{
	/** Create a new BasicDocument object
	 * 
	 * @param text The full text of the Document.
	 */
	public BasicDocument(String text)
	{
		super(text);
	}
	
	
	/**
	 * Get the number of words in the document.
	 * "Words" are defined as contiguous strings of alphabetic characters
	 * i.e. any upper or lower case characters a-z or A-Z
	 * 
	 * @return The number of words in the document.
	 */
	public int getNumWords()
	{
		int num = 0;
		
		for (String tok : getTokens(getText()))
		{
			if (isWord(tok))
			{
				num++;
			}
		}
		return num;
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Sentences are defined as contiguous strings of characters ending in an 
	 * end of sentence punctuation (. ! or ?) or the last contiguous set of 
	 * characters in the document, even if they don't end with a punctuation mark.
	 * 
	 * @return The number of sentences in the document.
	 */
	public int getNumSentences()
	{
		int num = 0;
                List<String> toks = getTokens(getText());
		for (int i = 0; i < toks.size(); i++)
		{
			System.out.println(toks.get(i));
			if (i == toks.size() - 1 || endsSentence(toks.get(i)))
			{
				//System.out.println("Ends a sentence.");
				num++;
			}
		}
		
		return num;
	}
	
	/**
	 * Get the number of sentences in the document.
	 * Words are defined as above.  Syllables are defined as:
	 * a contiguous sequence of vowels, except for an "e" at the 
	 * end of a word if the word has another set of contiguous vowels, 
	 * makes up one syllable.   y is considered a vowel.
	 * @return The number of syllables in the document.
	 */
	public int getNumSyllables()
	{
                int num = 0;

                List<String> toks = getTokens(getText());

                for (int i = 0; i < toks.size(); i++)
                {
                        if (isWord(toks.get(i)))
                        {
                                num += countSyllables(toks.get(i));
                        }
                }
                return num;
	}
	
	
	/* The main method for testing this class. 
	 * You are encouraged to add your own tests.  */
	//XXX FIX THESE TESTS
	public static void main(String[] args)
	{
		testCase(new BasicDocument("This is a test.  How many???  Senteeeeeeeeeences are here... there should be 5!  Right?"),
				16, 13, 5);
		testCase(new BasicDocument(""), 0, 0, 0);
		testCase(new BasicDocument("sentence, with, lots, of, commas.!  (And some poaren)).  The output is: 7.5."), 15, 11, 4);
		testCase(new BasicDocument("many???  Senteeeeeeeeeences are"), 6, 3, 2);
		
	}

        /** Return true if this string is a word (as opposed to punctuation */
	private boolean isWord(String tok)
	{
		// If the string has non space, non digit, and non punctionation chars, it's a word
		Pattern word = Pattern.compile("[\\S&&\\P{Punct}&&\\D]+");
		Matcher m = word.matcher(tok);
		return m.find();
	}
	
	private boolean endsSentence(String token)
	{
		Pattern endOfSentence = Pattern.compile("[.|!|?]+");
		Matcher m = endOfSentence.matcher(token);
		return m.find();
	}
	
}
