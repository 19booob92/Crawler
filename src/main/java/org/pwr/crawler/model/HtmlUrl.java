package org.pwr.crawler.model;

import org.bson.types.ObjectId;

public class HtmlUrl {

	private ObjectId _id;

	private String name;

	private String url;
	
	private boolean isProcessed = false;

	public HtmlUrl() {

	}

	public HtmlUrl(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getIsProcessed() {
		return isProcessed;
	}

	public void processed() {
		this.isProcessed = true;
	}

}
