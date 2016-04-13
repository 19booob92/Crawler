package org.pwr.crawler.engine;

import static org.pwr.crawler.utils.htmlUtils.OsType.LINUX;
import static org.pwr.crawler.utils.htmlUtils.OsType.MAC_OS;
import static org.pwr.crawler.utils.htmlUtils.OsType.UNIX;
import static org.pwr.crawler.utils.htmlUtils.OsType.WINDOWS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pwr.crawler.db.crud.LinkDAO;
import org.pwr.crawler.db.crud.LinkDAOImpl;
import org.pwr.crawler.http.HttpConnector;
import org.pwr.crawler.model.HtmlUrl;
import org.pwr.crawler.utils.htmlUtils.OsType;
import org.pwr.crawler.utils.htmlUtils.TextToSentences;

public class CrawlerAgent implements Runnable {

	private Map<OsType, Integer> statistics = new HashMap<OsType, Integer>() {
		{
			put(LINUX, 0);
			put(WINDOWS, 0);
			put(UNIX, 0);
			put(MAC_OS, 0);
		}
	};

	private int id;

	private LinkDAO linkDAO;
	private HttpConnector httpConnector;

	private int linksToFetchAmount;

	public CrawlerAgent(int id, int linksToFetchAmount, String[] wordsToContains) {
		this.id = id;
		this.linksToFetchAmount = linksToFetchAmount;

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
				} else if (sentence.contains(MAC_OS.toString())) {
					addToStatistics(sentence, MAC_OS);
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
