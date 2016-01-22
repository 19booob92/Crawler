package org.pwr.crawler.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Connector {

	public String fetchHtmlFromURL(String url) throws IOException {

		StringBuilder pageContent = new StringBuilder();

		URL page = new URL(url);
		BufferedReader in = new BufferedReader(new InputStreamReader(page.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			pageContent.append(inputLine);
		}
		in.close();

		return pageContent.toString();
	}

}
