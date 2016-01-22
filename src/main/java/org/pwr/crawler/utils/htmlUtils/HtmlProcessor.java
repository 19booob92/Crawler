package org.pwr.crawler.utils.htmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pwr.crawler.model.HtmlUrl;

public class HtmlProcessor {

	private final static int DATA_POSITION = 1;
	private final static String DEFAULT_NAME = "DEFAULT NAME";
	private final static String PROTOCOL_SUFFIX = "http";

	public static List<HtmlUrl> findAllLinks(String pageSource) {
		List<HtmlUrl> urls = new ArrayList();

		Pattern anchorPattern = Pattern.compile("<a href=\"http(.*?)\"");

		Matcher matcher = anchorPattern.matcher(pageSource);
		while (matcher.find()) {
			String link = matcher.group(DATA_POSITION);

			String name = getNameFromUrl(link);
			urls.add(new HtmlUrl(name, PROTOCOL_SUFFIX + link));
		}

		return urls;
	}

	private static String getNameFromUrl(String link) {
		Pattern namePattern = Pattern.compile("\\/\\/(.*?)[\\/\\.]");
		Matcher nameMatcher = namePattern.matcher(link);
		String name = "";
		if (nameMatcher.find()) {
			name = nameMatcher.group(1);
		}
		return name;
	}

	public static List<String> findAllPhrasesWithWord(String word, String pageSource) {
		List<String> phrases = new ArrayList();

		Pattern anchorPattern = Pattern
				.compile("([A-Z][^.?!]*?)?(?<!\\w)(?i)(" + word + ")(?!\\w)[^.?!]*?[.?!]{1,2}\"?");

		Matcher matcher = anchorPattern.matcher(pageSource);
		while (matcher.find()) {
			String phrase = matcher.group();

			phrases.add(phrase);
		}

		return phrases;
	}

}
