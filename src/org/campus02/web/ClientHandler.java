package org.campus02.web;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

	private Socket client;
	private WebProxy proxy;

	public ClientHandler(Socket client, WebProxy proxy) {
		this.client = client;
		this.proxy = proxy;
	}

	@Override
	public void run() {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
			String command;
			while ((command = br.readLine()) != null) {
				if (command.equals("bye")) {
					client.close();
					return;
				} else if (command.startsWith("fetch ")) {
					try {
						bw.write(proxy.fetch(command.substring("fetch ".length())).getContent());
					} catch (UrlLoaderException e) {
						bw.write("error: loading the url failed");
					}
				} else if (command.startsWith("stats ")) {
					switch (command.substring("stats ".length())) {
						case "hits":
							bw.write(proxy.statsHits());
							break;
						case "misses":
							bw.write(proxy.statsMisses());
							break;
						default:
							bw.write("error: invalid command");
					}
				} else {
					bw.write("error: command invalid");
				}
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
