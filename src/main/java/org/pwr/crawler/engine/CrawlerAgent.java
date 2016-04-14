package org.pwr.crawler.engine;

import static org.pwr.crawler.utils.htmlUtils.OsType.LINUX;
import static org.pwr.crawler.utils.htmlUtils.OsType.MAC;
import static org.pwr.crawler.utils.htmlUtils.OsType.UNIX;
import static org.pwr.crawler.utils.htmlUtils.OsType.WINDOWS;

import java.util.List;
import java.util.Map;

import org.pwr.crawler.db.crud.LinkDAO;
import org.pwr.crawler.db.crud.LinkDAOImpl;
import org.pwr.crawler.http.HttpConnector;
import org.pwr.crawler.model.HtmlUrl;
import org.pwr.crawler.utils.htmlUtils.OsType;
import org.pwr.crawler.utils.htmlUtils.TextToSentences;

public class CrawlerAgent implements Runnable {

	private int id;

	private LinkDAO linkDAO;
	private HttpConnector httpConnector;

	private int linksToFetchAmount;

	private Map<OsType, Integer> statistics;

	public CrawlerAgent(int id, int linksToFetchAmount, Map<OsType, Integer> statistics) {
		this.id = id;
		this.linksToFetchAmount = linksToFetchAmount;
		this.statistics = statistics;

		linkDAO = LinkDAOImpl.getInstance();
		httpConnector = HttpConnector.getInstance();
	}

	@Override
	public void run() {
		List<HtmlUrl> linksToProcessFromDB;
		HtmlUrl htmlUrl;
		linksToProcessFromDB = linkDAO.listUnprocessedLinksForThread(linksToFetchAmount);
		for (int i = 0; i < linksToProcessFromDB.size(); i++) {

			htmlUrl = linksToProcessFromDB.get(i);

			String pageSource = httpConnector.fetchHtmlFromURL(htmlUrl.getUrl());

			// TODO przetwarzenie strony
			for (String sentence : TextToSentences.splitTextToSentences(pageSource)) {
				if (sentence.contains(LINUX.toString())) {
					addToStatistics(sentence, LINUX);
				} else if (sentence.contains(MAC.toString())) {
					addToStatistics(sentence, MAC);
				} else if (sentence.contains(UNIX.toString())) {
					addToStatistics(sentence, UNIX);
				} else if (sentence.contains(WINDOWS.toString())) {
					addToStatistics(sentence, WINDOWS);
				}
			}
		}
	}

	private void addToStatistics(String sentence, OsType type) {
		int actualVal = statistics.get(type);
		statistics.put(type, actualVal + SentimentDetector.findSentimentOfSentence(sentence));
	}
}
