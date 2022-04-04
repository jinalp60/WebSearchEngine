package WebSearchEngine;

import java.util.HashMap;
import java.util.Scanner;

import WebSearchEngine.WebCrawler;

public class WebSearchEngine {

	public static void main(String args[]) {

		try {

			Scanner scanner = new Scanner(System.in);
			HashMap<String, String> webPagesNames = null;
			SearchWord searchWord = new SearchWord();
			SpellChecking sc = new SpellChecking();
			sc.getVocab();
			System.out.println("Welcome to Web Search Engine !!\n");
			int option = 0;

			while (option != 3) {
				System.out.println("Please choose from following options: \n");
				System.out.println(" 1. Search Word \n 2. Crawl URL \n 3. Quit \n");

				option = scanner.nextInt();

				switch (option) {
				case 1:
					webPagesNames = UrlPreLoad.GetData();
					while (true) {
						System.out.println("------------------------------------------------------------\n");
						System.out.println("Please enter the word which you want to search or type quit : \n");
						String wordToSearch = scanner.next();
						if (wordToSearch.equals("quit")) {
							break;
						} else {
							
							SearchFrequency.FreqCount(wordToSearch);
							if (!sc.isCorrect(wordToSearch)) {
								String s[] = sc.getAltWords(wordToSearch);
								System.out.println("The word might be misspelled!");
								System.out.println("The top ten suggestions include: ");
								for (int i = 0; i < 10; i++) {
									System.out.println(s[i]);
								}
							} else {
								HashMap<String, Integer> frequencyMapToSort = searchWord.searchForWord(wordToSearch, webPagesNames);
								if(frequencyMapToSort.size() > 0) {
									PageRanking.sortWebPages(frequencyMapToSort);
								}
							}

						}
					}
					break;

				case 2:
					System.out.println("-------------------------------------------------------------------\n");
					System.out.println("Please enter the URL which you want to crawl: \n");
					String urlToCrawl = scanner.next();

					System.out.println("Crawling has started ...\n");

					WebCrawler wc = new WebCrawler();
					wc.getPageLinks(urlToCrawl);
					System.out.println("Crawling has finished ...\n");
					webPagesNames = wc.getHTMLWebPages();
					wc.HTMLToTextconverter();

					while (true) {
						System.out.println("------------------------------------------------------------\n");
						System.out.println("Please enter the word which you want to search or type quit : \n");
						String wordToSearch = scanner.next();
						if (wordToSearch.equals("quit")) {
							break;
						} else {
							
							SearchFrequency.FreqCount(wordToSearch);
							if (!sc.isCorrect(wordToSearch)) {
								String s[] = sc.getAltWords(wordToSearch);
								System.out.println("The word might be misspelled!");
								System.out.println("The top ten suggestions include: ");
								for (int i = 0; i < 10; i++) {
									System.out.println(s[i]);
								}
							} else {
								HashMap<String, Integer> frequencyMapToSort = searchWord.searchForWord(wordToSearch, webPagesNames);
								if(frequencyMapToSort.size() > 0) {
									PageRanking.sortWebPages(frequencyMapToSort);
								}
							}

						}
					}
					break;
				case 3:
					System.out.println("Program ended");
					break;

				default:
					System.out.println("Enter Valid Input");

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
