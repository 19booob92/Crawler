package org.pwr.crawler.db.sentimentAnalysis;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.pwr.crawler.engine.SentimentDetector;

public class SentimentDetectorTest {

	@Test
	public void shouldReturnNegative() {
		int result = SentimentDetector.findSentimentOfSentence("I'm think Hadoop is really bad");

		Assertions.assertThat(result).isLessThan(0);
	}

	@Test
	public void shouldReturnPossitive() {
		int result = SentimentDetector.findSentimentOfSentence(
				"I'm thinking Hadoop is really bad, but actually it is Better than for example Spark, it is really better");

		Assertions.assertThat(result).isGreaterThan(0);
	}

}
