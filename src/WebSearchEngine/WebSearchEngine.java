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
			System.out.println("Welcome to Web Search Engine !!\n");
			int option = 0;
			
			while(option != 3) {
				System.out.println("Please choose from following options: \n");
				System.out.println(" 1. Search Word \n 2. Crawl URL \n 3. Quit \n");

				
				try {
					option = scanner.nextInt();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					switch(option){
						case 1:
							webPagesNames = UrlPreLoader.GetData();
							while(true) {
								System.out.println("------------------------------------------------------------\n");
								System.out.println("Please enter the word which you want to search or type quit : \n");
								String wordToSearch = scanner.next();
								if(wordToSearch.equals("quit")) {
									break;
								} 
								else {
									searchWord.searchForWord(wordToSearch, webPagesNames);
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

							while(true) {
								System.out.println("------------------------------------------------------------\n");
								System.out.println("Please enter the word which you want to search or type quit : \n");
								String wordToSearch = scanner.next();
								if(wordToSearch.equals("quit")) {
									break;
								} 
								else {
									searchWord.searchForWord(wordToSearch, webPagesNames);
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
			// handle exception
			System.out.println("Exception occured " + e);
		}

	}
}
