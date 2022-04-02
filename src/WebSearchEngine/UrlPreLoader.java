package WebSearchEngine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UrlPreLoader {
	public static HashMap<String,String> GetData() throws IOException {
		HashMap<String, String> webPagesNames = null;
		HashMap<String, String> allPagesNames = new HashMap<>();

		Scanner s = new Scanner(new File("src/PreLoadURLs.txt"));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
			list.add(s.next());
		}
		s.close();

		for(int i=0;i<list.size();i++)
		{
			WebCrawler wc = new WebCrawler();
			wc.getPageLinks(list.get(i));
			webPagesNames = wc.getHTMLWebPages();
			wc.HTMLToTextconverter();
			allPagesNames.putAll(webPagesNames);
//			for (Map.Entry<String,String> pair : webPagesNames.entrySet()) {
//				allPagesNames.put(pair.getKey(), pair.getValue());
////				System.out.println(allPagesNames);
//			
//			}
			
		} 
		return allPagesNames;

		}
}
