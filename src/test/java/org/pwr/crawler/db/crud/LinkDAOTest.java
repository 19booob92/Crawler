package org.pwr.crawler.db.crud;

import static org.junit.Assert.*;
import static org.pwr.crawler.utils.restriction.DocumentRestriction.eq;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pwr.crawler.exceptions.DocumentNotFoundException;
import org.pwr.crawler.model.HtmlUrl;

import junit.framework.Assert;

public class LinkDAOTest {

	private static LinkDAOImpl dao;

	@BeforeClass
	public static void init() {
		dao = new LinkDAOImpl();
	}

	@Test
	public void shouldReturnAllLinksFromDB() {
		List<HtmlUrl> links = dao.listAllLinks();

		Assertions.assertThat(links).hasAtLeastOneElementOfType(HtmlUrl.class);
	}

	@Test
	public void shouldReturnProperElements() {
		List<HtmlUrl> links = dao.listAllLinks();

		Assertions.assertThat(links.get(0).getName()).isEqualTo("bbc");
	}

	@Test
	public void shouldReturnElementsWithProperId() {
		ObjectId id = new ObjectId("56a15c1b1bb59c81f7206253");

		List<HtmlUrl> links = dao.listAllLinks();

		ObjectId firstElementId = links.get(0).get_id();
		Assertions.assertThat(firstElementId).isEqualTo(id);
	}

	@Test
	public void shouldSaveEntry() throws DocumentNotFoundException {
		String anyName = "TEST";
		String anyUrl = "http://www.test.123.pl";

		HtmlUrl url = new HtmlUrl(anyName, anyUrl);
		String id = dao.saveUrl(url);

		Assertions.assertThat(id).isNotNull();
		Assertions.assertThat(id).isNotEmpty();

		HtmlUrl savedUrl = dao.findOne(new ObjectId(id));

		Assertions.assertThat(savedUrl.get_id().toString()).isEqualTo(id);
		Assertions.assertThat(savedUrl.getName()).isEqualTo(url.getName());
		Assertions.assertThat(savedUrl.getUrl()).isEqualTo(url.getUrl());

		Boolean isDeleted = dao.delete(eq("name", anyName));

		Assertions.assertThat(isDeleted).isTrue();
	}

	@Test(expected = DocumentNotFoundException.class)
	public void shouldRemoveEntry() throws DocumentNotFoundException {
		String anyName = "TEST";
		String anyUrl = "http://www.test.123.pl";

		HtmlUrl url = new HtmlUrl(anyName, anyUrl);
		String id = dao.saveUrl(url);

		HtmlUrl savedUrl = dao.findOne(new ObjectId(id));

		Assertions.assertThat(savedUrl).isNotNull();

		Boolean isDeleted = dao.delete(eq("name", anyName));

		Assertions.assertThat(isDeleted).isTrue();

		savedUrl = dao.findOne(new ObjectId(id));
	}
	
	@Test
	public void shouldReturnProperAmountOfUnprocessedLinks(){

		String anyName = "TEST";
		String anyUrl = "http://www.test.123.pl";

		HtmlUrl processedUrl = new HtmlUrl(anyName, anyUrl);
		processedUrl.processed();
		
		dao.saveUrl(processedUrl);
		
		Long amountOfUnprocessed = dao.countUnprocessedLinks();
		int amountOfAll = dao.listAllLinks().size();
		
		Assert.assertTrue(amountOfAll > amountOfUnprocessed);
		
		dao.delete(eq("name", anyName));
	}

	@Test
	public void shouldReturn4Results() {
			int threadsAmount = 4;
			Long amountOfUnprocessed = dao.countUnprocessedLinks();
			
			List<HtmlUrl> result = dao.listUnprocessedLinksForThread(threadsAmount);
			
			int exceptedResultSize = (int) (amountOfUnprocessed / threadsAmount);
			
			Assert.assertTrue(result.size() == exceptedResultSize);
			Assert.assertFalse(result.get(0).getIsProcessed());
	}
}
