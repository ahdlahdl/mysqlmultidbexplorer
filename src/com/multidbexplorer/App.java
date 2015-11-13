package com.multidbexplorer;

import java.util.ArrayList;
import java.util.List;

public class App {
	public static void main(String[] args) throws Exception {
		System.out.println("welcome to multiple mysql db query explorer!");

		if (args.length == 0) {
			System.out.println("# please input parameter - query file path.");
			System.out.println("# ex. java -jar multitoad.jar query.sql");
			return;
		}

		String queryFilePath = args[0];
		String propertieFilePath = "jdbc.properties";

		App app = new App();
		app.doJob(queryFilePath, propertieFilePath);
	}

	/**
	 * 
	 * @param queryFilePath
	 * @param propertieFilePath
	 */
	public void doJob(String queryFilePath, String propertieFilePath) {
		String query = FileUtils.readFile(queryFilePath);
		if (query == null || query.length() == 0) {
			System.out.println("empty query in the file '" + queryFilePath + "'.");
			return;
		}

		System.out.println("your query : " + query);
		
		ConnectionPropertie properties = makePropertie(propertieFilePath);
		if (properties == null) {
			System.out.println("need usable connectoin. check the file '" + propertieFilePath + "'");
			return;
		}

		for (String url : properties.getUrls()) {
			Thread thread = new Thread(new QueryExecutor(url, properties.getUsername(), properties.getPassword(), query));
			thread.start();
		}
	}
	
	/**
	 * 
	 * @param propertieFilePath
	 * @return
	 */
	private ConnectionPropertie makePropertie(String propertieFilePath) {
		String properties = FileUtils.readFile(propertieFilePath);
		if (properties == null || properties.length() == 0) {
			return null;
		}
		
		ConnectionPropertie cp = new ConnectionPropertie();

		String[] datas = properties.split("\n");
		List<String> urls = new ArrayList<String>();
		for (String propertie : datas) {
			if (propertie.startsWith("username=")) {
				cp.setUsername(propertie.replace("username=", ""));
			} else if (propertie.startsWith("password=")) {
				cp.setPassword(propertie.replace("password=", ""));
			} else if (propertie.startsWith("#")) {
				continue;
			} else if (propertie.trim().length() == 0) {
				continue;
			} else {
				urls.add(propertie);
			}
		}
		cp.setUrls(urls);
		return cp;
	}
	
	

}
