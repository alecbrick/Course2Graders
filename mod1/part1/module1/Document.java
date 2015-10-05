package module1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {	
	/** The text represented as a list of words and punctuation marks */
	private String text;
	
	/** Create a document from a string of text */
	protected Document(String text)
	{
                this.text = text;
	}
	
        protected List<String> getTokens(String text)
	{
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[.]{3}|[\\p{Punct}]+|[\\S&&\\P{Punct}]+");
		Matcher m = tokSplitter.matcher(text);
		while (m.find()) {
			tokens.add(m.group());
		}
		
		return tokens;
	}

	protected int countSyllables(String word)
	{
		int numSyllables = 0;
		boolean newSyllable = true;
                boolean firstSyllable = true;
		for (int i = 0; i < word.toCharArray().length; i++)
		{
                        char c = word.toCharArray()[i];
                        if (i == word.toCharArray().length - 1 && c == 'e' && !firstSyllable)
                        {
                            continue;
                        }
			else if (newSyllable && "aeiouy".indexOf(Character.toLowerCase(c)) >= 0) {
				newSyllable = false;
                                firstSyllable = false;
				numSyllables++;
			}
			else if (!newSyllable && !("aeiou".indexOf(Character.toLowerCase(c)) >= 0)) {
				newSyllable = true;
			}
		}
		return numSyllables;
	}

        public static boolean testCase(Document doc, int syllables, int words, int sentences)
	{
		System.out.println("Testing text: ");
		System.out.print(doc.getText() + "\n....");
		boolean passed = true;
		int syllFound = doc.getNumSyllables();
		int wordsFound = doc.getNumWords();
		int sentFound = doc.getNumSentences();
		if (syllFound != syllables) {
			System.out.println("\nIncorrect number of syllables.  Found " + syllFound 
					+ ", expected " + syllables);
			passed = false;
		}
		if (wordsFound != words) {
			System.out.println("\nIncorrect number of words.  Found " + wordsFound 
					+ ", expected " + words);
			passed = false;
		}
		if (sentFound != sentences) {
			System.out.println("\nIncorrect number of sentences.  Found " + sentFound 
					+ ", expected " + sentences);
			passed = false;
		}
		
		if (passed) {
			System.out.println("passed.\n");
		}
		else {
			System.out.println("FAILED.\n");
		}
		return passed;
	}

        public abstract int getNumWords();
        public abstract int getNumSentences();
        public abstract int getNumSyllables();

	public String getText()
        {
                return this.text;
        }

	/** Calculates and returns the fleschIndex score of this document */
	public double getFleschScore()
	{
		int numWords = getNumWords();
		int numSentences = getNumSentences();
		int numSyllables = getNumSyllables();
		
		return 206.835 - (1.015 * (numWords/(float)numSentences)) 
				- 84.6*(numSyllables/(float)numWords);
	}
}
