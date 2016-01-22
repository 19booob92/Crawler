package org.pwr.crawler.model;

public class Stats {

	private String id;
	
	private Long pagesPerSecond;
	
	private Long processedPages;
	
	private Double pageFetchingAvgTime;
	
	private Double findedWordsOnPageAvg;
	
	private Long maxWordsOnPage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPagesPerSecond() {
		return pagesPerSecond;
	}

	public void setPagesPerSecond(Long pagesPerSecond) {
		this.pagesPerSecond = pagesPerSecond;
	}

	public Long getProcessedPages() {
		return processedPages;
	}

	public void setProcessedPages(Long processedPages) {
		this.processedPages = processedPages;
	}

	public Double getPageFetchingAvgTime() {
		return pageFetchingAvgTime;
	}

	public void setPageFetchingAvgTime(Double pageFetchingAvgTime) {
		this.pageFetchingAvgTime = pageFetchingAvgTime;
	}

	public Double getFindedWordsOnPageAvg() {
		return findedWordsOnPageAvg;
	}

	public void setFindedWordsOnPageAvg(Double findedWordsOnPageAvg) {
		this.findedWordsOnPageAvg = findedWordsOnPageAvg;
	}

	public Long getMaxWordsOnPage() {
		return maxWordsOnPage;
	}

	public void setMaxWordsOnPage(Long maxWordsOnPage) {
		this.maxWordsOnPage = maxWordsOnPage;
	}
	
	
}
