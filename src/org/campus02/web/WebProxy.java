package org.campus02.web;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WebProxy {
	private PageCache cache;
	private int numCacheHits = 0;
	private int numCacheMisses = 0;

	public WebProxy() {
		cache = new PageCache();
	}

	public WebProxy(PageCache cache) {
		this.cache = cache;
	}

	public WebPage fetch(String url) throws UrlLoaderException {
		try {
			WebPage page = cache.readFromCache(url);
			numCacheHits++;
			return page;
		} catch (CacheMissException e) {
			numCacheMisses++;
			return loadAndCacheWebPage(url);
		}
	}

	private WebPage loadAndCacheWebPage(String url) throws UrlLoaderException {
		WebPage page = UrlLoader.loadWebPage(url);
		cache.writeToCache(page);
		return page;
	}

	public String statsHits() {
		return "stats hits: " + numCacheHits;
	}

	public String statsMisses() {
		return "stats misses: " + numCacheMisses;
	}

	public boolean writePageCacheToFile(String pathToFile) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile))) {
			for (Map.Entry<String, WebPage> cacheEntry : cache.getCache().entrySet()) {
				bw.write(cacheEntry.getKey() + ";" + cacheEntry.getValue().getContent());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
