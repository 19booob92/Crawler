package org.pwr.crawler.main;

import static org.pwr.crawler.utils.htmlUtils.OsType.LINUX;
import static org.pwr.crawler.utils.htmlUtils.OsType.MAC;
import static org.pwr.crawler.utils.htmlUtils.OsType.UNIX;
import static org.pwr.crawler.utils.htmlUtils.OsType.WINDOWS;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.pwr.crawler.db.crud.LinkDAO;
import org.pwr.crawler.db.crud.LinkDAOImpl;
import org.pwr.crawler.engine.CrawlerAgent;
import org.pwr.crawler.engine.Stopper;
import org.pwr.crawler.utils.htmlUtils.OsType;

public class Runner {

	public static int THREAD_AMOUNT = 4;

	public static final int THREAD_ARG = 0;

	public static void main(String[] args) {

		final Map<OsType, Integer> statistics = Collections.synchronizedMap(new HashMap<OsType, Integer>() {
			{
				put(LINUX, 0);
				put(WINDOWS, 0);
				put(UNIX, 0);
				put(MAC, 0);
			}
		});

		System.out.println("Argument 0 : ilosc watkow \n");

		THREAD_AMOUNT = Integer.valueOf(args[THREAD_ARG]);

		Thread[] threads = new Thread[THREAD_AMOUNT];

		LinkDAO linkDAO = LinkDAOImpl.getInstance();

		initThreads(linkDAO, threads, statistics);
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (OsType osType : statistics.keySet()) {
			System.err.println(osType.toString() + " : " + statistics.get(osType));
		}
		System.exit(0);
		initStopper(threads);
	}

	private static void initStopper(Thread[] threads) {
		Stopper stopper = new Stopper(threads);
		Thread stopperThread = new Thread(stopper);

		stopperThread.start();
	}

	private static void initThreads(LinkDAO linkDAO, Thread[] threads, Map<OsType, Integer> statistics) {

		CrawlerAgent[] crawlers = new CrawlerAgent[THREAD_AMOUNT];

		Long unprocessedLinksAmount = linkDAO.countUnprocessedLinks();
		int linksToFetchAmount = (int) Math.ceil(unprocessedLinksAmount * 1.0 / THREAD_AMOUNT);

		for (int i = 0; i < THREAD_AMOUNT; i++) {
			crawlers[i] = new CrawlerAgent(i, linksToFetchAmount, statistics);
			threads[i] = new Thread(crawlers[i]);

			threads[i].start();
		}
	}
}
