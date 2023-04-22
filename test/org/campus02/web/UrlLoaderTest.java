package org.campus02.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlLoaderTest {
	@Test
	void urlLoaderOpensPageSuccessfully() throws UrlLoaderException {
		String url = "https://www.wikipedia.org";

		WebPage page = UrlLoader.loadWebPage(url);

		assertTrue(page.getSize() > 0);
		assertEquals(url, page.getUrl());
		assertEquals(page.getSize(), page.getContent().length());
	}

	@Test
	void urlLoaderThrowsExceptionOnMalformedUrl() {
		String malformedUrl = "https:/www.wikipedia.org";

		assertThrows(UrlLoaderException.class, () -> UrlLoader.loadWebPage(malformedUrl));
	}

	@Test
	void urlLoaderThrowsExceptionOnNonExistentWebPageUrl() {
		String malformedUrl = "https://www.orf.nz";

		assertThrows(UrlLoaderException.class, () -> UrlLoader.loadWebPage(malformedUrl));
	}
}