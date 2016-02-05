package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/** A class for timing the EfficientDocument and BasicDocument classes
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 *XXX This class needs fixing up before moving it to the starter code.
 */

public class DocumentBenchmarking {

	// Run each test more than once to get bigger numbers and less noise.
	public static final int TRIALS = 100;
	
	public static void main(String [] args) {
		String s = "document/warAndPeace.txt";
                PrintWriter out;
                try {
                    out = new PrintWriter("output.out");
		}
                catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

		int increment = 10000;
		String test = null;
		int numSteps = 20;
		int start = 50000;
		long startTime = 0;
		long endTime = 0;
		double seconds = 0;
		long startTime2 = 0;
		long endTime2 = 0;
		double seconds2 = 0;
		
                try {
                        for (int numToCheck = start; numToCheck < numSteps*increment + start; 
                                        numToCheck += increment)
                        {
                                test = getStringFromFile(s,numToCheck);
                                //System.out.println(test);
                        
                                Document b = new EfficientDocument(test);
                                startTime = System.nanoTime();
                                for(int i = 0;  i< TRIALS; i++) {
                                        //Document b = new BasicDocument(test);
                                        b.getFleschScore();
                                }
                                endTime = System.nanoTime();
                                seconds = ((double )endTime - startTime) / 1000000000;
                        
                                b = new BasicDocument(test);
                                startTime2 = System.nanoTime();
                                for(int i = 0;  i< TRIALS; i++) {
                                        //Document b = new EfficientDocument(test);
                                        System.out.println("Ayy");
                                        b.getFleschScore();
                                }
                                endTime2 = System.nanoTime();
                                seconds2 = ((double )endTime2 - startTime2) / 1000000000;

                                if (seconds >= seconds2) {
                                        out.println("{ \"fractionalScore\": 0.0, \"feedback\":\"Your EfficientDocument, which took " + seconds + " seconds, is not faster than your BasicDocument, which took " + seconds2 + " seconds.\" }");
                                        out.close();
                                        return;
                                }
            

                        }
                }
                catch (Exception e) {
                        out.println("{ \"fractionalScore\": 0.0, \"feedback\":\"Your code produced a runtime error: " + e + ".\" }");
                        out.close();
                        return;
                }
                out.println("{ \"fractionalScore\": 1.0, \"feedback\":\"You got it!\" }");
                out.close();
	}
	
	public static String getStringFromFile(String filename, int numChars) {
		
		StringBuffer s = new StringBuffer();
		try {
			FileInputStream inputFile= new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char)val);
				count++;
			}
			bis.close();
		}
		catch(Exception e)
		{
		  System.out.println(e);
		  System.exit(0);
		}
		
		
		return s.toString();
	}
	
}
