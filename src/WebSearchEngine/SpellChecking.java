package WebSearchEngine;



import java.io.*;
import java.util.*;

public class SpellChecking {
	
	private static ArrayList<String> dictionary = new ArrayList<>();
	private static HashMap<String,Integer> mp; 
	private static String[] suggestions; 
	private static Map<String, Integer> mp2; 
	private static List<Map.Entry<String, Integer> > list; 
	private static HashMap<String, Integer> temp; 
	
	private static int noOfSuggestions; 
	
	//Constructor
	SpellChecking(){
		noOfSuggestions = 10;
		mp  = new HashMap<>();
		dictionary = new ArrayList<>();
		suggestions = new String[noOfSuggestions];
	}
	
	// setting how many suggestions of a incorrect word is needed
	public void setNoOfSuggestions(int N) {
		noOfSuggestions = N;
		suggestions = new String[noOfSuggestions]; 
	}
	
	
	// checks if the string is already correct
	public boolean isCorrect(String query)  throws IOException{
        // returns true if the query string exists in the dictionary, else false 
        if(dictionary.contains(query)) return true;
        return false; 
    }
	
	
	public static int Min(int a, int b) {
		if(a < b) return a;
		return b; 
	}
	
	// gets edit distance between the two words
	public static int getEditDistance(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		int[][] dp = new int[len1 + 1][len2 + 1];	// Edit distance table

		for (int i = 0; i <= len1; i++) { dp[i][0] = i;	}
		for (int j = 0; j <= len2; j++) { dp[0][j] = j; }
		
		for (int i = 0; i < len1; i++) {
			char c1 = word1.charAt(i);
			for (int j = 0; j < len2; j++) {
				char c2 = word2.charAt(j);
	 
				
				if (c1 == c2) {
					dp[i + 1][j + 1] = dp[i][j];
				} else {
					int replace = dp[i][j] + 1;
					int insert = dp[i][j + 1] + 1;
					int delete = dp[i + 1][j] + 1;
	 
					int min = Min(replace, insert);
					min = Min(delete, min);
					dp[i + 1][j + 1] = min;
				}
			}
		}
		return dp[len1][len2];
	}
	
	//used for debugging 
	public static void printDictionary() {
		int len = dictionary.size();
		System.out.println("The dictionary contains: "); 
		for(int i=0; i<len; i++) {
			System.out.println(dictionary.get(i)); 
		}
		return; 
	}
	
	
	// gets all the words from the dictionary
	public void getVocab() throws IOException {
		File files = new File("src/Dictionary/Oxford English Dictionary.txt");  //Opening the file containing the dictionary words
		StringBuilder SB = new StringBuilder();
		StringTokenizer STK; 

		BufferedReader BR = new BufferedReader(new FileReader(files));
		String str;
		
		//appending all the strings in the String Builder
		while ((str = BR.readLine()) != null) {    
			SB.append(str);
            SB.append(" "); 
		}
		BR.close();
		String allwords = SB.toString(); 
        
		STK = new StringTokenizer(allwords, "0123456789 ,`*$|~(){}_@><=+[]\\?;/&#-.!:\"'\n\t\r");
		
		
		while (STK.hasMoreTokens()) {
			String tk = STK.nextToken().toLowerCase(Locale.ROOT);
			if (!dictionary.contains(tk)) {
				dictionary.add(tk);
			}
		}
	}
    
	//gets suggestions for the incorrect word
	public  String[] getAltWords(String query) {

     
		mp  = new HashMap<>();
		
		for (int i=0; i<dictionary.size(); i++) {
			String s = dictionary.get(i); 
			int editDis = getEditDistance(query, s);
			mp.put(s, editDis);
		}

		list = new LinkedList<>(mp.entrySet());

		list.sort(Map.Entry.comparingByValue());

		// put data from sorted list to hashmap
		temp = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		mp2 = temp; 

		
		
		int rank = 0;
		for (Map.Entry<String, Integer> en : mp2.entrySet()) {
			if (en.getValue() != 0) {
				suggestions[rank] = en.getKey();
				rank++;
				if (rank == 10){ break; }
			}
		}
		return suggestions;
	}



	public static void main(String[] args) throws IOException{
		
		
		//used for debugging
		 SpellChecking sp = new SpellChecking();
		 int N = 10;
		 sp.setNoOfSuggestions(N);
		 String s[] = sp.getAltWords("thers");
         for(int i=0; i<10; i++){
             System.out.println(s[i]); 
         }
	}
}
