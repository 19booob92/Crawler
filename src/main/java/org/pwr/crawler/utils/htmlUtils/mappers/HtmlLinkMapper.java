package org.pwr.crawler.utils.htmlUtils.mappers;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.pwr.crawler.model.HtmlUrl;

public class HtmlLinkMapper {

	public static Document mapHtmlLink(HtmlUrl url) {
		ObjectId id = new ObjectId();

		Document doc = new Document()
				.append("_id", id)
				.append("name", url.getName())
				.append("isProcessed", url.getIsProcessed())
				.append("url", url.getUrl());

		return doc;
	}

}
