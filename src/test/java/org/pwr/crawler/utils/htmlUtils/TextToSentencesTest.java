package org.pwr.crawler.utils.htmlUtils;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TextToSentencesTest {

	@Test
	public void shouldReturnCleanWord() {
		String cleanedWord = TextToSentences.processWord("dwa.");
		String cleanedWord2 = TextToSentences.processWord("dwa,");
		String cleanedWord3 = TextToSentences.processWord("dwa!");
		String cleanedWord4 = TextToSentences.processWord("dwa?");
		String cleanedWord5 = TextToSentences.processWord("dwa:");
		String cleanedWord6 = TextToSentences.processWord("dwa\"");
		String cleanedWord7 = TextToSentences.processWord("\"dwa\"");
		String cleanedWord8 = TextToSentences.processWord("dwa'");

		Assertions.assertThat(cleanedWord).isEqualTo(cleanedWord2).isEqualTo(cleanedWord3).isEqualTo(cleanedWord4)
				.isEqualTo(cleanedWord5).isEqualTo(cleanedWord6).isEqualTo(cleanedWord7).isEqualTo(cleanedWord8);
	}
}
