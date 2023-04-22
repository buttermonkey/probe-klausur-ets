package org.campus02.web;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PageCache {
	private Map<String, WebPage> cache = new HashMap<>();

	public WebPage readFromCache(String url) throws CacheMissException {
		if (!cache.containsKey(url))
			throw new CacheMissException("URL not cached");
		return cache.get(url);
	}

	public void writeToCache(WebPage webPage) {
		cache.put(webPage.getUrl(), webPage);
	}

	public void warmUp(String pathToUrls) throws UrlLoaderException {
		try (BufferedReader br = new BufferedReader(new FileReader(pathToUrls))) {
			String urlFromFile;
			while ((urlFromFile = br.readLine()) != null) {
				try {
					WebPage page = UrlLoader.loadWebPage(urlFromFile);
					writeToCache(page);
				} catch (UrlLoaderException e) {
					// Don't throw, read next URL
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			throw new UrlLoaderException("Warmup file not found", e);
		} catch (IOException e) {
			throw new UrlLoaderException("Unable to read warmup file", e);
		}
	}

	public Map<String, WebPage> getCache() {
		return cache;
	}
}
