package application.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**Generates an array list of questions and statements intended to motivate the user to write.
 * Contains a method which randomly selects a statement from the list and passes it back to the calling method to display on the screen.
 * @author Kisa Burnett.
 * */

public class Motivation {
	private ArrayList<String> motivationalQuotes;
	
	public Motivation() {
		try {
			this.motivationalQuotes = loadQuotes("motivation.txt");
		} catch (IOException e) {
			System.out.println("Error: Motivational file not found. Please check the name and try again.");
		}
	}
	
	/**Gets the ArrayList of statements gathered from the file containing these statements and
	 * passes it back to the caller.
	 * @return motivationalQuotes ArrayList<String> which contains the motivational statements. */
	public ArrayList<String> getMotivationalQuotes() {
		return motivationalQuotes;
	}

	/**Passes in an ArrayList to the class's motivationalQuotes variable.
	 * @param motivationalQuotes ArrayList<String> which contains the motivational statements. */
	public void setMotivationalQuotes(ArrayList<String> motivationalQuotes) {
		this.motivationalQuotes = motivationalQuotes;
	}
	
	/**Retrieves statements from a file and passes it into an ArrayList, which is returned back to the caller.
	 * @param fileName String with the name of the file the method should utilize.
	 * @return quotes ArrayList<String> the collection of statements gathered from the file. */
	public ArrayList<String> loadQuotes(String fileName) throws IOException {
		ArrayList<String> quotes = new ArrayList<String>();
		File quoteList = new File(fileName);
		Scanner scan = new Scanner(quoteList);
		
		while(scan.hasNextLine()) {
			quotes.add(scan.nextLine());
		}
		
		scan.close();
		
		return quotes;
	}
	
	/**Randomly selects a quote from the statements collected in the motivationalQuotes ArrayList and returns it
	 * to the caller.
	 * @return pulledQuote String containing statement randomly selected from the motivationalQuotes variable.*/
	public String pickQuote() {
		String pulledQuote = new String();
		int min = 0;
		int max = (motivationalQuotes.size() - 1);
		
		int randSelection = (int)Math.floor(Math.random() * (max - min + 1) + min);
		
		pulledQuote = motivationalQuotes.get(randSelection);
		
		return pulledQuote;
	}
}
