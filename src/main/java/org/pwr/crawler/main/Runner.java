package org.pwr.crawler.main;

import java.util.Arrays;

import org.pwr.crawler.db.crud.LinkDAO;
import org.pwr.crawler.db.crud.LinkDAOImpl;
import org.pwr.crawler.engine.CrawlerAgent;
import org.pwr.crawler.engine.Stopper;

public class Runner {

	public static int THREAD_AMOUNT = 4;
	public static int MAX_UNPROCESSED_LINKS_AMOUNT = 50000;

	public static final int THREAD_ARG = 0;
	public static final int MAX_LINKS_AMOUNT_ARG = 1;
	public static final int WORDS_ARG = 2;

	private static String[] wordsToContains;

	public static void main(String[] args) {

		System.out.println("Argument 0 : ilość wątków \n" + "Argument 1 : maxymalna ilość linków w bazie \n"
				+ "Argument 2 : słowa do znalezienia oddzielone spacjami \n");

		THREAD_AMOUNT = Integer.valueOf(args[THREAD_ARG]);
		MAX_UNPROCESSED_LINKS_AMOUNT = Integer.valueOf(args[MAX_LINKS_AMOUNT_ARG]);
		wordsToContains = Arrays.copyOfRange(args, WORDS_ARG, args.length);

		Thread[] threads = new Thread[THREAD_AMOUNT];

		LinkDAO linkDAO = LinkDAOImpl.getInstance();

		initThreads(linkDAO, threads);
		initStopper(threads);

	}

	private static void initStopper(Thread[] threads) {
		Stopper stopper = new Stopper(threads);
		Thread stopperThread = new Thread(stopper);

		stopperThread.start();
	}

	private static void initThreads(LinkDAO linkDAO, Thread[] threads) {
		CrawlerAgent[] crawlers = new CrawlerAgent[THREAD_AMOUNT];

		Long unprocessedLinksAmount = linkDAO.countUnprocessedLinks();
		int linksToFetchAmount = (int) Math.ceil(unprocessedLinksAmount * 1.0 / THREAD_AMOUNT);

		for (int i = 0; i < THREAD_AMOUNT; i++) {
			crawlers[i] = new CrawlerAgent(i, linksToFetchAmount, wordsToContains);
			threads[i] = new Thread(crawlers[i]);

			threads[i].start();
		}
	}
}
