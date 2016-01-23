package org.pwr.crawler.utils.htmlUtils;

import org.bson.Document;

public class BsonDocumentUtils {

	private static final String ID = "_id";

	public static String getId(Document document) {
		return document.get(ID).toString();
	}

}
