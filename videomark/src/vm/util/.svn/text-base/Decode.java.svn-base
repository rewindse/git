package vm.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Decode {
	public static void main(String[] args) {
//		Decode de = new Decode();
//		String path = "";
//		String x = de.readFile(path);
//		System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"result12.json");
//		System.out.println(de.decodefile(x, "bend"));
	}

	public String readFile(String filename) {
		StringBuilder sBuilder = new StringBuilder();
		int hasRead = 0;
		try {
			//Thread.currentThread().getContextClassLoader().getResource("").getPath()+ this.filename
			FileReader fr = new FileReader(filename);
			char[] cbuf = new char[1024];
			while ((hasRead = fr.read(cbuf)) != -1) {

				sBuilder.append(new String(cbuf, 0, hasRead));
			}
			fr.close();
		} catch (IOException e) {

			e.printStackTrace();
			return "";
		}
		return sBuilder.toString();
	}

	@SuppressWarnings("rawtypes")
	public boolean decodefile(String targetString, String item) {
		System.out.println(targetString + " " + item);
		JSONObject mark = JSONObject.fromObject(targetString)
				.getJSONObject("mark_object").getJSONObject("mark_info");
		for (Iterator iter = mark.keys(); iter.hasNext();) {
			String key = iter.next().toString();
			JSONArray arr = (JSONArray) mark.get(key);
			for (int i = 0; i < arr.size(); i++) {
				if (item.equals(arr.get(i))) {
					return true;
				}
			}
		}
		return false;
	}
}
