package org.pwr.crawler.utils.htmlUtils;

import java.util.List;

import org.junit.Test;
import org.pwr.crawler.model.HtmlUrl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class HtmlProcessorTest {

	@Test
	public void shouldReturnNameAndUrlFromString() {

		String url = "http://superTest/test.pl";
		
		String htmlString = "<a href=\"" + url + "\">";
		
		List<HtmlUrl> resultUrl = HtmlProcessor.findAllLinks(htmlString);
		
		assertThat(resultUrl.get(0).getUrl()).isEqualTo(url);
		assertThat(resultUrl.get(0).getName()).isEqualTo("superTest");
	}
	
	@Test
	public void shouldReturnProperUrlFromString() {
		
		String url = "http://superTest.pl";
		
		String htmlString = "<a href=\"" + url + "\">";
		
		List<HtmlUrl> resultUrl = HtmlProcessor.findAllLinks(htmlString);
		
		assertThat(resultUrl.get(0).getUrl()).isEqualTo(url);
		assertThat(resultUrl.get(0).getName()).isEqualTo("superTest");
	}
	
	@Test
	public void shouldReturnTwoLinks() {
		
		String url = "http://superTest.pl";
		String url2 = "https://superTest2.com";
		
		String htmlString = "<a href=\"" + url + "\"> <div> test </div> <a href=\"" + url2 + "\">";
		
		List<HtmlUrl> resultUrl = HtmlProcessor.findAllLinks(htmlString);
		
		assertThat(resultUrl).hasSize(2);
		
		assertThat(resultUrl.get(0).getUrl()).isEqualTo(url);
		assertThat(resultUrl.get(0).getName()).isEqualTo("superTest");
		
		assertThat(resultUrl.get(1).getUrl()).isEqualTo(url2);
		assertThat(resultUrl.get(1).getName()).isEqualTo("superTest2");
	}
	
	@Test
	public void shouldRetunListOfPhrasesWithGivenWord() throws Exception {
		String testWord = "testWord";
		String text = "Szybko powiedzia " + testWord + " .";
		
		List<String> resultUrl = HtmlProcessor.findAllPhrasesWithWord(testWord, text);
		
		assertThat(resultUrl).hasSize(1);
		assertThat(resultUrl.get(0)).isEqualTo(text);
		
	}
}
