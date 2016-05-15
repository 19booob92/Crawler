package org.pwr.crawler.engine;

import static org.pwr.crawler.utils.htmlUtils.OsType.LINUX;
import static org.pwr.crawler.utils.htmlUtils.OsType.MAC;
import static org.pwr.crawler.utils.htmlUtils.OsType.UNIX;
import static org.pwr.crawler.utils.htmlUtils.OsType.WINDOWS;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.pwr.crawler.utils.htmlUtils.OsType;
import org.pwr.crawler.utils.htmlUtils.TextToSentences;

public class CrawlerAgent implements Runnable {

	private Map<OsType, Integer> statistics;
	private List<String> filesToProcess;
	Scanner scanner;

	public CrawlerAgent(List<String> filesToProcess, Map<OsType, Integer> statistics) {
		this.statistics = statistics;
		this.filesToProcess = filesToProcess;
	}

	@Override
	public void run() {
		for (String fileName : filesToProcess) {
			try {
				File file = new File("/home/hadoopuser/data/" + fileName);
				StringBuilder fileContents = new StringBuilder();
				scanner = new Scanner(file);

				if (scanner != null)
					while (scanner.hasNextLine()) {
						fileContents.append(scanner.nextLine() + "\n");
					}

				for (String sentence : TextToSentences.splitTextToSentences(fileContents.toString())) {
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
			} catch (IOException e) {
			} finally {
				scanner.close();
			}
		}
	}

	private void addToStatistics(String sentence, OsType type) {
		int actualVal = statistics.get(type);
		statistics.put(type, actualVal + SentimentDetector.findSentimentOfSentence(sentence));
	}
}
