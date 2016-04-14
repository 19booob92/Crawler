package org.pwr.crawler.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpConnector {

	private static HttpConnector instance;

	private Logger logger; 
	
	private HttpConnector() {
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	public String fetchHtmlFromURL(String url) {

		StringBuilder pageContent = new StringBuilder();
		BufferedReader in = null;
		URL page;

		try {
			page = new URL(url);
			if (page != null) {
				InputStreamReader pageInputStream = new InputStreamReader(page.openStream());

				in = new BufferedReader(pageInputStream);

				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					pageContent.append(inputLine);
				}
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getMessage(), e);
			}
		}

		return pageContent.toString();
	}

	public static synchronized HttpConnector getInstance() {
		if (instance == null) {
			instance = new HttpConnector();
		}
		return instance;
	}
}
