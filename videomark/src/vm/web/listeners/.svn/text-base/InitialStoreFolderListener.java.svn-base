/**
 * 
 */
package vm.web.listeners;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import vm.util.Const;

/**
 * @author Administrator
 *
 */
public class InitialStoreFolderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("InitialStoreFolderListener.contextDestroyed()");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("InitialStoreFolderListener.contextInitialized()");
		File file1 = new File(Const.APP_CONTEXT_PATH + File.separator + "store");
		if(!file1.exists()){
			file1.mkdirs();
		}
		File file2 = new File(Const.SOURCE_FILE_DIR);
		if(!file2.exists()){
			file2.mkdirs();
		}
		File file3 = new File(Const.RESULT_FILE_DIR);
		if(!file3.exists()){
			file3.mkdirs();
		}
		File file4 = new File(Const.POSTER_FILE_DIR);
		if(!file4.exists()){
			file4.mkdirs();
		}
		File file5 = new File(Const.UNCOMPRESS_TEMP_DIR);
		if(!file5.exists()){
			file5.mkdirs();
		}
	}
}
