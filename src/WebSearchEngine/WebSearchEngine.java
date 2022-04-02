package WebSearchEngine;

import java.util.HashMap;
import java.util.Scanner;

import WebSearchEngine.WebCrawler;

public class WebSearchEngine {

	public static void main(String args[]) {

		try {
			Scanner scanner = new Scanner(System.in);

			System.out.println("Welcome to Web Search Engine !!\n");

			System.out.println("-------------------------------------------------------------------\n");

			System.out.println("Please enter the URL which you want to crawl: \n");

			String urlToCrawl = scanner.next();

			System.out.println("Crawling has started ...\n");

			WebCrawler wc = new WebCrawler();
			wc.getPageLinks(urlToCrawl);
			System.out.println("Crawling has finished ...\n");
			HashMap<String, String> webPagesNames = wc.getHTMLWebPages();
			wc.HTMLToTextconverter();

			SearchWord searchWord = new SearchWord();
			SpellChecking sc = new SpellChecking();
			sc.getVocab(); 
			while(true) {
				System.out.println("------------------------------------------------------------\n");
				System.out.println("Please enter the word which you want to search or type quit : \n");
				String wordToSearch = scanner.next();
				if(wordToSearch.equals("quit")) {
					break;
				} else {
					
					
					if(!sc.isCorrect(wordToSearch)){
						String s[] = sc.getAltWords(wordToSearch); 
						System.out.println("The word might be misspelled!"); 
						System.out.println("The top ten suggestions include: "); 
						for(int i=0; i<10; i++){
							System.out.println(s[i]); 
						}
					}
					else {
						searchWord.searchForWord(wordToSearch, webPagesNames);
					}
					
				}
			} 
			
			System.out.println("Program ended");

		} catch (Exception e) {
			// handle exception
			System.out.println("Exception occured " + e);
		}

	}
}
