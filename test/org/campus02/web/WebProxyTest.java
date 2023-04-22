package org.campus02.web;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebProxyTest {
	@Test
	void webProxySavesCacheCorrectly() throws UrlLoaderException {
		WebProxy proxy = new WebProxy();
		proxy.fetch("https://www.wikipedia.org");
		proxy.writePageCacheToFile("test.txt");
	}
}