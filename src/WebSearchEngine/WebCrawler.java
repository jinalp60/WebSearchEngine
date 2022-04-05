package WebSearchEngine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import textprocessing.In;
import textprocessing.TST;

public class WebCrawler {
	private HashSet<String> pageLinks;
	private HashMap<String, String> webPagesNames;
	private int MAX_LINKS = 10;
	private String outputPathForHTML = "src/HTMLwebpages/";
	private String outputPathForText = "src/Textwebpages/";

	public WebCrawler() {
		pageLinks = new HashSet<String>();
		webPagesNames = new HashMap<>();
	}
	
	public HashMap<String, String> getWebPageNames() {
		return this.webPagesNames;
	}

	private boolean isValidURL(String URL) {
		String regexForURL = "https://[a-zA-Z0-9./]*[a-zA-Z0-9.]+";
		Pattern pattern = Pattern.compile(regexForURL);
		Matcher matcher = pattern.matcher(URL);
		return matcher.find();
	}

	public void getPageLinks(String URL) {
		try {
			if (!pageLinks.contains(URL) && (pageLinks.size() < MAX_LINKS) && isValidURL(URL)) {

				Document doc = Jsoup.connect(URL).get();

				if (pageLinks.add(URL)) {
					System.out.println(URL);
				}

				Elements availableLinksOnPage = doc.select("a[href]");

				// for each extracted URL, we repeat process
				for (Element ele : availableLinksOnPage) {
					// call getPageLinks() method and pass the extracted URL to it as an argument
					getPageLinks(ele.attr("abs:href"));
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// convert html page into text using jsoup library
	public void HTMLToTextconverter() throws IOException {

		File outputFolderForText = new File(outputPathForText);
		// check whether output folder for text files exists, if not create it
		if (!outputFolderForText.exists() && !outputFolderForText.isDirectory()) {
			outputFolderForText.mkdir();
		}
		File folder = new File(outputPathForHTML);
		File[] fileStream = folder.listFiles();
		assert fileStream != null;
		for (File f : fileStream) {
			if (!f.isDirectory()) {
				String htmlPath = outputPathForHTML + f.getName();
				String textPath = outputPathForText + f.getName().replaceAll(".html", "") + ".txt";

				File in = new File(htmlPath);
				Document doc = Jsoup.parse(in, "UTF-8");
				String output = doc.text();
				BufferedWriter writerTxt = new BufferedWriter(new FileWriter(textPath));
				writerTxt.write(output);
				writerTxt.close();
			}
		}
		// System.out.println(webPagesNames);

	}

	public HashMap<String, String> getHTMLWebPages() {
		try {
			for (String urlLink : this.pageLinks) {

				Document webDoc = Jsoup.connect(urlLink).get();

				String htmlDoc = webDoc.html();

				File outputFolderForHTML = new File(outputPathForHTML);
				// check whether output folder for html exists, if not create it
				if (!outputFolderForHTML.exists() && !outputFolderForHTML.isDirectory()) {
					outputFolderForHTML.mkdirs();
				}

				String fileName = urlLink.replace(".", " ").replace("https:", "").replace("www", "").replace("com", "")
						.replace("html", "").replace("htm", "").replace("php", "").replace("/", " ").replace("  ", " ")
						.trim();

				// Hashmap to map filenames to web URL links
				webPagesNames.put(fileName, urlLink);

				PrintWriter out = new PrintWriter(outputPathForHTML + fileName + ".html");
				out.println(htmlDoc);
				out.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(">> We can not fetch this url:" + e);
		}
		return this.webPagesNames;
	}

	
}
