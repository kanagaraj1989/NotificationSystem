import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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

	public static <V> void main(String[] args) {

		NotificationLoader cs = new NotificationLoader();
		File input_path = new File("E:\\Siva_amaz\\data_share");
		File input_bkup_path = new File("E:\\Siva_amaz\\data_share_bkup");

		String filename;
		String extension;

		if (input_path.exists())
			while (true) {
				for (File f : input_path.listFiles()) {
					filename = f.getName();
					extension = filename.substring(
							filename.lastIndexOf(".") + 1, filename.length());
					try {
						if (extension.equals("csv")) {
							File ff = new File(input_path + "\\" + filename);
							// System.out.println(filename);
							String ss = input_bkup_path + "\\siva\\" + filename;
							Map<String, BookInfo> map = cs.read_File(ff);
							cs.printMap(map);

							if (ff.renameTo(new File(ss)))
								;
							// System.out.println("file moved" + filename);
							// ff.delete();
						} else
							System.out.println(filename + "  not a CSV file");
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		else
			System.out.println("bad share path");

	}

	public void printMap(Map<String, BookInfo> map) {
		for (Map.Entry<String, BookInfo> entry : map.entrySet()) {
			String key = entry.getKey();
			BookInfo ds = entry.getValue();
			System.out.println("KEY -" + key);
			System.out.println(AUTHORS + "-" + ds.getAuthors());
			System.out.println(TITLE + "-" + ds.getTitle());
			System.out.println(RELEASE_DATE + " " + ds.getRelease_date());
			System.out.println(LIST_PRICE + "-" + ds.getList_price());
			System.out.println(PUBLISHER + "-" + ds.getPublisher());

		}
	}
}
