package org.pwr.crawler.http;


import org.assertj.core.api.Assertions;
import org.junit.Test;

public class HttpContectorTest {

	@Test
	public void shouldFetchHtml() throws Exception {
		
		String url = "http://gooole.com";
		
		HttpConnector httpContector = HttpConnector.getInstance();
		
		String htmlCode = httpContector.fetchHtmlFromURL(url);
		
		Assertions.assertThat(htmlCode).contains("<html", "</html>", "google", "<div");
	}
}
