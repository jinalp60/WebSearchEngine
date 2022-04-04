package WebSearchEngine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PageRanking {
	public static void sortWebPages(HashMap<String, Integer> frequencyMapToSort) {
		/*HashMap<String, Integer> frequencyMapToSort = new HashMap<>();
		frequencyMapToSort.put("www.com", 5);
		frequencyMapToSort.put("www2.com", 5);
		frequencyMapToSort.put("www3.com", 5);
		frequencyMapToSort.put("www12.com", 12);
		frequencyMapToSort.put("www10.com", 10);*/
		
		TreeMap<Integer, String[]> tree_map = new TreeMap<Integer, String[]>();
		
		for (Map.Entry frequencyEntry : frequencyMapToSort.entrySet()) {
			boolean frequencyFound = false;
			for (Map.Entry treeMapEntry : tree_map.entrySet()) {
				if (treeMapEntry.getKey().equals(frequencyEntry.getValue())) {
					frequencyFound = true;
					String[] arr= tree_map.get((Integer) frequencyEntry.getValue());
					List<String> arrlist = new ArrayList<String>(
		                Arrays.asList(arr));
					arrlist.add((String) frequencyEntry.getKey());
					arr = arrlist.toArray(arr);
					tree_map.put((Integer) frequencyEntry.getValue(), arr);
					break;
				}
			}
			if(!frequencyFound) {
				String[] arr= {(String) frequencyEntry.getKey()};
				tree_map.put((Integer) frequencyEntry.getValue(),arr);
			}
		}

		LinkedHashMap<Integer, String[]> sortedMap = new LinkedHashMap<>();

		tree_map.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
				.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

		System.out.println();
		System.out.println("------------ Sorted results ----------------");
		System.out.println();
		for (Map.Entry treeMapEntry : sortedMap.entrySet()) {
			String[] arr= (String[]) treeMapEntry.getValue();
			for(int i=0; i<arr.length; i++) {
				System.out.println(arr[i]+" -> "+(Integer)treeMapEntry.getKey());
			}
		}
		
	}
}
