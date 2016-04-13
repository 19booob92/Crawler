package org.pwr.crawler.engine;

import java.util.Arrays;
import java.util.List;

public class SentimentDetector {
	private static final List<String> NEGATIVE_WORDS = Arrays.asList(new String[] { "bad", "hate", "sick", "worst",
			"negative", "worse", "awful", "dreadful", "poor", "rough", "sad", "afraid", "disapointed" });

	private static final List<String> POSITIVE_WORDS = Arrays
			.asList(new String[] { "good", "fantastic", "super", "great", "favorite", "splendid", "first-rate",
					"first-class", "nice", "positive", "favorable", "excellent", "superb", "wonderful", "better" });

	public static int findSentimentOfSentence(String sentence) {
		int sentimentMeter = 0;
		for (String word : sentence.split(" ")) {
			word = word.toLowerCase();
			if (isNegative(word)) {
				sentimentMeter--;
			} else if (isPositive(word)) {
				sentimentMeter++;
			}
		}

		return sentimentMeter;
	}

	private static boolean isNegative(String word) {
		return NEGATIVE_WORDS.contains(word);
	}

	private static boolean isPositive(String word) {
		return POSITIVE_WORDS.contains(word);
	}
}
