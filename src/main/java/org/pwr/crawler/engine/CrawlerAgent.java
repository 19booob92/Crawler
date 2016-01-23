package org.pwr.crawler.engine;

import java.util.List;

import org.pwr.crawler.db.crud.LinkDAO;
import org.pwr.crawler.db.crud.LinkDAOImpl;
import org.pwr.crawler.exceptions.OutOfSpaceException;
import org.pwr.crawler.http.HttpConnector;
import org.pwr.crawler.main.Runner;
import org.pwr.crawler.model.HtmlUrl;
import org.pwr.crawler.utils.htmlUtils.HtmlProcessor;

public class CrawlerAgent implements Runnable {

	private int id;

	private LinkDAO linkDAO;
	private HttpConnector httpConnector;

	private String[] wordsToContains;
	private int linksToFetchAmount;

	public CrawlerAgent(int id, int linksToFetchAmount, String[] wordsToContains) {
		this.id = id;
		this.wordsToContains = wordsToContains;
		this.linksToFetchAmount = linksToFetchAmount;

		linkDAO = LinkDAOImpl.getInstance();
		httpConnector = HttpConnector.getInstance();
	}

	@Override
	public void run() {
		List<HtmlUrl> linksToProcessFromDB;
		HtmlUrl htmlUrl;
		while (true) {
			try {
				linksToProcessFromDB = linkDAO.listUnprocessedLinksForThread(linksToFetchAmount);
				// pętla zamiast parellal stream' a żeby łatwo przerwać i można
				// było przywrócić nie
				// przetworzone linki do bazy
				for (int i = 0; i < linksToProcessFromDB.size(); i++) {

					htmlUrl = linksToProcessFromDB.get(i);

					String pageSource = httpConnector.fetchHtmlFromURL(htmlUrl.getUrl());

					if (linkDAO.countUnprocessedLinks() < Runner.MAX_UNPROCESSED_LINKS_AMOUNT) {
						saveNewLinks(pageSource);
					} else {
						throw new OutOfSpaceException(linksToProcessFromDB.subList(i, linksToProcessFromDB.size() - 1));
					}

					printSentences(pageSource);

				}
			} catch (OutOfSpaceException ex) {
				linkDAO.restoreUnprocessed(ex.getHtmlUrls());
			}
		}
	}

	private void printSentences(String pageSource) {
		List<String> linksToProcess = HtmlProcessor.findAllPhrasesWithWord(wordsToContains, pageSource);

		linksToProcess.forEach(sentence -> {
			System.err.println(sentence);
		});
	}

	private void saveNewLinks(String pageSource) {
		List<HtmlUrl> linksToProcess = HtmlProcessor.findAllLinks(pageSource);

		linksToProcess.stream().forEach(url -> {
			linkDAO.saveUrl(url);
		});
	}

}
