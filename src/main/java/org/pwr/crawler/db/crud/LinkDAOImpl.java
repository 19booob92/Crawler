package org.pwr.crawler.db.crud;

import static org.pwr.crawler.utils.restriction.DocumentRestriction.*;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.pwr.crawler.db.abstracts.GenericDAO;
import org.pwr.crawler.exceptions.DocumentNotFoundException;
import org.pwr.crawler.model.HtmlUrl;
import org.pwr.crawler.utils.htmlUtils.BsonDocumentUtils;
import org.pwr.crawler.utils.htmlUtils.mappers.HtmlLinkMapper;

import com.mongodb.client.FindIterable;
import com.mongodb.client.result.DeleteResult;

public class LinkDAOImpl extends GenericDAO implements LinkDAO {

	private final static String COLLECTION_NAME = "links";

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
	public String saveUrl(HtmlUrl url) {
		Document urlDocument = HtmlLinkMapper.mapHtmlLink(url);
		collection.insertOne(urlDocument);

		return BsonDocumentUtils.getId(urlDocument);
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
	public synchronized List<HtmlUrl> listUnprocessedLinksForThread(int threadAmount) {
		List<HtmlUrl> urls = new ArrayList();
		
		Long unprocessedLinksAmount = countUnprocessedLinks();

		int limit = (int) (unprocessedLinksAmount / threadAmount);
		FindIterable<Document> documents = collection.find(eq("isProcessed", false)).limit(limit);
		
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
	public Long countUnprocessedLinks() {
		return collection.count(eq("isProcessed", false));
	}

}
