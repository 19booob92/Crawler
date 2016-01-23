package org.pwr.crawler.engine;

import java.util.List;

import org.pwr.crawler.db.crud.LinkDAO;
import org.pwr.crawler.db.crud.LinkDAOImpl;
import org.pwr.crawler.http.HttpConnector;
import org.pwr.crawler.main.Runner;
import org.pwr.crawler.model.HtmlUrl;
import org.pwr.crawler.utils.htmlUtils.HtmlProcessor;

public class CrawlerAgent implements Runnable {

	private LinkDAO linkDAO;
	private HttpConnector httpConnector;

	public CrawlerAgent() {
		linkDAO = new LinkDAOImpl();
		httpConnector = HttpConnector.getInstance();
	}

	@Override
	public void run() {
		List<HtmlUrl>  linksToProcessFromDB = linkDAO.listUnprocessedLinksForThread(Runner.THREAD_AMOUNT);

		linksToProcessFromDB
			.parallelStream()
			.forEach(url -> {
				
				String pageSource = httpConnector.fetchHtmlFromURL(url.getUrl());

				saveNewLinks(pageSource);
				printSentences(pageSource);
				
		});
	}

	private void printSentences(String pageSource) {
		List<String> linksToProcess = HtmlProcessor.findAllPhrasesWithWord("Java", pageSource);
		
		linksToProcess.forEach(sentence -> {
			System.err.println(sentence);
		});
	}

	private void saveNewLinks(String pageSource) {
		List<HtmlUrl> linksToProcess = HtmlProcessor.findAllLinks(pageSource);
		
		linksToProcess.stream()
				.forEach(url -> {
					linkDAO.saveUrl(url);
				});
	}

}
