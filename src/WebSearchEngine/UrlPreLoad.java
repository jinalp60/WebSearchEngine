package WebSearchEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UrlPreLoad {
	public static HashMap<String,String> GetData() throws IOException {
		HashMap<String, String> webPagesNames = null;
		HashMap<String, String> allPagesNames = new HashMap<>();

		Scanner s = new Scanner(new File("src/WebpreloadURL.txt")); //Scanning the URL file
		ArrayList<String> list = new ArrayList<String>(); //Initialising the array to process
		while (s.hasNext()){	//Check if the txt file has next line
			list.add(s.next());	//Add the line to the list array
		}
		s.close();	//Closing the files

		for(int i=0;i<list.size();i++)  //loop for the size of the list
		{
			WebCrawler wc = new WebCrawler();  //crawl each page mentioned in the URL file
			wc.getPageLinks(list.get(i));
			webPagesNames = wc.getHTMLWebPages();  //Store name in hashmap
			wc.HTMLToTextconverter();
			allPagesNames.putAll(webPagesNames); //merge hashmap in one place
//			for (Map.Entry<String,String> pair : webPagesNames.entrySet()) {
//				allPagesNames.put(pair.getKey(), pair.getValue());
////				System.out.println(allPagesNames);
//			
//			}
			
		} 
		return allPagesNames; //Returning the hashmap with all the pages which are crawled

		}
}
