package org.pwr.crawler.utils.htmlUtils;

public class StringUtils {
	public static String removeLastOccurenceOfCharacter(StringBuilder input, String charToRemove) {
		int lastCommaIndex = input.lastIndexOf(charToRemove);
		input.deleteCharAt(lastCommaIndex);

		return input.toString();
	}
}
