package com.test.notification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.WatchService;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class NotificationLoader {

	public static final String TITLE = "title";
	public static final String AUTHORS = "authors";
	public static final String RELEASE_DATE = "release date";
	public static final String LIST_PRICE = "list price";
	public static final String PUBLISHER = "publisher";

	public void putBookInfo(BookInfo bookinfo, String funName, String funValue) {

		if (funName.equals(AUTHORS))
			bookinfo.setAuthors(funValue);

		else if (funName.equals(RELEASE_DATE))
			bookinfo.setRelease_date(funValue);

		else if (funName.equals(LIST_PRICE))
			bookinfo.setList_price(Double.parseDouble(funValue));

		else if (funName.equals(TITLE))
			bookinfo.setTitle(funValue);

		else if (funName.equals(PUBLISHER))
			bookinfo.setPublisher(funValue);

	}

	public Map<String, BookInfo> read_File(File inFile) throws IOException {

		Map<String, BookInfo> map = new HashMap<String, BookInfo>();
		FileInputStream fstream;
		String strLine;
		try {
			fstream = new FileInputStream(inFile);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream));

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// System.out.println(strLine);
				String item[] = strLine.split(",");
				// System.out.println(item[0] + " " + item[1] + " " + item[2]);
				String key = item[0].trim();
				String funName = item[1].trim();
				String funValue = item[2].trim();

				if (map.containsKey(key)) {
					BookInfo bookinfo = map.get(key);
					putBookInfo(bookinfo, funName, funValue);
				} else {
					BookInfo bookinfo = new BookInfo();
					putBookInfo(bookinfo, funName, funValue);
					map.put(key, bookinfo);
				}

			}

			// Close the input stream
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;

	}

	// public void printMap(Map<String, BookInfo> map) {
	// for (Map.Entry<String, BookInfo> entry : map.entrySet()) {
	// String key = entry.getKey();
	// BookInfo ds = entry.getValue();
	// System.out.println("KEY -" + key);
	// System.out.println(AUTHORS + "-" + ds.getAuthors());
	// System.out.println(TITLE + "-" + ds.getTitle());
	// System.out.println(RELEASE_DATE + " " + ds.getRelease_date());
	// System.out.println(LIST_PRICE + "-" + ds.getList_price());
	// System.out.println(PUBLISHER + "-" + ds.getPublisher());
	//
	// }
	// }

	public void insertMapData(Map<String, BookInfo> map) throws Exception {
		Dbconn dao = new Dbconn();
		dao.readDataBase();
		
		for (Map.Entry<String, BookInfo> entry : map.entrySet()) {
			String key = entry.getKey();
			BookInfo bookinfo = entry.getValue();
			dao.rec_insert(bookinfo, key);
//			System.out.println("KEY -" + key);
//			System.out.println(AUTHORS + "-" + ds.getAuthors());
//			System.out.println(TITLE + "-" + ds.getTitle());
//			System.out.println(RELEASE_DATE + " " + ds.getRelease_date());
//			System.out.println(LIST_PRICE + "-" + ds.getList_price());
//			System.out.println(PUBLISHER + "-" + ds.getPublisher());
			

		}
		dao.close();
	}

	public void scanShareFolder(File input_path, String input_bkup_path) {

		String filename;
		String extension;

		if (input_path.exists())
			for (File file : input_path.listFiles()) {
				filename = file.getName();
				extension = filename.substring(filename.lastIndexOf(".") + 1,
						filename.length());
				try {
					if (extension.equals("csv")) {
						// System.out.println(filename);
						String ss = input_path + "\\" + filename;
						Map<String, BookInfo> map = read_File(file);
						insertMapData(map);

						if (file.renameTo(new File(ss)))
							// System.out.println("file moved" + filename);
							file.delete();
					} else
						System.out.println(filename + "  not a CSV file");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		else
			System.out.println("shared folder is not valid ");
	}

	public static void main(String[] args) {

		NotificationLoader notification_loader = new NotificationLoader();
		File input_path = new File("/home/kanagaraj/workspace/shrdFld");
		String input_bkup_path = "/home/kanagaraj/workspace/data_share_bkup";

		while (true) {
			notification_loader.scanShareFolder(input_path, input_bkup_path);
		}

	}

}
