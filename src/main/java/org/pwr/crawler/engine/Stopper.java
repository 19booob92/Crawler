package org.pwr.crawler.engine;

import org.pwr.crawler.main.Runner;

public class Stopper implements Runnable {

	private Thread[] agents;

	public Stopper(Thread[] agents) {
		this.agents = agents;
	}

	@Override
	public void run() {
		String userInput = null;
		while (true) {
			
			if (System.console() != null) {
				userInput = System.console().readLine();
			}
			if ("stop".equals(userInput)) {
				for (int i = 0; i < Runner.THREAD_AMOUNT; i++) {
					agents[i].interrupt();
				}
				System.exit(0);
			}
		}

	}

}
