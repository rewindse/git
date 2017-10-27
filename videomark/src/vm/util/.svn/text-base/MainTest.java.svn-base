/**
 * 
 */
package vm.util;

import java.util.Random;
import java.util.UUID;


import vm.db.dao.OriginalFileDAO;
import vm.db.po.OriginalFile;

/**
 * @author Administrator
 *
 */
public class MainTest {
	
	static OriginalFileDAO originalFileDao = new OriginalFileDAO();
	
	static private OriginalFile createOriginalFileObject(String id, String name, boolean isParent, String path, String pid) {
		OriginalFile originalFile = new OriginalFile();
		originalFile.setOriginalFileID(id);
		long time = System.currentTimeMillis();
		originalFile.setCtime(time);
		originalFile.setUtime(time);
		originalFile.setFlag(1);
		originalFile.setNote("");
		originalFile.setName(name);
		originalFile.setParent(isParent);
		originalFile.setPath(path);
		originalFile.setTaskID("");
		originalFile.setPid(pid);
		return originalFile;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random random = new Random();
		for (int i = 0; i < 30000; i++) {
			System.out.println( i + 1 + ": ");
			originalFileDao.insert(createOriginalFileObject(UUID.randomUUID().toString(), UUID.randomUUID().toString(),
					random.nextBoolean(), UUID.randomUUID().toString(), UUID.randomUUID().toString()));
		}
	}
}
