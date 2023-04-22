package org.campus02.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlLoader {
	public static WebPage loadWebPage(String url) throws UrlLoaderException {
		try {
			URL page = new URL(url);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(page.openStream()))) {
				String content = "";
				String line;
				while ((line = br.readLine()) != null) {
					content += line;
				}
				return new WebPage(url, content);
			}
		} catch (MalformedURLException e) {
			throw new UrlLoaderException("Invalid URL", e);
		} catch (IOException e) {
			throw new UrlLoaderException("Unable to open connection", e);
		}
	}
}
