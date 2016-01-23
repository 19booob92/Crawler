package org.pwr.crawler.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpConnector {

	private static HttpConnector instance;
	
	private HttpConnector() {
	}
	
	public String fetchHtmlFromURL(String url) {

		StringBuilder pageContent = new StringBuilder();
		BufferedReader in = null;
		URL page;
		
		try {
			page = new URL(url);
			InputStreamReader pageInputStream = new InputStreamReader(page.openStream());

			in = new BufferedReader(pageInputStream);

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				pageContent.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return pageContent.toString();
	}
	
	public static synchronized HttpConnector getInstance() {
		if (instance == null) {
			instance = new HttpConnector();
			return instance;
		}else {
			return instance;
		}
	}

}
