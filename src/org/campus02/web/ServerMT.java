package org.campus02.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMT {
	public static void main(String[] args) {
		PageCache cache = new PageCache();
		try {
			cache.warmUp("demo_urls.txt");
		} catch (UrlLoaderException e) {
			e.printStackTrace();
		}

		WebProxy proxy = new WebProxy(cache);
		try (ServerSocket serverSocket = new ServerSocket(5678)) {
			// server should run forever
			while (true) {
				System.out.println("Waiting for client to connect...");
				Socket client = serverSocket.accept();
				// business logic
				ClientHandler clientHandler = new ClientHandler(client, proxy);
				new Thread(clientHandler).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
