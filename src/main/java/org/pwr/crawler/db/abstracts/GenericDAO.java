package org.pwr.crawler.db.abstracts;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.pwr.crawler.db.MongoConnector;
import org.pwr.crawler.utils.htmlUtils.JsonMongoDeserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public abstract class GenericDAO {

	protected Gson gson;
	protected MongoDatabase database;
	protected MongoCollection<Document> collection;

	private String collectionName;

	public GenericDAO() {
		this.collectionName = getCollectionName();
		
		database = MongoConnector.getDatabase();

		JsonMongoDeserializer mongoDeserializer = JsonMongoDeserializer.getDeserializer();
		gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, mongoDeserializer).create();
		collection = database.getCollection(collectionName);
	}

	protected abstract String getCollectionName();
}
