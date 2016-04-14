package org.pwr.crawler.utils.htmlUtils;

public enum OsType {

	WINDOWS("windows"), MAC("mac"), LINUX("linux"), UNIX("unix");

	private final String label;

	OsType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return label;
	}
}
