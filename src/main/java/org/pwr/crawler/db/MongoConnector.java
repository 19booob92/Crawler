package org.pwr.crawler.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnector {

	private static MongoConnector mongoConnector;

	private MongoClient mongoClient;

	private static MongoDatabase database;

	private MongoConnector() {
		mongoClient = new MongoClient();
		database = mongoClient.getDatabase("links");
	}

	public static synchronized MongoDatabase getDatabase() {
		if (MongoConnector.mongoConnector == null) {
			MongoConnector.mongoConnector = new MongoConnector();
		}
		return database;
	}

}
