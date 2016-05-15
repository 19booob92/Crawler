package org.pwr.crawler.utils.htmlUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextToSentences {

	public static List<String> splitTextToSentences(String text) {
		if (text != null) {
			String[] sentences = text.split("(?i)(?<=[.?!])\\S+(?=[a-z])");

			return Arrays.asList(sentences);
		} else
			return new ArrayList<>();
	}

	public static String processWord(String word) {
		return word.replaceAll("[+^,:!\\?\\.\"']*", "");
	}

}
