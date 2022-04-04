package WebSearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.stream.Collectors;

import textprocessing.TST;

public class SearchWord {

	HashMap<Integer, String> hm = new HashMap<Integer, String>(); 
	ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
	private String outputPathForHTML = "src/HTMLwebpages/";
	private String outputPathForText = "src/Textwebpages/";

	public TST<Integer> getTSTOfWordOccurrences(String fileName) throws IOException {

		FileReader file = new FileReader(this.outputPathForText + fileName);
		BufferedReader br = new BufferedReader(file);

		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();

		String inputText = sb.toString();

		StringTokenizer stringTokenizer = new StringTokenizer(inputText, ".,-#|&(){}[]= \n\t");

		TST<Integer> tst = new TST<Integer>();

		while (stringTokenizer.hasMoreTokens()) {
			String currentToken = stringTokenizer.nextToken();
			int currentOccurrences;
			if (tst.get(currentToken) == null) {
				currentOccurrences = 0;
			} else {
				currentOccurrences = tst.get(currentToken);
			}
			tst.put(currentToken, currentOccurrences + 1);
		}
		return tst;

	}
	
	HashMap<String, Integer> textFilesFound = new HashMap<>();
	

	public void searchForWord(String wordToSearch, HashMap<String, String> webPagesNames) {
		System.out.println("searching for " + wordToSearch);

		try {
			File[] textFiles = new File(this.outputPathForText).listFiles();

			for (File file : textFiles) {
				if (file.isFile()) {
					String fileName = file.getName();
					TST<Integer> tst = this.getTSTOfWordOccurrences(fileName);
					Integer wordOccurrences = tst.get(wordToSearch);
					if (wordOccurrences != null) {
						textFilesFound.put(fileName, wordOccurrences);
					}
				}
			}

			if (textFilesFound.size() > 0) {
				for (Map.Entry textFileEntry : textFilesFound.entrySet()) {

					String textFileName = textFileEntry.getKey().toString();
					String textFileNameWithoutExt;
					int pos = textFileName.lastIndexOf(".");
					if (pos > 0 && pos < (textFileName.length() - 1)) {
						textFileNameWithoutExt = textFileName.substring(0, pos);
					} else {
						textFileNameWithoutExt = textFileName;
					}

					for (Map.Entry webPageEntry : webPagesNames.entrySet()) {
						if (webPageEntry.getKey().equals(textFileNameWithoutExt)) {
							//System.out.println(webPageEntry.getValue() + "->" + textFileEntry.getValue());
							
							int num = (int) textFileEntry.getValue();
							String str = webPageEntry.getValue().toString();
							String str2 = String.valueOf(num);
							hm.put(num,str);
							break;
							
						}
					}
				}
			} else {
				System.out.println("'" + wordToSearch + "' word couldn't be found");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void getMap() {
		//System.out.println("Test");		
		for (Integer name: hm.keySet()) {
    	    String key = name.toString();
    	    String value = hm.get(name).toString();
    	    System.out.println(value+" -> "+key);
    	}
		
   }
}
