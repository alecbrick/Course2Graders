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
                BasicDocument x = new BasicDocument("Lorem ipsum dolor sit amet, qui ex choro quodsi moderatius, nam dolores explicari forensibus ad. Vidit mnesarchum quo ut, tempor volutpat contentiones ex pro, quo at elitr dolore. Idque placerat id his, nisl dicit has cu, cu quo clita detracto prodesset. Est tale voluptua cu, in ornatus epicuri nam. Ut falli nobis vel, mei salutatus referrentur et. Quo nisl augue vituperata in, percipit intellegat usu in. Has esse sensibus splendide te, bonorum perpetua eum an. No sit mediocrem deseruisse, graece vocent eos ea, nibh summo praesent et sea. Melius maiorum persequeris vis an, usu ad eirmod definitionem, ne vis odio tamquam repudiare. Id est esse graeco oporteat. Pro et erat persius, his stet libris ridens et. Animal vulputate disputando eam at. Per an zril nostro suavitate, eum te exerci comprehensam. Et eos cetero labores, eam graeci tamquam ei. Eu pri perfecto ocurreret liberavisse. Et nulla feugiat philosophia vix, at mei oratio explicari deterruisset. Ea mea aliquip fuisset suscipiantur. Cum inani nonumes appetere at, nibh senserit his eu. Pri nusquam iudicabit signiferumque ut, his ex debet oportere deseruisse. Inermis reprimique ei pri. Summo salutatus sed at, cu vel enim quodsi definitiones. Quo facete eligendi iracundia in, pri vero prima quaerendum ad, solet gubergren cum eu. Duo te volumus singulis adipisci. Veri omnesque in vix. At mel stet erant ludus, id usu congue persequeris. Sit omnesque convenire ne. Vis an verterem reformidans, vim suas singulis definitionem eu. Has sale aeque efficiantur in, cu eam agam tota maiestatis, te sea repudiare mnesarchum. Eu maiorum epicuri sed, ullum affert omittantur at est. Eum dolorum reprehendunt ad. Vivendum invenire sea no, ne vis suas liber adipiscing. Consequat expetendis at sed, est eu labore aperiri ocurreret, est noster voluptua contentiones eu. Te nam lorem signiferumque, per te ludus quodsi. Sea vidit porro an, eos ea aeterno maiestatis consectetuer, te sed constituto ullamcorper philosophia. Dicta appareat maiestatis ius ut, vis illud viderer temporibus eu, quas elitr eos et. Hinc vidit possit ius id, altera adversarium eum ad. Cu debitis epicurei pro, postea deseruisse nec ad. No mel eruditi efficiantur. No sea meis imperdiet disputationi. Per labore atomorum tractatos an. Cum quot nostrum perpetua id, dico mundi nam ex. Eam an iriure vulputate voluptatum. Eu sit insolens iracundia contentiones. Nostrud tincidunt no usu, cum at cetero vivendo inimicus, no stet gubergren splendide has. Aliquip placerat reformidans te his, verterem convenire forensibus te pro, cu pri voluptua honestatis. Ex summo munere placerat sea, pro ei essent integre definitiones, per ex epicuri scripserit. Cu dico omnes sit, eu ius tale partem, mei quis errem eripuit ex. Et homero aliquam tibique pri. Mea ut utinam tamquam, at erat mucius sapientem qui. Te eam error euripidis accommodare, et quod nostrum vix. Ex quo tale ipsum vivendo. An persecuti liberavisse sed, eum cu viderer eruditi, id ius discere utroque aliquando. Regione vocibus assentior duo te. Meis legere vivendum vel ut, per repudiare vulputate ea, cum in offendit indoctum rationibus. Aeque affert forensibus cu vix, ea tibique neglegentur eam. Case civibus nam et. An mei dicit similique. Has in malorum scaevola omittantur. Vim et habemus detraxit, te sed dico invenire electram. Usu viderer fabellas intellegam an, no saepe facete vis, aliquip reformidans qui ne. Ad mea scaevola pertinax, has an amet aeque. Ut sit affert mandamus. Erant dicunt maiorum ea eam. Usu purto movet explicari et, inani legere facilis cum no. No his putant nonumes perpetua, cum fugit necessitatibus at. Eu has agam moderatius, eos ei omnis percipitur honestatis, verterem scribentur eloquentiam eam an. Ex vis quod reprimique, an vix utroque officiis. Cu sumo option rationibus mea, aliquid facilisis qui ne. Eirmod petentium conclusionemque mei te, sonet altera sadipscing per at. Aliquam corpora ut cum, cu hinc posse malorum usu, no quo graeci qualisque honestatis. Blandit accusata adipiscing ea his. Vix ea tale iuvaret periculis. Autem ignota ei pro, ad cum dicam timeam. Duo officiis phaedrum repudiandae id, usu ut brute inani tincidunt. Sit semper impedit cotidieque ei. Nostrud propriae voluptatum cum ea. Nec clita dolorum oportere et, sea ne accusamus efficiendi complectitur, nulla nostro ut mei. Aliquid fierent ex est, vis ullum tamquam dissentiet ne. Epicurei reformidans te mel, ius malis maluisset ex. Id perfecto sensibus mei, mei sanctus fastidii periculis id. Atomorum incorrupte an cum, elit nostrud sadipscing ad sed. Saepe nostrud ad nam. Est congue veritus percipit ut, te mundi populo oportere sea. Corpora probatus efficiantur ei eum. Altera pertinax postulant mei ne, error partem offendit sit cu. Modo semper tincidunt ius id, mei an stet imperdiet delicatissimi. Id mea homero efficiendi. Diam minim similique duo ut. Eu mucius delenit pri. Cu ridens expetenda corrumpit eum, id per impedit mediocrem, idque audiam te mel. Ad probo aliquid mel, eruditi inermis offendit ius te, voluptua conceptam pri id. In error invidunt aliquando vis. Veniam splendide assentior ad duo, an dicta vocent sit. Ad ubique indoctum philosophia usu, ea eum dolores dissentiunt. Mea salutandi temporibus ut, iusto oblique at est, tollit postea putent his te. Ad ius atqui liber latine. Porro abhorreant vituperata an pro, sit choro deserunt at, tale verear ceteros vim an. Per detracto facilisis signiferumque ex, vis et platonem eloquentiam, in mutat eruditi mei. Antiopam explicari ut sea, diam maiorum temporibus no est, vix cu consul dolorem menandri. His eu meis albucius adipiscing. Pri audire appareat complectitur ea, vis eu homero aperiam denique. Qui inimicus pertinacia ei, voluptua partiendo qui ei. Tacimates rationibus percipitur pri ea, ea duo wisi apeirian dissentiet. Per eu equidem repudiare. Modus saepe laoreet vix cu, mucius euripidis est ea, mea iudico noster quodsi et. Rebum idque disputando eam no. Mel quodsi audire ad, quo iuvaret denique vituperata ad. Et mea possim sapientem maiestatis, usu congue labore ornatus ad, consul ceteros conceptam per in. Ad eam amet etiam voluptaria. In cum labore timeam, ex putant dolorum usu. Usu offendit reprehendunt ex, cu eripuit nostrum gubergren his. Docendi democritum his et, cu eos mazim erant sapientem. Ad vim iriure philosophia. Dico voluptua ea sea, an mundi utamur iisque eum, justo aliquid ut ius. Mea et alia tritani.");
                System.out.println(x.getNumWords());
                System.out.println(x.getNumSentences());
                System.out.println(x.getNumSyllables());
		
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
