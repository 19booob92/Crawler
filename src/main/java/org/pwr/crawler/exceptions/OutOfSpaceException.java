package org.pwr.crawler.exceptions;

import java.util.List;

import org.pwr.crawler.model.HtmlUrl;

public class OutOfSpaceException extends RuntimeException {

	private static final long serialVersionUID = 6014098097855483204L;

	private List<HtmlUrl> htmlUrls;

	public List<HtmlUrl> getHtmlUrls() {
		return htmlUrls;
	}

	public void setHtmlUrls(List<HtmlUrl> htmlUrls) {
		this.htmlUrls = htmlUrls;
	}

	public OutOfSpaceException(List<HtmlUrl> htmlUrls) {
		this.htmlUrls = htmlUrls;
	}
}
