package org.pwr.crawler.db.crud;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.pwr.crawler.exceptions.DocumentNotFoundException;
import org.pwr.crawler.model.HtmlUrl;

public interface LinkDAO {
	List<HtmlUrl> listAllLinks();
	
	Optional<String> saveUrl(HtmlUrl url);
	
	HtmlUrl findOne(ObjectId id) throws DocumentNotFoundException;
	
	Boolean delete(Document doc);
	
	List<HtmlUrl> listUnprocessedLinksForThread(int threadAmount);

	Long countUnprocessedLinks();

	void restoreUnprocessed(List<HtmlUrl> htmlUrls);

	Boolean checkIfExists(String url);
	
}
