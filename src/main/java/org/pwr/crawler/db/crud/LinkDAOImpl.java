package org.pwr.crawler.db.crud;

import static org.pwr.crawler.utils.restriction.DocumentRestriction.eq;
import static org.pwr.crawler.utils.restriction.DocumentRestriction.set;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.pwr.crawler.db.abstracts.GenericDAO;
import org.pwr.crawler.exceptions.DocumentNotFoundException;
import org.pwr.crawler.model.HtmlUrl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;

public class LinkDAOImpl extends GenericDAO implements LinkDAO {

	private final static String COLLECTION_NAME = "links";

	private static LinkDAOImpl instance;

	private LinkDAOImpl() {

	}

	@Override
	protected String getCollectionName() {
		return COLLECTION_NAME;
	}

	@Override
	public List<HtmlUrl> listAllLinks() {

		List<HtmlUrl> urls = new ArrayList();

		FindIterable<Document> documents = collection.find();

		for (Document document : documents) {
			HtmlUrl url = gson.fromJson(document.toJson(), HtmlUrl.class);
			urls.add(url);
		}

		return urls;
	}


	@Override
	public HtmlUrl findOne(ObjectId id) throws DocumentNotFoundException {
		Document document = collection.find(eq("_id", id)).first();
		if (document == null) {
			throw new DocumentNotFoundException();
		}
		HtmlUrl url = gson.fromJson(document.toJson(), HtmlUrl.class);

		return url;
	}

	@Override
	public Boolean delete(Document doc) {
		DeleteResult result = collection.deleteOne(doc);

		return result.getDeletedCount() > 0;
	}

	@Override
	public synchronized List<HtmlUrl> listUnprocessedLinksForThread(int linksToFetchAmount) {
		List<HtmlUrl> urls = new ArrayList<>();

		FindIterable<Document> documents = collection.find(eq("isProcessed", false)).limit(linksToFetchAmount);
		
		for (Document doc : documents) {
			collection.updateOne(doc, set("isProcessed", true));
		}

		for (Document document : documents) {
			HtmlUrl url = gson.fromJson(document.toJson(), HtmlUrl.class);
			urls.add(url);
		}

		return urls;
	}
	
	@Override
	public synchronized Long countUnprocessedLinks() {
		return collection.count(eq("isProcessed", false));
	}

	public static LinkDAOImpl getInstance() {
		if (instance == null) {
			instance = new LinkDAOImpl();
		}
		return instance;
	}

	@Override
	public Boolean checkIfExists(String url) {
		long result = collection.count(eq("url", url));
		if (result != 0) {
			return true;
		}
		return false;
	}

}
