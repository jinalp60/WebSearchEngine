package WebSearchEngine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SearchFrequency {

	public static void FreqCount(String keyword) throws IOException {
		keyword.toLowerCase();
		FileReader fr = new FileReader("src/Wordsmemory.txt"); // Creation of File Reader object
		FileWriter fw = new FileWriter("src/Wordsmemory.txt", true);
		BufferedReader br = new BufferedReader(fr); // Creation of BufferedReader object
		String s1;
		String input = keyword; // Input word to be searched
		int count = 0; // Intialize the word to zero
		while ((s1 = br.readLine()) != null) // Reading Content from the file
		{
			String[] wordsplit = s1.split(" "); // Split the word using space
			for (String word : wordsplit) {
				if (word.equals(input)) // Search for the given word
				{
					count++; // If Present increase the count by one
				}

			}
		}
		if (count != 0) // Check for count not equal to zero
		{
			System.out.println("-------------------------------------------------");
			System.out.println("This word has been searched for " + count + " times.");
			System.out.println("-------------------------------------------------");
			fw.write(keyword + "\n");
			fw.close();
		} else {
			System.out.println("-------------------------------------------------");
			System.out.println("The given word was not searched before. adding the word to the library.");
			System.out.println("-------------------------------------------------");

			fw.write(keyword + "\n");
			fw.close();
		}

		fr.close();
	}
}
