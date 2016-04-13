package org.pwr.crawler.utils.htmlUtils;

import java.util.Arrays;
import java.util.List;

public class TextToSentences {

	public static List<String> splitTextToSentences(String text) {
		String[] sentences = text.split("(?i)(?<=[.?!])\\S+(?=[a-z])");

		return Arrays.asList(sentences);
	}

	public static String processWord(String word) {
		return word.replaceAll("[+^,:!\\?\\.\"']*", "");
	}

}
