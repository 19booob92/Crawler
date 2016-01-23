package org.pwr.crawler.utils.restriction;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DocumentRestriction {

	public static Document eq(String key, Object value) {
		return new Document(key, value);
	}

	public static Document set(String key, Object value) {
		DBObject query = new BasicDBObject(key, value);
		Document updateQuery = new Document("$set", query);

		return updateQuery;
	}

}
